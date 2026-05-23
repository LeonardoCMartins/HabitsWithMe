package com.backend.habitsduel.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historico")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;

    @OneToMany(mappedBy = "historico", cascade = CascadeType.ALL)
    private List<HistoricoHabito> itens = new ArrayList<>();

    public Historico() {
    }

    public Historico(Long id, User user, LocalDate date, List<HistoricoHabito> itens) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<HistoricoHabito> getItens() {
        return itens;
    }

    public void setItens(List<HistoricoHabito> itens) {
        this.itens = itens;
    }
}
