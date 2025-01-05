package com.mitkov.footballmanager.converters;

import com.mitkov.footballmanager.dto.PlayerResponseDTO;
import com.mitkov.footballmanager.dto.TeamDTO;
import com.mitkov.footballmanager.dto.TeamResponseDTO;
import com.mitkov.footballmanager.models.Player;
import com.mitkov.footballmanager.models.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamConverterTest {

    @Mock
    private PlayerConverter playerConverter;

    @InjectMocks
    private TeamConverter teamConverter;

    @Test
    public void testConvertToTeam() {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("TestTeamDTO");
        teamDTO.setBudget(BigDecimal.valueOf(2000000.0));
        teamDTO.setCommission(5.05);

        Team team = teamConverter.convertToTeam(teamDTO);

        assertNotNull(team);
        assertEquals(team.getName(), teamDTO.getName());
        assertEquals(team.getBudget(), teamDTO.getBudget());
        assertEquals(team.getCommission(), teamDTO.getCommission());
    }

    @Test
    public void testConvertToTeamResponseDTO() {

        Player player = new Player();
        player.setName("PlayerName");
        player.setSurname("PlayerSurname");

        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();
        playerResponseDTO.setName("PlayerName");
        playerResponseDTO.setSurname("PlayerSurname");

        Team team = new Team();
        team.setId(2L);
        team.setName("TestTeam");
        team.setBudget(BigDecimal.valueOf(100000.0));
        team.setCommission(2.0);
        team.setPlayers(Collections.singletonList(player));

        when(playerConverter.convertToPlayerResponseDTO(any())).thenReturn(playerResponseDTO);

        TeamResponseDTO teamResponseDTO = teamConverter.convertToTeamResponseDTO(team);

        assertNotNull(team);
        assertEquals(team.getName(), teamResponseDTO.getName());
        assertEquals(team.getBudget(), teamResponseDTO.getBudget());
        assertEquals(team.getCommission(), teamResponseDTO.getCommission());
        assertEquals(1, teamResponseDTO.getPlayerDTOs().size());
        assertEquals("PlayerName", teamResponseDTO.getPlayerDTOs().get(0).getName());
    }
}
