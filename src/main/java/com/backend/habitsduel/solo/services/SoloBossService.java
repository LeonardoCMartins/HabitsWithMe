package com.backend.habitsduel.solo.services;

import com.backend.habitsduel.solo.dtos.SoloBossDto;
import com.backend.habitsduel.solo.dtos.SoloPlayerDto;
import com.backend.habitsduel.solo.models.SoloBoss;
import com.backend.habitsduel.solo.models.SoloPlayer;
import com.backend.habitsduel.solo.repositories.SoloBossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;

@Service
public class SoloBossService {

    @Autowired private SoloBossRepository soloBossRepository;
    @Autowired private SoloPlayerService soloPlayerService;

    // HP por rank (igual ao frontend)
    private static final Map<String, Integer> BOSS_HP = Map.of(
        "E", 600, "D", 1000, "C", 2000, "B", 4000,
        "A", 8000, "S", 15000, "SS", 30000, "SSS", 60000
    );
    // XP de recompensa por matar o boss
    private static final Map<String, Long> BOSS_XP = Map.of(
        "E", 500L, "D", 1500L, "C", 4000L, "B", 12000L,
        "A", 30000L, "S", 80000L, "SS", 200000L, "SSS", 500000L
    );
    private static final Map<String, Long> BOSS_GOLD = Map.of(
        "E", 50L, "D", 150L, "C", 400L, "B", 1200L,
        "A", 3000L, "S", 8000L, "SS", 20000L, "SSS", 50000L
    );

    public SoloBossDto getBoss(Long userId) {
        SoloPlayer player = soloPlayerService.findPlayer(userId);
        String weekStart = getWeekStart();
        String rankName  = calcRank(player.getTotalXP());

        SoloBoss boss = soloBossRepository.findBySoloPlayer(player).orElseGet(() -> {
            SoloBoss b = new SoloBoss();
            b.setSoloPlayer(player);
            return b;
        });

        // Reset semanal ou mudança de rank
        if (!weekStart.equals(boss.getWeekStart()) || !rankName.equals(boss.getRankName())) {
            int hp = BOSS_HP.getOrDefault(rankName, 600);
            boss.setHp(hp);
            boss.setMaxHp(hp);
            boss.setWeekStart(weekStart);
            boss.setDefeated(false);
            boss.setRankName(rankName);
            boss = soloBossRepository.save(boss);
        }

        return new SoloBossDto(boss);
    }

    public SoloBossDto damage(Long userId, Integer dmg) {
        SoloPlayer player = soloPlayerService.findPlayer(userId);
        SoloBoss boss = findBoss(player);
        if (boss.getDefeated()) return new SoloBossDto(boss);

        boss.setHp(Math.max(0, boss.getHp() - dmg));

        if (boss.getHp() == 0) {
            boss.setDefeated(true);
            String rn = boss.getRankName();
            soloPlayerService.bossDefeated(userId, BOSS_XP.getOrDefault(rn, 500L), BOSS_GOLD.getOrDefault(rn, 50L));
        }

        return new SoloBossDto(soloBossRepository.save(boss));
    }

    public SoloBossDto heal(Long userId, Integer hp) {
        SoloPlayer player = soloPlayerService.findPlayer(userId);
        SoloBoss boss = findBoss(player);
        if (!boss.getDefeated()) {
            boss.setHp(Math.min(boss.getMaxHp(), boss.getHp() + hp));
            boss = soloBossRepository.save(boss);
        }
        return new SoloBossDto(boss);
    }

    private SoloBoss findBoss(SoloPlayer player) {
        return soloBossRepository.findBySoloPlayer(player)
            .orElseThrow(() -> new RuntimeException("Boss não encontrado. Chame GET /solo/boss/{userId} primeiro."));
    }

    private String getWeekStart() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toString();
    }

    private String calcRank(Long xp) {
        if (xp >= 1_075_000) return "SSS";
        if (xp >= 618_000)   return "SS";
        if (xp >= 345_000)   return "S";
        if (xp >= 163_000)   return "A";
        if (xp >= 72_500)    return "B";
        if (xp >= 27_500)    return "C";
        if (xp >= 5_000)     return "D";
        return "E";
    }
}
