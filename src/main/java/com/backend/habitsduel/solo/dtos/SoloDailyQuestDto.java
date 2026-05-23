package com.backend.habitsduel.solo.dtos;

import com.backend.habitsduel.solo.models.SoloDailyQuest;

public record SoloDailyQuestDto(Long id, String name, Integer xpReward, Boolean done, String date) {
    public SoloDailyQuestDto(SoloDailyQuest q) {
        this(q.getId(), q.getName(), q.getXpReward(), q.getDone(),
                q.getDate() != null ? q.getDate().toString() : null);
    }
}