package com.backend.habitsduel.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String senha;

    private String codigo;

    @OneToMany(mappedBy = "user")
    private List<Habits> habitos = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String senha, String codigo, List<Habits> habitos) {
        this.id = id;
        this.name = name;
        this.senha = senha;
        this.codigo = codigo;
        this.habitos = habitos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Habits> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habits> habitos) {
        this.habitos = habitos;
    }
}
