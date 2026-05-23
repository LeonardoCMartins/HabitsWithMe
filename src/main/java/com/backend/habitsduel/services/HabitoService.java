package com.backend.habitsduel.services;

import com.backend.habitsduel.dtos.HabitsDto;
import com.backend.habitsduel.models.Habits;
import com.backend.habitsduel.models.User;
import com.backend.habitsduel.repositories.DueloRepository;
import com.backend.habitsduel.repositories.HabitsRepository;
import com.backend.habitsduel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitoService {

    @Autowired
    private HabitsRepository habitsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DueloRepository dueloRepository;

    public HabitsDto criar(Long userId, String nome) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Habits habito = new Habits();
        habito.setUser(user);
        habito.setName(nome);
        habitsRepository.save(habito);

        dueloRepository.findByUser1OrUser2(user, user).ifPresent(duelo -> {
            User oponente = duelo.getUser1().getId().equals(userId)
                    ? duelo.getUser2() : duelo.getUser1();
            boolean jatem = habitsRepository.findByUser(oponente).stream()
                    .anyMatch(h -> h.getName().equalsIgnoreCase(nome));
            if (!jatem) {
                Habits oppHabito = new Habits();
                oppHabito.setUser(oponente);
                oppHabito.setName(nome);
                habitsRepository.save(oppHabito);
            }
        });

        return new HabitsDto(habito);
    }

    public HabitsDto editar(Long habitoId, Long userId, String novoNome) {
        Habits habito = habitsRepository.findById(habitoId)
                .orElseThrow(() -> new RuntimeException("Hábito não encontrado"));
        if (!habito.getUser().getId().equals(userId)) {
            throw new RuntimeException("Hábito não pertence a esse usuário");
        }
        habito.setName(novoNome);
        return new HabitsDto(habitsRepository.save(habito));
    }

    public List<HabitsDto> listarPorUsuario(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return habitsRepository.findByUser(user)
                .stream().map(HabitsDto::new).toList();
    }
}
