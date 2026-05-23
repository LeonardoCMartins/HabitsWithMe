package com.backend.habitsduel.services;

import com.backend.habitsduel.dtos.UserDto;
import com.backend.habitsduel.models.User;
import com.backend.habitsduel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto register(String name, String senha, String codigoOponente) {
        if (userRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }
        User user = new User();
        user.setName(name);
        user.setSenha(senha); // hash depois com BCrypt
        user.setCodigo(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return new UserDto(userRepository.save(user));
    }

    public UserDto login(String name, String senha) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        if (!user.getSenha().equals(senha)) {
            throw new RuntimeException("Senha incorreta");
        }
        return new UserDto(user);
    }

    public UserDto getById(Long id) {
        return new UserDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
    }
}
