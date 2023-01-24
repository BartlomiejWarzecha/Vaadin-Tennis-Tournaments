package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.Punctation.Punctation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PunctationRepository extends JpaRepository<Punctation, Integer> {

    @Query("select p from Punctation p " +
        " where lower(p.tournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.nickname) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(p.rank) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(p.interest) like lower(concat('%', :searchTerm, '%'))")
    List<Punctation> search(@Param("searchTerm") String searchTerm);
}
