package ru.job4j.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Passport;

import java.util.Collections;
import java.util.List;

@Service
public class PassportService {

    @Value("${client.app.api-passport-url}")
    private String url;

    @Autowired
    private RestTemplate client;

    public Passport save(Passport passport) {
        return client.postForEntity(
                String.format("%s/save", url),
                passport,
                Passport.class
        ).getBody();
    }

    public boolean update(int id, Passport passport) {
        return client.exchange(
                String.format("%s/update?id=%s", url, id),
                HttpMethod.PUT,
                new HttpEntity<>(passport),
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public boolean delete(int id) {
        return client.exchange(
                String.format("%s/delete?id=%s", url, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public List<Passport> findAll() {
        return getAll(String.format("%s/find", url));
    }

    public List<Passport> findBySeries(int series) {
        return getAll(String.format("%s/find?series=%s", url, series));
    }

    public List<Passport> findUnavailable() {
        return getAll(String.format("%s/find-unavailable", url));
    }

    public List<Passport> findReplaceable() {
        return getAll(String.format("%s/find-replaceable", url));
    }

    private List<Passport> getAll(String url) {
        List<Passport> list = client.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return list == null ? Collections.emptyList() : list;
    }
}