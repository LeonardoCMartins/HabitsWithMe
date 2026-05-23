package com.backend.habitsduel.solo.controllers;

import com.backend.habitsduel.solo.dtos.*;
import com.backend.habitsduel.solo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solo/player")
public class SoloPlayerController {

    @Autowired private SoloPlayerService playerService;
    @Autowired private SoloBossService bossService;
    @Autowired private SoloMissionService missionService;
    @Autowired private SoloRewardService rewardService;

    @PostMapping("/init")
    public ResponseEntity<SoloStateDto> init(@RequestParam Long userId) {
        try {
            SoloPlayerDto player = playerService.initPlayer(userId);
            SoloBossDto boss = bossService.getBoss(userId);
            List<SoloMissionDto> missions = missionService.getMissions(userId);
            List<SoloDailyQuestDto> quests = missionService.getOrGenerateQuests(userId);
            List<SoloRewardDto> rewards = rewardService.getRewards(userId);
            return ResponseEntity.ok(new SoloStateDto(player, boss, missions, quests, rewards));
        } catch (RuntimeException e) { return ResponseEntity.badRequest().build(); }
    }

    @GetMapping("/state/{userId}")
    public ResponseEntity<SoloStateDto> getState(@PathVariable Long userId) {
        try {
            SoloPlayerDto player = playerService.getPlayer(userId);
            SoloBossDto boss = bossService.getBoss(userId);
            List<SoloMissionDto> missions = missionService.getMissions(userId);
            List<SoloDailyQuestDto> quests = missionService.getOrGenerateQuests(userId);
            List<SoloRewardDto> rewards = rewardService.getRewards(userId);
            return ResponseEntity.ok(new SoloStateDto(player, boss, missions, quests, rewards));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SoloPlayerDto> getPlayer(@PathVariable Long userId) {
        try { return ResponseEntity.ok(playerService.getPlayer(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/habit-done")
    public ResponseEntity<SoloPlayerDto> habitDone(
            @PathVariable Long userId,
            @RequestParam Integer xp,
            @RequestParam Integer gold) {
        try {
            bossService.damage(userId, 100);
            return ResponseEntity.ok(playerService.habitDone(userId, xp, gold));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/bad-habit")
    public ResponseEntity<SoloPlayerDto> badHabit(
            @PathVariable Long userId,
            @RequestParam Integer xpLoss,
            @RequestParam Integer goldLoss) {
        try {
            bossService.heal(userId, 150);
            return ResponseEntity.ok(playerService.badHabit(userId, xpLoss, goldLoss));
        } catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PutMapping("/{userId}/title")
    public ResponseEntity<SoloPlayerDto> equipTitle(
            @PathVariable Long userId,
            @RequestParam String titleId) {
        try { return ResponseEntity.ok(playerService.equipTitle(userId, titleId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PutMapping("/{userId}/bet")
    public ResponseEntity<SoloPlayerDto> setBet(
            @PathVariable Long userId,
            @RequestParam Double amount) {
        try { return ResponseEntity.ok(playerService.setBet(userId, amount)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/fine/pay")
    public ResponseEntity<SoloPlayerDto> payFine(@PathVariable Long userId) {
        try { return ResponseEntity.ok(playerService.payFine(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/fine/reset")
    public ResponseEntity<SoloPlayerDto> resetFine(@PathVariable Long userId) {
        try { return ResponseEntity.ok(playerService.resetFine(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/penalties/clear")
    public ResponseEntity<SoloPlayerDto> clearPenalties(@PathVariable Long userId) {
        try { return ResponseEntity.ok(playerService.clearPenalties(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/spend-gold")
    public ResponseEntity<SoloPlayerDto> spendGold(@PathVariable Long userId, @RequestParam Integer amount) {
        try { return ResponseEntity.ok(playerService.spendGold(userId, amount)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().build(); }
    }
}