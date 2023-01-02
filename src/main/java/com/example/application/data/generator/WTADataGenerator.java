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
    public CommandLineRunner loadWTAdata(WTARepository wtaRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (wtaRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }

        };
    }

}
