package com.backend.habitsduel.dtos;

import com.backend.habitsduel.models.Duelo;

public record DueloDto(Long id, Long user1Id, String user1, Long user2Id, String user2, Float valor) {
    public DueloDto(Duelo duelo) {
        this(
                duelo.getId(),
                duelo.getUser1().getId(),
                duelo.getUser1().getName(),
                duelo.getUser2().getId(),
                duelo.getUser2().getName(),
                duelo.getValor()
        );
    }
}
