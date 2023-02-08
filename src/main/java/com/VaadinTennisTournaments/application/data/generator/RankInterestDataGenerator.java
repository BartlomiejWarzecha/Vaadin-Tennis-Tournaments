package com.VaadinTennisTournaments.application.data.generator;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.repository.InterestsRepository;
import com.VaadinTennisTournaments.application.data.repository.RankRepository;
import com.VaadinTennisTournaments.application.data.repository.WTAResultRepository;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class RankInterestDataGenerator {

    @Bean
    public CommandLineRunner loadResultData(WTAResultRepository WTAResultRepository,
                                            RankRepository rankRepository, InterestsRepository interestsRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (rankRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            int seed = 123;

            List<Rank> ranks = rankRepository.saveAll(Stream.of("Grand Slam", "100", "500", "250", "Challenger")
                    .map(Rank::new).collect(Collectors.toList()));

            List<Interests> interests = interestsRepository.saveAll(Stream.of("ATP", "WTA", "ATP/WTA")
                    .map(Interests::new).collect(Collectors.toList()));


        };
    }

}