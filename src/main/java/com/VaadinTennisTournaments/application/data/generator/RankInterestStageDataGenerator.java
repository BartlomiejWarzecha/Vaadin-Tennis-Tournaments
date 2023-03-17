package com.VaadinTennisTournaments.application.data.generator;

import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.repository.InterestsRepository;
import com.VaadinTennisTournaments.application.data.repository.RankRepository;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.VaadinTennisTournaments.application.data.repository.StageRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class RankInterestStageDataGenerator {
    @Bean
    public CommandLineRunner loadResultData(StageRepository stageRepository, RankRepository rankRepository, InterestsRepository interestsRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (rankRepository.count() != 0L && stageRepository.count() != 0L && interestsRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            int seed = 123;

            if (rankRepository.count() == 0L) {
                List<Rank> ranks = rankRepository.saveAll(Stream.of("Grand Slam", "1000", "500", "250", "125", "Challenger")
                        .map(Rank::new).collect(Collectors.toList()));
            }
            if (stageRepository.count() == 0L) {
                List<Stage> stages = stageRepository.saveAll(Stream.of("Round 1", "Round 2", "Round 3", "Round 4", "Quater-Final", "Semi-Final", "Final")
                        .map(Stage::new).collect(Collectors.toList()));
            }
            if (interestsRepository.count() == 0L) {
                List<Interests> interests = interestsRepository.saveAll(Stream.of("ATP", "WTA", "ATP/WTA")
                        .map(Interests::new).collect(Collectors.toList()));
            }
        };
    }
}
