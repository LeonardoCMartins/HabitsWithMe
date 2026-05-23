package com.backend.habitsduel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.*;

@Entity
@Table(name = "historico_habito")
public class HistoricoHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "historico_id")
    private Historico historico;

    @ManyToOne
    @JoinColumn(name = "habito_id")
    private Habits habito;

    private String situacao;

    private String fotoPath;

    public HistoricoHabito() {
    }

    public HistoricoHabito(Long id, Historico historico, Habits habito, String situacao, String fotoPath) {
        this.id = id;
        this.historico = historico;
        this.habito = habito;
        this.situacao = situacao;
        this.fotoPath = fotoPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }

    public Habits getHabito() {
        return habito;
    }

    public void setHabito(Habits habito) {
        this.habito = habito;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }
}
