package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.atp.ATP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HowToPlayRepository extends JpaRepository<HowToPlay, Integer> {

    @Query("select a from HowToPlay a " +
        " where lower(a.generalRulesDescription) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.usersDescription) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.predictionDescription) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.punctationDescription) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.resultsDescription) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.rankingDescription) like lower(concat('%', :searchTerm, '%'))" )
    List<HowToPlay> search(@Param("searchTerm") String searchTerm);
}
