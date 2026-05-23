package com.backend.habitsduel.solo.dtos;

import com.backend.habitsduel.solo.models.SoloMission;

public record SoloMissionDto(Long id, String title, String difficulty, Integer xpReward, Integer goldReward, Boolean completed, String createdAt) {
    public SoloMissionDto(SoloMission m) {
        this(m.getId(), m.getTitle(), m.getDifficulty(), m.getXpReward(), m.getGoldReward(), m.getCompleted(),
                m.getCreatedAt() != null ? m.getCreatedAt().toString() : null);
    }
}