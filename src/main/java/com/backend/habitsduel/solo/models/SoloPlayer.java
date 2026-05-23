package com.backend.habitsduel.solo.models;

import com.backend.habitsduel.models.User;
import jakarta.persistence.*;

@Entity
@Table(name = "solo_player")
public class SoloPlayer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private Long totalXP = 0L;
    private Long gold = 0L;
    private Long totalGoldEarned = 0L;
    private Long goldSpent = 0L;
    private Integer mana = 50;
    private Integer maxMana = 100;
    private Integer streak = 0;
    private Integer maxStreak = 0;
    private String lastStreakDate = "";
    private Integer shields = 0;
    private Integer totalHabsDone = 0;
    private Integer totalPhotos = 0;
    private Integer bossKills = 0;
    private Integer missionsCompleted = 0;
    private Double betAmount = 0.0;
    private Double fineBalance = 0.0;
    private Double finesPaid = 0.0;
    private String equippedTitleId = "tt1";
    private Integer cleanStreak = 0;

    @Column(columnDefinition = "TEXT")
    private String pendingPenalties = "[]";

    // Getters & Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Long getTotalXP() { return totalXP; }
    public void setTotalXP(Long totalXP) { this.totalXP = totalXP; }
    public Long getGold() { return gold; }
    public void setGold(Long gold) { this.gold = gold; }
    public Long getTotalGoldEarned() { return totalGoldEarned; }
    public void setTotalGoldEarned(Long totalGoldEarned) { this.totalGoldEarned = totalGoldEarned; }
    public Long getGoldSpent() { return goldSpent; }
    public void setGoldSpent(Long goldSpent) { this.goldSpent = goldSpent; }
    public Integer getMana() { return mana; }
    public void setMana(Integer mana) { this.mana = mana; }
    public Integer getMaxMana() { return maxMana; }
    public void setMaxMana(Integer maxMana) { this.maxMana = maxMana; }
    public Integer getStreak() { return streak; }
    public void setStreak(Integer streak) { this.streak = streak; }
    public Integer getMaxStreak() { return maxStreak; }
    public void setMaxStreak(Integer maxStreak) { this.maxStreak = maxStreak; }
    public String getLastStreakDate() { return lastStreakDate; }
    public void setLastStreakDate(String lastStreakDate) { this.lastStreakDate = lastStreakDate; }
    public Integer getShields() { return shields; }
    public void setShields(Integer shields) { this.shields = shields; }
    public Integer getTotalHabsDone() { return totalHabsDone; }
    public void setTotalHabsDone(Integer totalHabsDone) { this.totalHabsDone = totalHabsDone; }
    public Integer getTotalPhotos() { return totalPhotos; }
    public void setTotalPhotos(Integer totalPhotos) { this.totalPhotos = totalPhotos; }
    public Integer getBossKills() { return bossKills; }
    public void setBossKills(Integer bossKills) { this.bossKills = bossKills; }
    public Integer getMissionsCompleted() { return missionsCompleted; }
    public void setMissionsCompleted(Integer missionsCompleted) { this.missionsCompleted = missionsCompleted; }
    public Double getBetAmount() { return betAmount; }
    public void setBetAmount(Double betAmount) { this.betAmount = betAmount; }
    public Double getFineBalance() { return fineBalance; }
    public void setFineBalance(Double fineBalance) { this.fineBalance = fineBalance; }
    public Double getFinesPaid() { return finesPaid; }
    public void setFinesPaid(Double finesPaid) { this.finesPaid = finesPaid; }
    public String getEquippedTitleId() { return equippedTitleId; }
    public void setEquippedTitleId(String equippedTitleId) { this.equippedTitleId = equippedTitleId; }
    public Integer getCleanStreak() { return cleanStreak; }
    public void setCleanStreak(Integer cleanStreak) { this.cleanStreak = cleanStreak; }
    public String getPendingPenalties() { return pendingPenalties; }
    public void setPendingPenalties(String pendingPenalties) { this.pendingPenalties = pendingPenalties; }
}
