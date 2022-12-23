package com.example.application.data.generator;

import com.example.application.data.entity.Stage;
import com.example.application.data.entity.WTA.WTA;
import com.example.application.data.repository.StageRepository;
import com.example.application.data.repository.WTARepository;
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
public class WTADataGenerator {

    @Bean
    public CommandLineRunner loadWTAdata(WTARepository wtaRepository, StageRepository stageRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (wtaRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            List<Stage> stages = stageRepository
                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
                            .map(Stage::new).collect(Collectors.toList()));

            logger.info("... generating 50 User entities...");
            ExampleDataGenerator<WTA> wtaGenerator = new ExampleDataGenerator<>(WTA.class,
                    LocalDateTime.now());
            wtaGenerator.setData(WTA::setNickname, DataType.BOOK_TITLE);
            wtaGenerator.setData(WTA::setWTATournament, DataType.BOOK_GENRE);
            wtaGenerator.setData(WTA::setPlayer, DataType.LAST_NAME);


            Random r = new Random(seed);
            List<WTA> wtas = wtaGenerator.create(1, seed).stream().peek(wta -> {
                wta.setStage(stages.get(r.nextInt(stages.size())));
            }).collect(Collectors.toList());

            wtaRepository.saveAll(wtas);

            logger.info("Generated demo data");
        };
    }

}
