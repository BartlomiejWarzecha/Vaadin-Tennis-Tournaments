package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import com.VaadinTennisTournaments.application.data.entity.Register.RegisterUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegisterUserRepository extends JpaRepository<RegisterUser, Long> {
    @Query("select a from RegisterUser a " +
            " where lower(a.username) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.email) like lower(concat('%', :searchTerm, '%'))")
    List<RegisterUser> search(@Param("searchTerm") String searchTerm);
    Optional<RegisterUser> findByUsername(String username);
}
