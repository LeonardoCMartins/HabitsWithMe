package com.backend.habitsduel.solo.dtos;

import com.backend.habitsduel.solo.models.SoloReward;

public record SoloRewardDto(Long id, String rewardId, String name, String tier, String icon, Integer cost, Boolean purchased, Boolean custom) {
    public SoloRewardDto(SoloReward r) {
        this(r.getId(), r.getRewardId(), r.getName(), r.getTier(), r.getIcon(), r.getCost(), r.getPurchased(), r.getCustom());
    }
}