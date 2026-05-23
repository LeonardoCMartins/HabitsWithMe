package com.backend.habitsduel.solo.repositories;

import com.backend.habitsduel.solo.models.SoloDailyQuest;
import com.backend.habitsduel.solo.models.SoloPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SoloDailyQuestRepository extends JpaRepository<SoloDailyQuest, Long> {
    List<SoloDailyQuest> findBySoloPlayerAndDate(SoloPlayer soloPlayer, LocalDate date);
    void deleteBySoloPlayerAndDateBefore(SoloPlayer soloPlayer, LocalDate date);
}