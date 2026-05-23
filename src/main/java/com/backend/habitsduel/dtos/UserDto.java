package com.backend.habitsduel.dtos;

import com.backend.habitsduel.models.Habits;
import com.backend.habitsduel.models.User;

import java.util.List;

public record UserDto(Long id, String name, String codigo, List<HabitsDto> habitos) {
    public UserDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getCodigo(),
                user.getHabitos().stream().map(HabitsDto::new).toList()
        );
    }
}
