package com.backend.habitsduel.repositories;

import com.backend.habitsduel.models.Historico;
import com.backend.habitsduel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    Optional<Historico> findByUserAndDate(User user, LocalDate date);
    List<Historico> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
