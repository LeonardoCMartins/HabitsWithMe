package com.backend.habitsduel.controllers;

import com.backend.habitsduel.dtos.HistoricoDto;
import com.backend.habitsduel.dtos.HistoricoHabitoItemDto;
import com.backend.habitsduel.services.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @PostMapping("/confirmar")
    public ResponseEntity<HistoricoHabitoItemDto> confirmar(@RequestParam Long userId,
                                                            @RequestParam Long habitoId,
                                                            @RequestParam MultipartFile foto) throws IOException {
        return ResponseEntity.ok(historicoService.confirmarHabito(userId, habitoId, foto));
    }

    @GetMapping("/dia")
    public ResponseEntity<HistoricoDto> getDia(@RequestParam Long userId,
                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return ResponseEntity.ok(historicoService.getByDia(userId, date));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mes")
    public ResponseEntity<List<HistoricoDto>> getMes(@RequestParam Long userId,
                                                     @RequestParam int ano,
                                                     @RequestParam int mes) {
        return ResponseEntity.ok(historicoService.getByMes(userId, ano, mes));
    }
}
