package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u " +
        "where lower(u.nickname) like lower(concat('%', :searchTerm, '%')) " )
    List<User> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT u FROM User u WHERE u.interests != '7'")
    List<User> findAllAtpUsers();

    @Query("SELECT u FROM User u WHERE u.interests != '6'")
    List<User> findAllWtaUsers();
}
