package com.mitkov.footballmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitkov.footballmanager.converters.TeamConverter;
import com.mitkov.footballmanager.dao.TeamDAO;
import com.mitkov.footballmanager.dto.TeamDTO;
import com.mitkov.footballmanager.dto.TeamResponseDTO;
import com.mitkov.footballmanager.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamConverter teamConverter;

    @MockBean
    private TeamDAO teamDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTeams() throws Exception {
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team1");
        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team2");

        TeamResponseDTO teamResponseDTO1 = new TeamResponseDTO();
        teamResponseDTO1.setName("Team1");
        TeamResponseDTO teamResponseDTO2 = new TeamResponseDTO();
        teamResponseDTO2.setName("Team2");

        when(teamDAO.getAllTeams()).thenReturn(Arrays.asList(team1, team2));
        when(teamConverter.convertToTeamResponseDTO(team1)).thenReturn(teamResponseDTO1);
        when(teamConverter.convertToTeamResponseDTO(team2)).thenReturn(teamResponseDTO2);

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Team1"))
                .andExpect(jsonPath("$[1].name").value("Team2"));
    }

    @Test
    public void testGetTeam() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setName("Team1");

        TeamResponseDTO teamResponseDTO = new TeamResponseDTO();
        teamResponseDTO.setName("Team1");

        when(teamDAO.getTeam(1L)).thenReturn(team);
        when(teamConverter.convertToTeamResponseDTO(team)).thenReturn(teamResponseDTO);

        mockMvc.perform(get("/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Team1"));
    }

    @Test
    public void testCreateTeam() throws Exception {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Team1");
        teamDTO.setBudget(BigDecimal.valueOf(1000));
        teamDTO.setCommission(5.0);

        Team team = new Team();
        team.setName("Team1");
        team.setBudget(BigDecimal.valueOf(1000));
        team.setCommission(5.0);

        when(teamConverter.convertToTeam(teamDTO)).thenReturn(team);

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("The team has been saved successfully"));
    }

    @Test
    public void testUpdateTeam() throws Exception {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setName("Updated Team");
        teamDTO.setBudget(BigDecimal.valueOf(2000));
        teamDTO.setCommission(10.0);

        Team team = new Team();
        team.setName("Updated Team");
        team.setBudget(BigDecimal.valueOf(2000));
        team.setCommission(10.0);

        when(teamConverter.convertToTeam(teamDTO)).thenReturn(team);

        mockMvc.perform(patch("/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("The team has been updated successfully"));
    }

    @Test
    public void testDeleteTeam() throws Exception {
        mockMvc.perform(delete("/teams/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The team has been deleted successfully"));
    }
}
