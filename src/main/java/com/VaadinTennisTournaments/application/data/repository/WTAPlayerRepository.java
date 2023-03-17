package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.wta.WTAPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WTAPlayerRepository extends JpaRepository<WTAPlayer, Integer> {

    @Query("select u from ATPPlayer u " +
        "where lower(u.fullname) like lower(concat('%', :searchTerm, '%')) " )
    List<WTAPlayer> search(@Param("searchTerm") String searchTerm);
}
