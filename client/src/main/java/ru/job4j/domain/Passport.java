package ru.job4j.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Passport {
    private Integer id;
    private String series;
    private Timestamp created;
}