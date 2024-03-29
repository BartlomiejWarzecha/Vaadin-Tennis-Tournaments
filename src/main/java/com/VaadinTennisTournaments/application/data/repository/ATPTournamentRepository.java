package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.atp.ATPTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ATPTournamentRepository extends JpaRepository<ATPTournament, Integer> {

    @Query("select r from ATPTournament r " +
        " where lower(r.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.rank) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.description) like lower(concat('%', :searchTerm, '%'))")
    List<ATPTournament> search(@Param("searchTerm") String searchTerm);
}