package com.VaadinTennisTournaments.application.data.generator;

import com.VaadinTennisTournaments.application.data.repository.StageRepository;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class ATPWTADataGenerator {

    @Bean
    public CommandLineRunner loadATPWTAData(StageRepository stageRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());


            List<Stage> stages = stageRepository
                    .saveAll(Stream.of("1/256", "1/128", "1/64", "1/32", "1/16", "1/8", "QF", "SF", "F")
                            .map(Stage::new).collect(Collectors.toList()));

            logger.info("... generating  stages for WTA/ATP entities...");




        };
    }

}
