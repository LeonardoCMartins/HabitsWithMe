package com.backend.habitsduel.dtos;

import com.backend.habitsduel.models.Historico;

import java.time.LocalDate;
import java.util.List;

public record HistoricoDto(Long id, LocalDate date, List<HistoricoHabitoItemDto> habitos) {
    public HistoricoDto(Historico historico) {
        this(
                historico.getId(),
                historico.getDate(),
                historico.getItens().stream().map(HistoricoHabitoItemDto::new).toList()
        );
    }
}
