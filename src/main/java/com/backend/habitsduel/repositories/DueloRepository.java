package com.backend.habitsduel.repositories;

import com.backend.habitsduel.models.Duelo;
import com.backend.habitsduel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DueloRepository extends JpaRepository<Duelo, Long> {
    Optional<Duelo> findByUser1OrUser2(User user1, User user2);
}
