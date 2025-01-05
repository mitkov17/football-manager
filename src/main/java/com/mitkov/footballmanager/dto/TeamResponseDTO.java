package com.mitkov.footballmanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TeamResponseDTO {

    private Long id;

    private String name;

    private BigDecimal budget;

    private Double commission;

    private List<PlayerResponseDTO> playerDTOs;
}
