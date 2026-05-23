package com.backend.habitsduel.solo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "solo_reward")
public class SoloReward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "solo_player_id")
    private SoloPlayer soloPlayer;

    private String rewardId;  // ID do frontend (r1, r2...) ou null se custom
    private String name;
    private String tier;      // basic, mid, epic, legend
    private String icon;
    private Integer cost;
    private Boolean purchased = false;
    private Boolean custom = false;

    // Getters & Setters
    public Long getId() { return id; }
    public SoloPlayer getSoloPlayer() { return soloPlayer; }
    public void setSoloPlayer(SoloPlayer soloPlayer) { this.soloPlayer = soloPlayer; }
    public String getRewardId() { return rewardId; }
    public void setRewardId(String rewardId) { this.rewardId = rewardId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getCost() { return cost; }
    public void setCost(Integer cost) { this.cost = cost; }
    public Boolean getPurchased() { return purchased; }
    public void setPurchased(Boolean purchased) { this.purchased = purchased; }
    public Boolean getCustom() { return custom; }
    public void setCustom(Boolean custom) { this.custom = custom; }
}
