package com.backend.habitsduel.solo.dtos;

import com.backend.habitsduel.solo.models.SoloPlayer;

public record SoloPlayerDto(
        Long id, Long userId, String username,
        Long totalXP, Long gold, Long totalGoldEarned, Long goldSpent,
        Integer mana, Integer maxMana,
        Integer streak, Integer maxStreak, String lastStreakDate,
        Integer shields, Integer totalHabsDone, Integer totalPhotos,
        Integer bossKills, Integer missionsCompleted,
        Double betAmount, Double fineBalance, Double finesPaid,
        String equippedTitleId, Integer cleanStreak, String pendingPenalties
) {
    public SoloPlayerDto(SoloPlayer p) {
        this(p.getId(), p.getUser().getId(), p.getUser().getName(),
                p.getTotalXP(), p.getGold(), p.getTotalGoldEarned(), p.getGoldSpent(),
                p.getMana(), p.getMaxMana(),
                p.getStreak(), p.getMaxStreak(), p.getLastStreakDate(),
                p.getShields(), p.getTotalHabsDone(), p.getTotalPhotos(),
                p.getBossKills(), p.getMissionsCompleted(),
                p.getBetAmount(), p.getFineBalance(), p.getFinesPaid(),
                p.getEquippedTitleId(), p.getCleanStreak(), p.getPendingPenalties());
    }
}