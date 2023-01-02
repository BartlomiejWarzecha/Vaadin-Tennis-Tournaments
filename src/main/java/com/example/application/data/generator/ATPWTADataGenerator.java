package com.example.application.data.generator;

import com.example.application.data.entity.Stage;
import com.example.application.data.entity.ATP.ATP;
import com.example.application.data.entity.WTA.WTA;
import com.example.application.data.repository.ATPRepository;
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
public class ATPWTADataGenerator {

    @Bean
    public CommandLineRunner loadATPWTAData(ATPRepository atpRepository, StageRepository stageRepository, WTARepository wtaRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (atpRepository.count() != 0L || wtaRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            List<Stage> stages = stageRepository
                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
                            .map(Stage::new).collect(Collectors.toList()));

            ExampleDataGenerator<ATP> atpTournamentGenerator = new ExampleDataGenerator<>(ATP.class,
                    LocalDateTime.now());
            atpTournamentGenerator.setData(ATP::setNickname, DataType.BOOK_TITLE);
            atpTournamentGenerator.setData(ATP::setAtpTournament, DataType.BOOK_GENRE);
            atpTournamentGenerator.setData(ATP::setPlayer, DataType.LAST_NAME);

            ExampleDataGenerator<WTA> wtaTournamentGenerator = new ExampleDataGenerator<>(WTA.class,
                    LocalDateTime.now());
            wtaTournamentGenerator.setData(WTA::setNickname, DataType.BOOK_TITLE);
            wtaTournamentGenerator.setData(WTA::setWtaTournament, DataType.BOOK_GENRE);
            wtaTournamentGenerator.setData(WTA::setPlayer, DataType.LAST_NAME);

            Random r = new Random(seed);
            List<ATP> atps = atpTournamentGenerator.create(1, seed).stream().peek(atp -> {
                atp.setStage(stages.get(r.nextInt(stages.size())));
            }).collect(Collectors.toList());

            List<WTA> wtas = wtaTournamentGenerator.create(1, seed).stream().peek(wta -> {
                wta.setStage(stages.get(r.nextInt(stages.size())));
            }).collect(Collectors.toList());

            atpRepository.saveAll(atps);
            wtaRepository.saveAll(wtas);

        };
    }

}
