package com.backend.habitsduel.controllers;

import com.backend.habitsduel.dtos.UserDto;
import com.backend.habitsduel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestParam String name,
                                            @RequestParam String senha,
                                            @RequestParam(required = false) String codigoOponente) {
        return ResponseEntity.ok(userService.register(name, senha, codigoOponente));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestParam String name, @RequestParam String senha) {
        return ResponseEntity.ok(userService.login(name, senha));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
