package com.backend.habitsduel.solo.repositories;

import com.backend.habitsduel.solo.models.SoloMission;
import com.backend.habitsduel.solo.models.SoloPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SoloMissionRepository extends JpaRepository<SoloMission, Long> {
    List<SoloMission> findBySoloPlayer(SoloPlayer soloPlayer);
    List<SoloMission> findBySoloPlayerAndCompleted(SoloPlayer soloPlayer, Boolean completed);
}