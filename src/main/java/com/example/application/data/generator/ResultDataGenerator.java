package com.example.application.data.generator;

import com.example.application.data.entity.Interests;
import com.example.application.data.entity.Rank;
import com.example.application.data.repository.RankRepository;
import com.example.application.data.repository.ResultRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class ResultDataGenerator {

    @Bean
    public CommandLineRunner loadResultData( ResultRepository resultRepository,
                                          RankRepository rankRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (resultRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            List<Rank> ranks = rankRepository.saveAll(Stream.of("Grand Slam", "100", "500", "250", "Challenger")
                    .map(Rank::new).collect(Collectors.toList()));
        };
    }

}
