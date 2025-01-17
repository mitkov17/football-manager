package com.mitkov.footballmanager.controllers;

import com.mitkov.footballmanager.converters.TeamConverter;
import com.mitkov.footballmanager.dao.TeamDAO;
import com.mitkov.footballmanager.dto.TeamDTO;
import com.mitkov.footballmanager.dto.TeamResponseDTO;
import com.mitkov.footballmanager.models.Team;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamConverter teamConverter;

    private final TeamDAO teamDAO;

    @GetMapping()
    public List<TeamResponseDTO> getAllTeams() {
        List<Team> teams = teamDAO.getAllTeams();
        return teams.stream().map(teamConverter::convertToTeamResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TeamResponseDTO getTeam(@PathVariable("id") Long teamId) {
        return teamConverter.convertToTeamResponseDTO(teamDAO.getTeam(teamId));
    }

    @PostMapping()
    public ResponseEntity<String> createTeam(@Valid @RequestBody TeamDTO teamDTO) {
        Team team = teamConverter.convertToTeam(teamDTO);
        teamDAO.createTeam(team);
        return ResponseEntity.ok("The team has been saved successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTeam(@PathVariable("id") Long teamId,
                                             @Valid @RequestBody TeamDTO teamDTO) {
        Team team = teamConverter.convertToTeam(teamDTO);
        teamDAO.updateTeam(teamId, team);
        return ResponseEntity.ok("The team has been updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable("id") Long teamId) {
        teamDAO.deleteTeam(teamId);
        return ResponseEntity.ok("The team has been deleted successfully");
    }
}
