package com.backend.habitsduel.solo.controllers;

import com.backend.habitsduel.solo.dtos.SoloBossDto;
import com.backend.habitsduel.solo.services.SoloBossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solo/boss")
public class SoloBossController {

    @Autowired private SoloBossService bossService;

    @GetMapping("/{userId}")
    public ResponseEntity<SoloBossDto> getBoss(@PathVariable Long userId) {
        try { return ResponseEntity.ok(bossService.getBoss(userId)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/damage")
    public ResponseEntity<SoloBossDto> damage(@PathVariable Long userId, @RequestParam Integer dmg) {
        try { return ResponseEntity.ok(bossService.damage(userId, dmg)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }

    @PostMapping("/{userId}/heal")
    public ResponseEntity<SoloBossDto> heal(@PathVariable Long userId, @RequestParam Integer hp) {
        try { return ResponseEntity.ok(bossService.heal(userId, hp)); }
        catch (RuntimeException e) { return ResponseEntity.notFound().build(); }
    }
}