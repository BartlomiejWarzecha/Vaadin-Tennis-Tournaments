package com.example.application.data.repository;

import com.example.application.data.entity.WTA.WTA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WTARepository extends JpaRepository<WTA, Integer> {

    @Query("select w from WTA w " +
        " where lower(w.Nickname) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(w.WTATournament) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(w.Player) like lower(concat('%', :searchTerm, '%'))")
    List<WTA> search(@Param("searchTerm") String searchTerm);
}
