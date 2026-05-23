package com.backend.habitsduel.solo.repositories;

import com.backend.habitsduel.solo.models.SoloReward;
import com.backend.habitsduel.solo.models.SoloPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SoloRewardRepository extends JpaRepository<SoloReward, Long> {
    List<SoloReward> findBySoloPlayer(SoloPlayer soloPlayer);
    Optional<SoloReward> findBySoloPlayerAndRewardId(SoloPlayer soloPlayer, String rewardId);
}