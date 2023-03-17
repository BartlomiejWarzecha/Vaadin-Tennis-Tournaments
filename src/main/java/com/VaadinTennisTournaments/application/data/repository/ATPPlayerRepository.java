package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.atp.ATPPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ATPPlayerRepository extends JpaRepository<ATPPlayer, Integer> {

    @Query("select u from ATPPlayer u " +
        "where lower(u.fullname) like lower(concat('%', :searchTerm, '%')) " )
    List<ATPPlayer> search(@Param("searchTerm") String searchTerm);
}
