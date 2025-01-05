package com.mitkov.footballmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitkov.footballmanager.converters.PlayerConverter;
import com.mitkov.footballmanager.dao.PlayerDAO;
import com.mitkov.footballmanager.dto.PlayerDTO;
import com.mitkov.footballmanager.dto.PlayerResponseDTO;
import com.mitkov.footballmanager.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerDAO playerDAO;

    @MockBean
    private PlayerConverter playerConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPlayers() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("John");
        player.setSurname("Doe");
        player.setDateOfBirth(LocalDate.of(2001, 10, 10));
        player.setExperience(10);

        PlayerResponseDTO responseDTO = new PlayerResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John");
        responseDTO.setSurname("Doe");
        responseDTO.setDateOfBirth(LocalDate.of(2001, 10, 10));
        responseDTO.setExperience(10);

        when(playerDAO.getAllPlayers()).thenReturn(List.of(player));
        when(playerConverter.convertToPlayerResponseDTO(player)).thenReturn(responseDTO);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("2001-10-10"))
                .andExpect(jsonPath("$[0].experience").value(10));
    }

    @Test
    public void testGetPlayer() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("John");
        player.setSurname("Doe");
        player.setDateOfBirth(LocalDate.of(2001, 10, 10));
        player.setExperience(10);

        PlayerResponseDTO responseDTO = new PlayerResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("John");
        responseDTO.setSurname("Doe");
        responseDTO.setDateOfBirth(LocalDate.of(2001, 10, 10));
        responseDTO.setExperience(10);

        when(playerDAO.getPlayer(1L)).thenReturn(player);
        when(playerConverter.convertToPlayerResponseDTO(player)).thenReturn(responseDTO);

        mockMvc.perform(get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value("2001-10-10"))
                .andExpect(jsonPath("$.experience").value(10));
    }

    @Test
    public void testCreatePlayer() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName("John");
        playerDTO.setSurname("Doe");
        playerDTO.setDateOfBirth(LocalDate.of(2001, 11, 11));
        playerDTO.setExperience(10);

        Player player = new Player();
        player.setName("John");
        player.setSurname("Doe");
        player.setDateOfBirth(LocalDate.of(2001, 11, 11));
        player.setExperience(10);

        when(playerConverter.convertToPlayer(playerDTO)).thenReturn(player);

        mockMvc.perform(post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("The player has been saved successfully"));
    }

    @Test
    public void testUpdatePlayer() throws Exception {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName("John Updated");
        playerDTO.setSurname("Doe Updated");
        playerDTO.setDateOfBirth(LocalDate.of(2001, 11, 11));
        playerDTO.setExperience(15);

        Player player = new Player();
        player.setName("John Updated");
        player.setSurname("Doe Updated");
        player.setDateOfBirth(LocalDate.of(2001, 11, 11));
        player.setExperience(15);

        when(playerConverter.convertToPlayer(playerDTO)).thenReturn(player);

        mockMvc.perform(patch("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("The player has been updated successfully"));
    }

    @Test
    public void testDeletePlayer() throws Exception {
        mockMvc.perform(delete("/players/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("The player has been deleted successfully"));
    }

    @Test
    public void testTransferPlayer() throws Exception {
        mockMvc.perform(patch("/players/1/transfer/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("The player has been successfully transferred or added to the team"));
    }
}
