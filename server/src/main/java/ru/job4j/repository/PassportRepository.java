package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.domain.Passport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

    List<Passport> findAll();

    List<Passport> findBySeries(@NotBlank @Size(min = 8, max = 8) String series);

    List<Passport> findByCreatedBefore(Timestamp date);
}