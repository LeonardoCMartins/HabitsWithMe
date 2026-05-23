package com.backend.habitsduel.solo.controllers;

import com.backend.habitsduel.solo.dtos.SoloRewardDto;
import com.backend.habitsduel.solo.services.SoloRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solo/rewards")
public class SoloRewardController {

    @Autowired private SoloRewardService rewardService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<SoloRewardDto>> getRewards(@PathVariable Long userId) {
        try { return ResponseEntity.ok(rewardService.getRewards(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping
    public ResponseEntity<SoloRewardDto> addCustom(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam String tier,
            @RequestParam(required = false) String icon,
            @RequestParam Integer cost) {
        try { return ResponseEntity.ok(rewardService.addCustomReward(userId, name, tier, icon, cost)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().build(); }
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<SoloRewardDto> buy(@PathVariable Long id, @RequestParam Long userId) {
        try { return ResponseEntity.ok(rewardService.buyReward(id, userId)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().build(); }
    }
}