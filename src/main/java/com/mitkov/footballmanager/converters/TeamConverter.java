package com.mitkov.footballmanager.converters;

import com.mitkov.footballmanager.dto.TeamDTO;
import com.mitkov.footballmanager.dto.TeamResponseDTO;
import com.mitkov.footballmanager.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamConverter {

    private final PlayerConverter playerConverter;

    @Autowired
    public TeamConverter(PlayerConverter playerConverter) {
        this.playerConverter = playerConverter;
    }

    public Team convertToTeam(TeamDTO teamDTO) {
        Team team = new Team();
        team.setName(teamDTO.getName());
        team.setBudget(teamDTO.getBudget());
        team.setCommission(teamDTO.getCommission());

        return team;
    }

    public TeamResponseDTO convertToTeamResponseDTO(Team team) {
        TeamResponseDTO teamResponseDTO = new TeamResponseDTO();
        teamResponseDTO.setName(team.getName());
        teamResponseDTO.setBudget(team.getBudget());
        teamResponseDTO.setCommission(team.getCommission());
        teamResponseDTO.setPlayerDTOs(
                team.getPlayers().stream()
                        .map(playerConverter::convertToPlayerResponseDTO)
                        .collect(Collectors.toList())
        );
        return teamResponseDTO;
    }
}
