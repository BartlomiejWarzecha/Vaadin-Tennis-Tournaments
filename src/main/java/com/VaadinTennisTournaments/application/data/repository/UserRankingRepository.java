package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.User.UserRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRankingRepository extends JpaRepository<UserRanking, Integer> {

    @Query("select u from UserRanking u " +
        "where lower(u.user) like lower(concat('%', :searchTerm, '%')) " +
        "or lower(u.interest) like lower(concat('%', :searchTerm, '%')) " +
            " or lower(u.tournamentsNumber) like lower(concat('%', :searchTerm, '%')) " +
             " or lower(u.points) like lower(concat('%', :searchTerm, '%')) ")

    List<UserRanking> search(@Param("searchTerm") String searchTerm);
}
