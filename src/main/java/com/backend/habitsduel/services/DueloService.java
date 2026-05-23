package com.backend.habitsduel.services;

import com.backend.habitsduel.dtos.DueloDto;
import com.backend.habitsduel.models.Duelo;
import com.backend.habitsduel.models.User;
import com.backend.habitsduel.repositories.DueloRepository;
import com.backend.habitsduel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DueloService {

    @Autowired private DueloRepository dueloRepository;
    @Autowired
    private UserRepository userRepository;

    public DueloDto criar(Long userId, String codigoOponente, Float valor) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<Duelo> existente = dueloRepository.findByUser1OrUser2(user, user);
        if (existente.isPresent()) {
            return new DueloDto(existente.get());
        }

        User oponente = userRepository.findByCodigo(codigoOponente)
                .orElseThrow(() -> new RuntimeException("Oponente não encontrado"));

        Duelo duelo = new Duelo();
        duelo.setUser1(user);
        duelo.setUser2(oponente);
        duelo.setValor(valor);
        return new DueloDto(dueloRepository.save(duelo));
    }

    public DueloDto getMeuDuelo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new DueloDto(dueloRepository.findByUser1OrUser2(user, user)
                .orElseThrow(() -> new RuntimeException("Nenhum duelo encontrado")));
    }

    public DueloDto atualizarValor(Long userId, Float valor) {
        Duelo duelo = dueloRepository.findByUser1OrUser2(
                userRepository.findById(userId).orElseThrow(),
                userRepository.findById(userId).orElseThrow()
        ).orElseThrow(() -> new RuntimeException("Duelo não encontrado"));
        duelo.setValor(valor);
        dueloRepository.save(duelo);
        return new DueloDto(duelo);
    }
}
