package com.example.application.data.repository;

import com.example.application.data.entity.User.Interests;
import com.example.application.data.entity.User.Status;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
