package com.jinaz.learning.rest;

import com.jinaz.learning.rest.Model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBLoader {

    private static final Logger log = LoggerFactory.getLogger(DBLoader.class);

    @Bean
    CommandLineRunner initDatabase(ChatRepository repository, InsertionRepository insertionRepository) {

        return args -> {
            log.info("MessageLogger: Add " + repository.save(new User("Albert", "Anon", "hello message 1")));
            log.info("MessageLogger: Add  " + repository.save(new User("B", "B", "restponse B")));

            insertionRepository.save(new Insertion("completed DB insert", Status.COMPLETED));
            insertionRepository.save(new Insertion("db insert in progress", Status.IN_PROGRESS));

            insertionRepository.findAll().forEach(insert -> {
                log.info("Insertrion Logger: Add " + insert);
            });
        };
    }
}
