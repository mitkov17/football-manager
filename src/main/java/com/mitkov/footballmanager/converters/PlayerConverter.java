package com.mitkov.footballmanager.converters;

import com.mitkov.footballmanager.dao.TeamDAO;
import com.mitkov.footballmanager.dto.PlayerDTO;
import com.mitkov.footballmanager.dto.PlayerResponseDTO;
import com.mitkov.footballmanager.exceptions.TeamNotFoundException;
import com.mitkov.footballmanager.models.Player;
import com.mitkov.footballmanager.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerConverter {

    private final TeamDAO teamDAO;

    @Autowired
    public PlayerConverter(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public Player convertToPlayer(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setSurname(playerDTO.getSurname());
        player.setExperience(playerDTO.getExperience());
        player.setDateOfBirth(playerDTO.getDateOfBirth());

        if (playerDTO.getTeamId() != null) {
            Team team = teamDAO.getTeam(playerDTO.getTeamId());
            if (team == null) {
                throw new TeamNotFoundException(playerDTO.getTeamId());
            }
            player.setTeam(team);
        }

        return player;
    }

    public PlayerResponseDTO convertToPlayerResponseDTO(Player player) {
        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        playerResponseDTO.setId(player.getId());
        playerResponseDTO.setName(player.getName());
        playerResponseDTO.setSurname(player.getSurname());
        playerResponseDTO.setDateOfBirth(player.getDateOfBirth().toString());
        playerResponseDTO.setExperience(player.getExperience());

        return playerResponseDTO;
    }
}
