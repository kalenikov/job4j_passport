package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.service.PassportService;

@SpringBootApplication
public class ClientApp implements CommandLineRunner {
    @Autowired
    private PassportService service;

    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println(service.findAll());
    }
}
