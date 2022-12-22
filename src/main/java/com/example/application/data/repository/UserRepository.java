package com.example.application.data.repository;

import com.example.application.data.entity.User.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u " +
        "where lower(u.Nickname) like lower(concat('%', :searchTerm, '%')) ")
    List<User> search(@Param("searchTerm") String searchTerm);
}
