package com.backend.habitsduel.solo.dtos;

import com.backend.habitsduel.solo.models.SoloBoss;

public record SoloBossDto(Long id, Integer hp, Integer maxHp, String weekStart, Boolean defeated, String rankName) {
    public SoloBossDto(SoloBoss b) {
        this(b.getId(), b.getHp(), b.getMaxHp(), b.getWeekStart(), b.getDefeated(), b.getRankName());
    }
}