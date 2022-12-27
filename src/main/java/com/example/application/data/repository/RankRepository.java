package com.example.application.data.repository;

import com.example.application.data.entity.Rank;
import com.example.application.data.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer> {

}
