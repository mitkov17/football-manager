package com.mitkov.footballmanager.controllers;

import com.mitkov.footballmanager.converters.PlayerConverter;
import com.mitkov.footballmanager.dao.PlayerDAO;
import com.mitkov.footballmanager.dto.PlayerDTO;
import com.mitkov.footballmanager.dto.PlayerResponseDTO;
import com.mitkov.footballmanager.models.Player;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerConverter playerConverter;

    private final PlayerDAO playerDAO;

    @Autowired
    public PlayerController(PlayerConverter playerConverter, PlayerDAO playerDAO) {
        this.playerConverter = playerConverter;
        this.playerDAO = playerDAO;
    }

    @GetMapping()
    public List<PlayerResponseDTO> getAllPlayers() {
        List<Player> players = playerDAO.getAllPlayers();
        return players.stream().map(playerConverter::convertToPlayerResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlayerResponseDTO getPlayer(@PathVariable("id") Long playerId) {
        return playerConverter.convertToPlayerResponseDTO(playerDAO.getPlayer(playerId));
    }

    @PostMapping()
    public ResponseEntity<String> savePlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        Player player = playerConverter.convertToPlayer(playerDTO);
        playerDAO.savePlayer(player);
        return ResponseEntity.ok("The player has been saved successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePlayer(@PathVariable("id") Long playerId,
                                               @Valid @RequestBody PlayerDTO playerDTO) {
        Player player = playerConverter.convertToPlayer(playerDTO);
        playerDAO.updatePlayer(playerId, player);
        return ResponseEntity.ok("The player has been updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable("id") Long playerId) {
        playerDAO.deletePlayer(playerId);
        return ResponseEntity.ok("The player has been deleted successfully");
    }

    @PatchMapping("/{playerId}/transfer/{teamId}")
    public ResponseEntity<String> transferPlayer(@PathVariable("playerId") Long playerId,
                                                 @PathVariable("teamId") Long teamId) {
        playerDAO.transferPlayer(playerId, teamId);
        return ResponseEntity.ok("The player has been successfully transferred or added to the team");
    }
}
