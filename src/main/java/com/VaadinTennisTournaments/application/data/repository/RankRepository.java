package com.VaadinTennisTournaments.application.data.repository;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Integer> {

}
