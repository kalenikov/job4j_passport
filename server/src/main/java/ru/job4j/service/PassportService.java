package ru.job4j.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Passport;
import ru.job4j.repository.PassportRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {

    private final PassportRepository repo;

    @Value("${passport.app.validity-years}")
    private Integer validityYears;

    public PassportService(PassportRepository repo) {
        this.repo = repo;
    }

    public List<Passport> findAll() {
        return repo.findAll();
    }

    public List<Passport> findBySeries(String series) {
        return repo.findBySeries(series);
    }

    public List<Passport> findExpired() {
        return repo.findByCreatedBefore(Timestamp.valueOf(LocalDateTime.now().minusYears(validityYears)));
    }

    public List<Passport> findAlmostExpired() {
        return repo.findByCreatedBefore(Timestamp.valueOf(LocalDateTime.now().minusYears(validityYears).plusMonths(3)));
    }

    public Passport save(Passport passport) {
        return repo.save(passport);
    }

    public boolean update(int id, Passport passport) {
        Optional<Passport> current = repo.findById(id);
        if (current.isPresent()) {
            passport.setId(id);
            repo.save(passport);
            return true;
        }
        return false;
    }

    public boolean delete(int id) {
        Optional<Passport> current = this.repo.findById(id);
        if (current.isPresent()) {
            repo.delete(current.get());
            return true;
        }
        return false;
    }
}
