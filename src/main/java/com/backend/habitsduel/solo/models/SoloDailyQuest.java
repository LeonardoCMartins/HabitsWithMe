package com.backend.habitsduel.solo.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solo_daily_quest")
public class SoloDailyQuest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solo_player_id")
    private SoloPlayer soloPlayer;

    private String name;
    private Integer xpReward;
    private Boolean done = false;
    private LocalDate date;

    // Getters & Setters
    public Long getId() { return id; }
    public SoloPlayer getSoloPlayer() { return soloPlayer; }
    public void setSoloPlayer(SoloPlayer soloPlayer) { this.soloPlayer = soloPlayer; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getXpReward() { return xpReward; }
    public void setXpReward(Integer xpReward) { this.xpReward = xpReward; }
    public Boolean getDone() { return done; }
    public void setDone(Boolean done) { this.done = done; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
