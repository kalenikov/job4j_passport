package ru.job4j.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KafkaTemplate<Integer, MailMessage> kafkaTemplate;

    @Autowired
    private PassportRepository repo;

    @Value("${passport.app.validity-years}")
    private Integer validityYears;

    @Value("${spring.kafka.default-topic}")
    private String topic;

    @Scheduled(fixedDelayString = "${scheduling.passport.interval}")
    public void notifyExpired() {
        log.debug("notifyExpired");
        repo.findByCreatedBefore(Timestamp.valueOf(LocalDateTime.now().minusYears(validityYears)))
                .stream()
                .map(passport -> new MailMessage(passport.getSeries()))
                .forEach(message -> kafkaTemplate.send(topic, message));
    }
}
