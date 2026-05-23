package com.backend.habitsduel.solo.services;

import com.backend.habitsduel.solo.dtos.SoloDailyQuestDto;
import com.backend.habitsduel.solo.dtos.SoloMissionDto;
import com.backend.habitsduel.solo.models.SoloDailyQuest;
import com.backend.habitsduel.solo.models.SoloMission;
import com.backend.habitsduel.solo.models.SoloPlayer;
import com.backend.habitsduel.solo.repositories.SoloDailyQuestRepository;
import com.backend.habitsduel.solo.repositories.SoloMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SoloMissionService {

    @Autowired private SoloMissionRepository missionRepo;
    @Autowired private SoloDailyQuestRepository questRepo;
    @Autowired private SoloPlayerService soloPlayerService;
    @Autowired private SoloBossService soloBossService;

    // ── MISSIONS ──

    public List<SoloMissionDto> getMissions(Long userId) {
        SoloPlayer p = soloPlayerService.findPlayer(userId);
        return missionRepo.findBySoloPlayer(p).stream().map(SoloMissionDto::new).toList();
    }

    public SoloMissionDto createMission(Long userId, String title, String difficulty, Integer xp, Integer gold) {
        SoloPlayer p = soloPlayerService.findPlayer(userId);
        SoloMission m = new SoloMission();
        m.setSoloPlayer(p);
        m.setTitle(title);
        m.setDifficulty(difficulty);
        m.setXpReward(xp);
        m.setGoldReward(gold);
        m.setCompleted(false);
        return new SoloMissionDto(missionRepo.save(m));
    }

    public SoloMissionDto completeMission(Long missionId, Long userId) {
        SoloMission m = missionRepo.findById(missionId)
            .orElseThrow(() -> new RuntimeException("Missão não encontrada"));
        if (m.getCompleted()) return new SoloMissionDto(m);

        m.setCompleted(true);
        missionRepo.save(m);

        // Ganhar XP e Gold
        soloPlayerService.gainProgress(userId, m.getXpReward().longValue(), m.getGoldReward().longValue());

        // Causar dano ao boss
        soloBossService.damage(userId, 200);

        // Incrementar contador de missões
        soloPlayerService.incrementMissions(soloPlayerService.findPlayer(userId));

        return new SoloMissionDto(m);
    }

    // ── DAILY QUESTS ──

    private static final List<String[]> QUEST_POOL = List.of(
        new String[]{"Completar 1 hábito hoje", "30"},
        new String[]{"Beber 2L de água", "25"},
        new String[]{"10 min de meditação", "35"},
        new String[]{"2h sem redes sociais", "40"},
        new String[]{"Ler por 15 minutos", "20"},
        new String[]{"Fazer 50 flexões", "30"},
        new String[]{"Dormir antes da meia-noite", "25"},
        new String[]{"Comer uma fruta", "15"},
        new String[]{"Fazer uma caminhada", "30"},
        new String[]{"Escrever 3 gratidões", "20"}
    );

    public List<SoloDailyQuestDto> getOrGenerateQuests(Long userId) {
        SoloPlayer p = soloPlayerService.findPlayer(userId);
        LocalDate today = LocalDate.now();

        List<SoloDailyQuest> existing = questRepo.findBySoloPlayerAndDate(p, today);
        if (!existing.isEmpty()) {
            return existing.stream().map(SoloDailyQuestDto::new).toList();
        }

        // Gera 3 quests aleatórias para hoje
        List<String[]> pool = new ArrayList<>(QUEST_POOL);
        Collections.shuffle(pool);
        List<SoloDailyQuest> quests = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SoloDailyQuest q = new SoloDailyQuest();
            q.setSoloPlayer(p);
            q.setName(pool.get(i)[0]);
            q.setXpReward(Integer.parseInt(pool.get(i)[1]));
            q.setDone(false);
            q.setDate(today);
            quests.add(questRepo.save(q));
        }

        return quests.stream().map(SoloDailyQuestDto::new).toList();
    }

    public SoloDailyQuestDto completeQuest(Long questId, Long userId) {
        SoloDailyQuest q = questRepo.findById(questId)
            .orElseThrow(() -> new RuntimeException("Quest não encontrada"));
        if (q.getDone()) return new SoloDailyQuestDto(q);

        q.setDone(true);
        questRepo.save(q);

        soloPlayerService.gainProgress(userId, q.getXpReward().longValue(), 0L);

        return new SoloDailyQuestDto(q);
    }
}
