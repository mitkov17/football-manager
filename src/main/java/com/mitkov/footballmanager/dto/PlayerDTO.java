package com.mitkov.footballmanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerDTO {

    @NotNull(message = "Player's name should not be empty")
    private String name;

    @NotNull(message = "Player's surname should not be empty")
    private String surname;

    @NotNull(message = "Player's date of birth should not be empty")
    private LocalDate dateOfBirth;

    @NotNull(message = "Player's experience should not be empty")
    @Min(value = 0, message = "Experience must be at least 0")
    private Integer experience;

    private Long teamId;
}
