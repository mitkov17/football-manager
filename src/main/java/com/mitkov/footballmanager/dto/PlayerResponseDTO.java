package com.mitkov.footballmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerResponseDTO {

    private Long id;

    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private Integer experience;
}
