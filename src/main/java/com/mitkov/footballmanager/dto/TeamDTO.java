package com.mitkov.footballmanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TeamDTO {

    @NotNull(message = "Name of team should not be empty")
    private String name;

    @NotNull(message = "Budget must not be null")
    @Min(value = 0, message = "Budget must be at least 0")
    private BigDecimal budget;

    @Min(value = 0, message = "Commission must be at least 0")
    @Max(value = 10, message = "Commission must be no more than 10")
    private Double commission;
}
