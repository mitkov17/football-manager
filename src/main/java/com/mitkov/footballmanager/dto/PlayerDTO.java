package com.mitkov.footballmanager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
