package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.atp.ATPResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ATPResultRepository extends JpaRepository<ATPResult, Integer> {

    @Query("select r from ATPResult r " +
        " where lower(r.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.winner) like lower(concat('%', :searchTerm, '%'))" )
    List<ATPResult> search(@Param("searchTerm") String searchTerm);
}
