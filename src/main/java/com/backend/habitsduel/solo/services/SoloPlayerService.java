package com.backend.habitsduel.solo.services;

import com.backend.habitsduel.models.User;
import com.backend.habitsduel.repositories.UserRepository;
import com.backend.habitsduel.solo.dtos.SoloPlayerDto;
import com.backend.habitsduel.solo.models.SoloPlayer;
import com.backend.habitsduel.solo.repositories.SoloPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SoloPlayerService {

    @Autowired private SoloPlayerRepository soloPlayerRepository;
    @Autowired private UserRepository userRepository;

    // Cria ou retorna o SoloPlayer do usuário
    public SoloPlayerDto initPlayer(Long userId) {
        User user = getUser(userId);
        SoloPlayer player = soloPlayerRepository.findByUser(user)
            .orElseGet(() -> {
                SoloPlayer p = new SoloPlayer();
                p.setUser(user);
                return soloPlayerRepository.save(p);
            });
        return new SoloPlayerDto(player);
    }

    public SoloPlayerDto getPlayer(Long userId) {
        return new SoloPlayerDto(findPlayer(userId));
    }

    // Chamado quando hábito é confirmado com foto
    public SoloPlayerDto habitDone(Long userId, Integer xp, Integer gold) {
        SoloPlayer p = findPlayer(userId);
        p.setTotalXP(p.getTotalXP() + xp);
        p.setGold(p.getGold() + gold);
        p.setTotalGoldEarned(p.getTotalGoldEarned() + gold);
        p.setMana(Math.min(p.getMaxMana(), p.getMana() + 20));
        p.setTotalHabsDone(p.getTotalHabsDone() + 1);
        p.setTotalPhotos(p.getTotalPhotos() + 1);
        updateStreak(p);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Chamado quando mau hábito é reportado
    public SoloPlayerDto badHabit(Long userId, Integer xpLoss, Integer goldLoss) {
        SoloPlayer p = findPlayer(userId);
        p.setTotalXP(Math.max(0, p.getTotalXP() - xpLoss));
        p.setGold(Math.max(0, p.getGold() - goldLoss));
        p.setMana(Math.max(0, p.getMana() - 15));
        p.setCleanStreak(0);
        // Multa automática
        if (p.getBetAmount() > 0) {
            p.setFineBalance(p.getFineBalance() + p.getBetAmount());
        }
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Ganhar XP e Gold (missão, quest)
    public SoloPlayerDto gainProgress(Long userId, Long xp, Long gold) {
        SoloPlayer p = findPlayer(userId);
        p.setTotalXP(p.getTotalXP() + xp);
        p.setGold(p.getGold() + gold);
        p.setTotalGoldEarned(p.getTotalGoldEarned() + gold);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Boss derrotado
    public SoloPlayerDto bossDefeated(Long userId, Long xpReward, Long goldReward) {
        SoloPlayer p = findPlayer(userId);
        p.setTotalXP(p.getTotalXP() + xpReward);
        p.setGold(p.getGold() + goldReward);
        p.setTotalGoldEarned(p.getTotalGoldEarned() + goldReward);
        p.setBossKills(p.getBossKills() + 1);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Equipar título
    public SoloPlayerDto equipTitle(Long userId, String titleId) {
        SoloPlayer p = findPlayer(userId);
        p.setEquippedTitleId(titleId);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Aposta
    public SoloPlayerDto setBet(Long userId, Double amount) {
        SoloPlayer p = findPlayer(userId);
        p.setBetAmount(amount);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    public SoloPlayerDto payFine(Long userId) {
        SoloPlayer p = findPlayer(userId);
        p.setFinesPaid(p.getFinesPaid() + p.getFineBalance());
        p.setFineBalance(0.0);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    public SoloPlayerDto resetFine(Long userId) {
        SoloPlayer p = findPlayer(userId);
        p.setFineBalance(0.0);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Penalidades pendentes
    public SoloPlayerDto clearPenalties(Long userId) {
        SoloPlayer p = findPlayer(userId);
        p.setPendingPenalties("[]");
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Comprar recompensa (deduz gold)
    public SoloPlayerDto spendGold(Long userId, Integer cost) {
        SoloPlayer p = findPlayer(userId);
        if (p.getGold() < cost) throw new RuntimeException("Gold insuficiente");
        p.setGold(p.getGold() - cost);
        p.setGoldSpent(p.getGoldSpent() + cost);
        return new SoloPlayerDto(soloPlayerRepository.save(p));
    }

    // Incrementar missões
    public void incrementMissions(SoloPlayer p) {
        p.setMissionsCompleted(p.getMissionsCompleted() + 1);
        soloPlayerRepository.save(p);
    }

    private void updateStreak(SoloPlayer p) {
        String today = LocalDate.now().toString();
        if (!today.equals(p.getLastStreakDate())) {
            p.setStreak(p.getStreak() + 1);
            p.setLastStreakDate(today);
            p.setCleanStreak(p.getCleanStreak() + 1);
            if (p.getStreak() > p.getMaxStreak()) p.setMaxStreak(p.getStreak());
            if (p.getStreak() % 7 == 0) p.setShields(p.getShields() + 1);
        }
    }

    public SoloPlayer findPlayer(Long userId) {
        User user = getUser(userId);
        return soloPlayerRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("SoloPlayer não encontrado. Use /solo/player/init primeiro."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
