package com.backend.habitsduel.dtos;

import com.backend.habitsduel.models.HistoricoHabito;

public record HistoricoHabitoItemDto(Long habitoId, String habitoNome, String situacao, String fotoPath) {
    public HistoricoHabitoItemDto(HistoricoHabito item) {
        this(
                item.getHabito().getId(),
                item.getHabito().getName(),
                item.getSituacao(),
                item.getFotoPath()
        );
    }
}
