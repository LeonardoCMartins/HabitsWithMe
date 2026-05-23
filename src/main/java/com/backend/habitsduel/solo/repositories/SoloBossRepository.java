package com.backend.habitsduel.solo.repositories;

import com.backend.habitsduel.solo.models.SoloBoss;
import com.backend.habitsduel.solo.models.SoloPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SoloBossRepository extends JpaRepository<SoloBoss, Long> {
    Optional<SoloBoss> findBySoloPlayer(SoloPlayer soloPlayer);
}