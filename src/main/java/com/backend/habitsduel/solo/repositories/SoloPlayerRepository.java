package com.backend.habitsduel.solo.repositories;

import com.backend.habitsduel.models.User;
import com.backend.habitsduel.solo.models.SoloPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SoloPlayerRepository extends JpaRepository<SoloPlayer, Long> {
    Optional<SoloPlayer> findByUser(User user);
    Optional<SoloPlayer> findByUserId(Long userId);
}