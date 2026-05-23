package com.backend.habitsduel.solo.dtos;

import java.util.List;

public record SoloStateDto(
        SoloPlayerDto player,
        SoloBossDto boss,
        List<SoloMissionDto> missions,
        List<SoloDailyQuestDto> dailyQuests,
        List<SoloRewardDto> rewards
) {}