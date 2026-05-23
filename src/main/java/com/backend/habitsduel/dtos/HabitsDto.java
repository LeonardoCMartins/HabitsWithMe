package com.backend.habitsduel.dtos;

import com.backend.habitsduel.models.Habits;

public record HabitsDto(Long id, String name) {
    public HabitsDto(Habits habito) {
        this(habito.getId(), habito.getName());
    }
}
