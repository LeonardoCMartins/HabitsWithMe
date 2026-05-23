package com.backend.habitsduel.services;

import com.backend.habitsduel.dtos.HistoricoDto;
import com.backend.habitsduel.dtos.HistoricoHabitoItemDto;
import com.backend.habitsduel.models.Habits;
import com.backend.habitsduel.models.Historico;
import com.backend.habitsduel.models.HistoricoHabito;
import com.backend.habitsduel.models.User;
import com.backend.habitsduel.repositories.HabitsRepository;
import com.backend.habitsduel.repositories.HistoricoHabitoRepository;
import com.backend.habitsduel.repositories.HistoricoRepository;
import com.backend.habitsduel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class HistoricoService {

    @Autowired
    private HistoricoRepository historicoRepository;
    @Autowired private HistoricoHabitoRepository historicoHabitoRepository;
    @Autowired private HabitsRepository habitsRepository;
    @Autowired private UserRepository userRepository;

    // Confirma um hábito do dia com foto
    public HistoricoHabitoItemDto confirmarHabito(Long userId, Long habitoId, MultipartFile foto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Habits habito = habitsRepository.findById(habitoId)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));

        // Busca ou cria o historico do dia
        LocalDate hoje = LocalDate.now();
        Historico historico = historicoRepository.findByUserAndDate(user, hoje)
                .orElseGet(() -> {
                    Historico novo = new Historico();
                    novo.setUser(user);
                    novo.setDate(hoje);
                    return historicoRepository.save(novo);
                });

        // Salva a foto no disco
        String fotoPath = salvarFoto(foto, userId, habitoId);

        // Busca ou cria o item de ligação
        HistoricoHabito item = historicoHabitoRepository
                .findByHistoricoAndHabito(historico, habito)
                .orElseGet(() -> {
                    HistoricoHabito novo = new HistoricoHabito();
                    novo.setHistorico(historico);
                    novo.setHabito(habito);
                    return novo;
                });

        item.setSituacao("FEITO");
        item.setFotoPath(fotoPath);
        return new HistoricoHabitoItemDto(historicoHabitoRepository.save(item));
    }

    // Busca o histórico de um dia específico
    public HistoricoDto getByDia(Long userId, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Historico historico = historicoRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new RuntimeException("Nenhum histórico para essa data"));
        return new HistoricoDto(historico);
    }

    // Busca histórico do mês inteiro
    public List<HistoricoDto> getByMes(Long userId, int ano, int mes) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return historicoRepository.findByUserAndDateBetween(user, inicio, fim)
                .stream().map(HistoricoDto::new).toList();
    }

    private String salvarFoto(MultipartFile foto, Long userId, Long habitoId) throws IOException {
        String pasta = "uploads/" + userId + "/";
        new File(pasta).mkdirs();
        String nomeArquivo = habitoId + "_" + LocalDate.now() + "_" + foto.getOriginalFilename();
        Path caminho = Paths.get(pasta + nomeArquivo);
        Files.write(caminho, foto.getBytes());
        return caminho.toString();
    }
}