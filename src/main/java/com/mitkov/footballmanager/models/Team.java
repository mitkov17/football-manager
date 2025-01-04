package com.mitkov.footballmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "team")
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

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
