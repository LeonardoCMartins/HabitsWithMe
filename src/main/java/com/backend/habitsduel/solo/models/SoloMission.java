package com.backend.habitsduel.solo.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solo_mission")
public class SoloMission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solo_player_id")
    private SoloPlayer soloPlayer;

    private String title;
    private String difficulty; // FACIL, NORMAL, DIFICIL, EXTREMA, ABSURDA
    private Integer xpReward;
    private Integer goldReward;
    private Boolean completed = false;
    private LocalDate createdAt;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDate.now(); }

    // Getters & Setters
    public Long getId() { return id; }
    public SoloPlayer getSoloPlayer() { return soloPlayer; }
    public void setSoloPlayer(SoloPlayer soloPlayer) { this.soloPlayer = soloPlayer; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public Integer getXpReward() { return xpReward; }
    public void setXpReward(Integer xpReward) { this.xpReward = xpReward; }
    public Integer getGoldReward() { return goldReward; }
    public void setGoldReward(Integer goldReward) { this.goldReward = goldReward; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
    public LocalDate getCreatedAt() { return createdAt; }
}
