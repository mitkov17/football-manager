package com.mitkov.footballmanager.converters;

import com.mitkov.footballmanager.dao.TeamDAO;
import com.mitkov.footballmanager.dto.PlayerDTO;
import com.mitkov.footballmanager.dto.PlayerResponseDTO;
import com.mitkov.footballmanager.models.Player;
import com.mitkov.footballmanager.models.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerConverterTest {

    @Mock
    private TeamDAO teamDAO;

    @InjectMocks
    private PlayerConverter playerConverter;

    @Test
    public void testConvertToPlayer() {

        Team team = new Team();
        team.setId(5L);
        team.setName("TestTeam");

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName("TestName");
        playerDTO.setSurname("TestSurname");
        playerDTO.setDateOfBirth(LocalDate.of(2001, 10, 10));
        playerDTO.setExperience(20);
        playerDTO.setTeamId(5L);

        when(teamDAO.getTeam(anyLong())).thenReturn(team);

        Player player = playerConverter.convertToPlayer(playerDTO);

        assertNotNull(player);
        assertEquals(playerDTO.getName(), player.getName());
        assertEquals(playerDTO.getSurname(), player.getSurname());
        assertEquals(playerDTO.getDateOfBirth(), player.getDateOfBirth());
        assertEquals(playerDTO.getExperience(), player.getExperience());
        assertEquals(team, player.getTeam());
    }

    @Test
    public void testConvertToPlayerResponseDTO() {
        Player player = new Player();

        player.setId(6L);
        player.setName("TestResponseName");
        player.setSurname("TestResponseSurname");
        player.setDateOfBirth(LocalDate.of(2000, 8, 15));
        player.setExperience(12);

        PlayerResponseDTO playerResponseDTO = playerConverter.convertToPlayerResponseDTO(player);

        assertNotNull(playerResponseDTO);
        assertEquals(playerResponseDTO.getId(), player.getId());
        assertEquals(playerResponseDTO.getName(), player.getName());
        assertEquals(playerResponseDTO.getSurname(), player.getSurname());
        assertEquals(playerResponseDTO.getDateOfBirth(), player.getDateOfBirth());
        assertEquals(playerResponseDTO.getExperience(), player.getExperience());
    }
}
