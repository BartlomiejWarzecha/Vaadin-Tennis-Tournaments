package com.example.application.data.generator;

import com.example.application.data.entity.Stage;
import com.example.application.data.entity.ATP.ATP;
import com.example.application.data.repository.ATPRepository;
import com.example.application.data.repository.StageRepository;
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
public class ATPDataGenerator {

    @Bean
    public CommandLineRunner loadATPData(ATPRepository atpRepository, StageRepository stageRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (atpRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            List<Stage> stages = stageRepository
                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
                            .map(Stage::new).collect(Collectors.toList()));

            logger.info("... generating 50 User entities...");
            ExampleDataGenerator<ATP> wtaGenerator = new ExampleDataGenerator<>(ATP.class,
                    LocalDateTime.now());
            wtaGenerator.setData(ATP::setNickname, DataType.BOOK_TITLE);
            wtaGenerator.setData(ATP::setAtpTournament, DataType.BOOK_GENRE);
            wtaGenerator.setData(ATP::setPlayer, DataType.LAST_NAME);


            Random r = new Random(seed);
            List<ATP> atps = wtaGenerator.create(1, seed).stream().peek(wta -> {
                wta.setStage(stages.get(r.nextInt(stages.size())));
            }).collect(Collectors.toList());

            atpRepository.saveAll(atps);

            logger.info("Generated demo data");
        };
    }

}
