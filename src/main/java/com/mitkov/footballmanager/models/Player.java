package com.mitkov.footballmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Player's name should not be empty")
    private String name;

    @Column(name = "surname")
    @NotNull(message = "Player's surname should not be empty")
    private String surname;

    @Column(name = "date_of_birth")
    @NotNull(message = "Player's date of birth should not be empty")
    private LocalDate dateOfBirth;

    @Column(name = "experience")
    @NotNull(message = "Player's experience should not be empty")
    @Min(value = 0, message = "Experience must be at least 0")
    private Integer experience;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
