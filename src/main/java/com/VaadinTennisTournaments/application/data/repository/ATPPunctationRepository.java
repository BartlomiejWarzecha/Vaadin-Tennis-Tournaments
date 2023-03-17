package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.atp.ATPPunctation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ATPPunctationRepository extends JpaRepository<ATPPunctation, Integer> {

    @Query("select a from ATPPunctation a " +
        " where lower(a.atpTournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.user) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.stage) like lower(concat('%', :searchTerm, '%'))")
    List<ATPPunctation> search(@Param("searchTerm") String searchTerm);
}
