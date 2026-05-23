package com.backend.habitsduel.controllers;

import com.backend.habitsduel.dtos.DueloDto;
import com.backend.habitsduel.services.DueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duelo")
public class DueloController {

    @Autowired
    private DueloService dueloService;


    @PostMapping
    public ResponseEntity<DueloDto> criar(@RequestParam Long userId,
                                          @RequestParam String codigoOponente,
                                          @RequestParam Float valor) {
        return ResponseEntity.ok(dueloService.criar(userId, codigoOponente, valor));
    }

    @GetMapping("/meu")
    public ResponseEntity<DueloDto> getMeuDuelo(@RequestParam Long userId) {
        try {
            return ResponseEntity.ok(dueloService.getMeuDuelo(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/conectar")
    public ResponseEntity<DueloDto> conectar(@RequestParam Long userId,
                                             @RequestParam String codigoOponente) {
        try {
            return ResponseEntity.ok(dueloService.criar(userId, codigoOponente, 50f));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/valor")
    public ResponseEntity<?> atualizarValor(@RequestParam Long userId, @RequestParam Float valor) {
        try {
            return ResponseEntity.ok(dueloService.atualizarValor(userId, valor));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}