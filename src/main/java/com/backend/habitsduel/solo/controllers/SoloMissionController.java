package com.backend.habitsduel.solo.controllers;

import com.backend.habitsduel.solo.dtos.SoloDailyQuestDto;
import com.backend.habitsduel.solo.dtos.SoloMissionDto;
import com.backend.habitsduel.solo.services.SoloMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solo")
public class SoloMissionController {

    @Autowired private SoloMissionService missionService;

    @GetMapping("/missions/{userId}")
    public ResponseEntity<List<SoloMissionDto>> getMissions(@PathVariable Long userId) {
        try { return ResponseEntity.ok(missionService.getMissions(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/missions")
    public ResponseEntity<SoloMissionDto> createMission(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String difficulty,
            @RequestParam Integer xp,
            @RequestParam Integer gold) {
        try { return ResponseEntity.ok(missionService.createMission(userId, title, difficulty, xp, gold)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().build(); }
    }

    @PostMapping("/missions/{id}/complete")
    public ResponseEntity<SoloMissionDto> completeMission(@PathVariable Long id, @RequestParam Long userId) {
        try { return ResponseEntity.ok(missionService.completeMission(id, userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @GetMapping("/quests/{userId}")
    public ResponseEntity<List<SoloDailyQuestDto>> getQuests(@PathVariable Long userId) {
        try { return ResponseEntity.ok(missionService.getOrGenerateQuests(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/quests/{id}/complete")
    public ResponseEntity<SoloDailyQuestDto> completeQuest(@PathVariable Long id, @RequestParam Long userId) {
        try { return ResponseEntity.ok(missionService.completeQuest(id, userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}