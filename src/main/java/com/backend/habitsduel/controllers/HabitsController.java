package com.backend.habitsduel.controllers;

import com.backend.habitsduel.dtos.HabitsDto;
import com.backend.habitsduel.services.HabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habito")
public class HabitsController {

    @Autowired
    private HabitoService habitoService;

    @PostMapping
    public ResponseEntity<HabitsDto> criar(@RequestParam Long userId, @RequestParam String nome) {
        return ResponseEntity.ok(habitoService.criar(userId, nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitsDto> editar(@PathVariable Long id,
                                            @RequestParam Long userId,
                                            @RequestParam String nome) {
        return ResponseEntity.ok(habitoService.editar(id, userId, nome));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HabitsDto>> listar(@PathVariable Long userId) {
        return ResponseEntity.ok(habitoService.listarPorUsuario(userId));
    }
}
