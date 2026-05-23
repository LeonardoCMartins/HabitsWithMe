package com.backend.habitsduel.solo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "solo_boss")
public class SoloBoss {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "solo_player_id", unique = true)
    private SoloPlayer soloPlayer;

    private Integer hp = 600;
    private Integer maxHp = 600;
    private String weekStart = "";
    private Boolean defeated = false;
    private String rankName = "E";

    // Getters & Setters
    public Long getId() { return id; }
    public SoloPlayer getSoloPlayer() { return soloPlayer; }
    public void setSoloPlayer(SoloPlayer soloPlayer) { this.soloPlayer = soloPlayer; }
    public Integer getHp() { return hp; }
    public void setHp(Integer hp) { this.hp = hp; }
    public Integer getMaxHp() { return maxHp; }
    public void setMaxHp(Integer maxHp) { this.maxHp = maxHp; }
    public String getWeekStart() { return weekStart; }
    public void setWeekStart(String weekStart) { this.weekStart = weekStart; }
    public Boolean getDefeated() { return defeated; }
    public void setDefeated(Boolean defeated) { this.defeated = defeated; }
    public String getRankName() { return rankName; }
    public void setRankName(String rankName) { this.rankName = rankName; }
}
