package com.example.application.data.repository;

import com.example.application.data.entity.ATP.ATP;
import com.example.application.data.entity.WTA.WTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ATPRepository extends JpaRepository<ATP, Integer> {

    @Query("select a from ATP a " +
        " where lower(a.nickname) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.atpTournament) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(a.player) like lower(concat('%', :searchTerm, '%'))")
    List<ATP> search(@Param("searchTerm") String searchTerm);
}
