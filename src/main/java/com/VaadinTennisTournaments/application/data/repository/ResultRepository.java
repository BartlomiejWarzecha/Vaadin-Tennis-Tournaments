package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.Result.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("select r from Result r " +
        " where lower(r.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.winner) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.rank) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(r.interest) like lower(concat('%', :searchTerm, '%'))")
    List<Result> search(@Param("searchTerm") String searchTerm);
}
