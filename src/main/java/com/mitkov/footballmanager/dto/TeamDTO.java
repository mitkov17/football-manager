package com.mitkov.footballmanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TeamDTO {

    @NotNull(message = "Name of team should not be empty")
    private String name;

    @NotNull(message = "Budget must not be null")
    @Min(value = 0, message = "Budget must be at least 0")
    private BigDecimal budget;

    @Min(value = 0, message = "Commission must be at least 0")
    @Max(value = 10, message = "Commission must be no more than 10")
    private Double commission;

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
}
