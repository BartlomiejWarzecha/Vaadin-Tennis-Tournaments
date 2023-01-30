package com.VaadinTennisTournaments.application.data.generator;

import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
import com.VaadinTennisTournaments.application.data.repository.InterestsRepository;
import com.VaadinTennisTournaments.application.data.repository.RankRepository;
import com.VaadinTennisTournaments.application.data.repository.WTAResultRepository;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.vaadin.artur.exampledata.DataType;
import org.vaadin.artur.exampledata.ExampleDataGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class ResultDataGenerator {

    @Bean
    public CommandLineRunner loadResultData(WTAResultRepository WTAResultRepository,
                                            RankRepository rankRepository, InterestsRepository interestsRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (WTAResultRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

            int seed = 123;

            List<Rank> ranks = rankRepository.saveAll(Stream.of("Grand Slam", "100", "500", "250", "Challenger")
                    .map(Rank::new).collect(Collectors.toList()));

            List<Interests> interests = interestsRepository.saveAll(Stream.of("ATP", "WTA", "ATP/WTA")
                    .map(Interests::new).collect(Collectors.toList()));

            logger.info("... generating 5 WTAResult entities...");
            ExampleDataGenerator<WTAResult> resultExampleDataGenerator = new ExampleDataGenerator<>(WTAResult.class,
                    LocalDateTime.now());
            resultExampleDataGenerator.setData(WTAResult::setTournament, DataType.FIRST_NAME);
            resultExampleDataGenerator.setData(WTAResult::setWinner, DataType.EMAIL);

            Random r = new Random(seed);
            List<WTAResult> WTAResults = resultExampleDataGenerator.create(5, seed).stream().peek(result -> {
                result.setRank(ranks.get(r.nextInt(ranks.size())));
                result.setInterest(interests.get(r.nextInt(interests.size())));
            }).collect(Collectors.toList());

            WTAResultRepository.saveAll(WTAResults);

        };
    }

}
