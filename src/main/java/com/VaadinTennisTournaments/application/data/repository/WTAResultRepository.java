package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WTAResultRepository extends JpaRepository<WTAResult, Integer> {

    @Query("select r from WTAResult r " +
        " where lower(r.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.winner) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.rank) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.interest) like lower(concat('%', :searchTerm, '%'))")
    List<WTAResult> search(@Param("searchTerm") String searchTerm);
}
