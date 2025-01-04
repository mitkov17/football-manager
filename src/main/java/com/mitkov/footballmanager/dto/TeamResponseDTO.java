package com.mitkov.footballmanager.dto;

import java.math.BigDecimal;
import java.util.List;

public class TeamResponseDTO {

    private String name;

    private BigDecimal budget;

    private Double commission;

    private List<PlayerResponseDTO> playerDTOs;

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

    public List<PlayerResponseDTO> getPlayerDTOs() {
        return playerDTOs;
    }

    public void setPlayerDTOs(List<PlayerResponseDTO> playerDTOs) {
        this.playerDTOs = playerDTOs;
    }
}
