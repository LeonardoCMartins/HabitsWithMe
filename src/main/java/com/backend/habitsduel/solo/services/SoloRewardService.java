package com.backend.habitsduel.solo.services;

import com.backend.habitsduel.solo.dtos.SoloRewardDto;
import com.backend.habitsduel.solo.models.SoloPlayer;
import com.backend.habitsduel.solo.models.SoloReward;
import com.backend.habitsduel.solo.repositories.SoloRewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoloRewardService {

    @Autowired private SoloRewardRepository rewardRepo;
    @Autowired private SoloPlayerService soloPlayerService;

    public List<SoloRewardDto> getRewards(Long userId) {
        SoloPlayer p = soloPlayerService.findPlayer(userId);
        return rewardRepo.findBySoloPlayer(p).stream().map(SoloRewardDto::new).toList();
    }

    // Sincroniza as recompensas pré-definidas (chamado no init)
    public void syncDefaultRewards(SoloPlayer p, List<SoloRewardDto> defaults) {
        for (SoloRewardDto def : defaults) {
            if (rewardRepo.findBySoloPlayerAndRewardId(p, def.rewardId()).isEmpty()) {
                SoloReward r = new SoloReward();
                r.setSoloPlayer(p);
                r.setRewardId(def.rewardId());
                r.setName(def.name());
                r.setTier(def.tier());
                r.setIcon(def.icon());
                r.setCost(def.cost());
                r.setPurchased(false);
                r.setCustom(false);
                rewardRepo.save(r);
            }
        }
    }

    public SoloRewardDto addCustomReward(Long userId, String name, String tier, String icon, Integer cost) {
        SoloPlayer p = soloPlayerService.findPlayer(userId);
        SoloReward r = new SoloReward();
        r.setSoloPlayer(p);
        r.setName(name);
        r.setTier(tier);
        r.setIcon(icon != null ? icon : "🎁");
        r.setCost(cost);
        r.setPurchased(false);
        r.setCustom(true);
        return new SoloRewardDto(rewardRepo.save(r));
    }

    public SoloRewardDto buyReward(Long rewardId, Long userId) {
        SoloReward r = rewardRepo.findById(rewardId)
            .orElseThrow(() -> new RuntimeException("Recompensa não encontrada"));
        if (r.getPurchased()) return new SoloRewardDto(r);

        // Deduz gold do player (lança exceção se insuficiente)
        soloPlayerService.spendGold(userId, r.getCost());

        r.setPurchased(true);
        return new SoloRewardDto(rewardRepo.save(r));
    }
}
