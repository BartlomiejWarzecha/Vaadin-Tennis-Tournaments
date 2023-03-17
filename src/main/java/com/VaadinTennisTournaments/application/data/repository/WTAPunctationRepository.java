package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.wta.WTAPunctation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WTAPunctationRepository extends JpaRepository<WTAPunctation, Integer> {

    @Query("select p from WTAPunctation p " +
        " where lower(p.wtaTournament) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.user) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(p.stage) like lower(concat('%', :searchTerm, '%'))" )
    List<WTAPunctation> search(@Param("searchTerm") String searchTerm);
}
