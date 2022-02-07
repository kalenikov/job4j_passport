package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Passport;
import ru.job4j.service.PassportService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/passport")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @GetMapping("/find")
    public List<Passport> find(@RequestParam(required = false) String series) {
        return series == null
                ? passportService.findAll()
                : passportService.findBySeries(series);
    }

    @GetMapping("/find-unavailable")
    public List<Passport> findExpired() {
        return passportService.findExpired();
    }

    @GetMapping("/find-replaceable")
    public List<Passport> findAlmostExpired() {
        return passportService.findAlmostExpired();
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Passport create(@Valid @RequestBody Passport passport) {
        return passportService.save(passport);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam Integer id,
                                       @Valid @RequestBody Passport passport) {
        return passportService.update(id, passport)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        return passportService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}