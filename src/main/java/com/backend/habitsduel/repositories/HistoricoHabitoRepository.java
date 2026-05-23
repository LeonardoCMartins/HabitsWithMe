package com.backend.habitsduel.repositories;

import com.backend.habitsduel.models.Habits;
import com.backend.habitsduel.models.Historico;
import com.backend.habitsduel.models.HistoricoHabito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoricoHabitoRepository extends JpaRepository<HistoricoHabito, Long> {
    List<HistoricoHabito> findByHistorico(Historico historico);
    Optional<HistoricoHabito> findByHistoricoAndHabito(Historico historico, Habits habito);
}
