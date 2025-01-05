package com.mitkov.footballmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
public class Team {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name = "team_seq", sequenceName = "team_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Name of team should not be empty")
    private String name;

    @Column(name = "budget")
    @NotNull(message = "Budget must not be null")
    @Min(value = 0, message = "Budget must be at least 0")
    private BigDecimal budget;

    @Column(name = "commission")
    @Min(value = 0, message = "Commission must be at least 0")
    @Max(value = 10, message = "Commission must be no more than 10")
    private Double commission;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<Player> players;
}
