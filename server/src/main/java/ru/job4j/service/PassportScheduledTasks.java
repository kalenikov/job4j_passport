package ru.job4j.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.domain.MailMessage;
import ru.job4j.repository.PassportRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PassportScheduledTasks {

    private final KafkaTemplate<Integer, MailMessage> kafkaTemplate;

    private final PassportRepository repo;

    @Value("${passport.app.validity-years}")
    private Integer validityYears;

    @Value("${spring.kafka.default-topic}")
    private String topic;

    public PassportScheduledTasks(KafkaTemplate<Integer, MailMessage> kafkaTemplate, PassportRepository repo) {
        this.kafkaTemplate = kafkaTemplate;
        this.repo = repo;
    }

    @Scheduled(fixedDelayString = "${scheduling.passport.interval}")
    public void notifyExpired() {
        log.debug("notifyExpired");
        repo.findByCreatedBefore(Timestamp.valueOf(LocalDateTime.now().minusYears(validityYears)))
                .stream()
                .map(passport -> new MailMessage(passport.getSeries()))
                .forEach(message -> kafkaTemplate.send(topic, message));
    }
}
