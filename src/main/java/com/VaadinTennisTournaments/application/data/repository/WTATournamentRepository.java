package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPTournament;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTATournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WTATournamentRepository extends JpaRepository<WTATournament, Integer> {

    @Query("select r from WTATournament r " +
        " where lower(r.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.rank) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.description) like lower(concat('%', :searchTerm, '%'))")
    List<WTATournament> search(@Param("searchTerm") String searchTerm);
}



