package com.backend.habitsduel.repositories;

import com.backend.habitsduel.models.Habits;
import com.backend.habitsduel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitsRepository extends JpaRepository<Habits, Long> {
    List<Habits> findByUser(User user);
}
