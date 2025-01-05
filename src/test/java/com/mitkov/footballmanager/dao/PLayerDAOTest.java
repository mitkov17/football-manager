package com.mitkov.footballmanager.dao;

import com.mitkov.footballmanager.models.Player;
import com.mitkov.footballmanager.models.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PLayerDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private PlayerDAO playerDAO;

    @Test
    public void testGetAllPlayers() {
        Player player1 = new Player();
        player1.setId(1L);
        player1.setName("John");
        player1.setSurname("Doe");

        Player player2 = new Player();
        player2.setId(2L);
        player2.setName("Jane");
        player2.setSurname("Smith");

        Query<Player> mockQuery = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT p FROM Player p", Player.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(List.of(player1, player2));

        List<Player> players = playerDAO.getAllPlayers();

        assertNotNull(players);
        assertEquals(2, players.size());
        assertEquals("John", players.get(0).getName());
        assertEquals("Jane", players.get(1).getName());
    }

    @Test
    public void testGetPlayer() {
        Player player = new Player();
        player.setId(1L);
        player.setName("John");
        player.setSurname("Doe");

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Player.class, 1L)).thenReturn(player);

        Player result = playerDAO.getPlayer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
    }

    @Test
    public void testCreatePlayer() {
        Player player = new Player();
        player.setName("John");
        player.setSurname("Doe");
        player.setDateOfBirth(LocalDate.of(2001, 10, 10));
        player.setExperience(5);

        Query<Long> mockQuery = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT count(p) FROM Player p WHERE p.name = :name AND p.surname = :surname AND p.dateOfBirth = :dateOfBirth", Long.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("name", "John")).thenReturn(mockQuery);
        when(mockQuery.setParameter("surname", "Doe")).thenReturn(mockQuery);
        when(mockQuery.setParameter("dateOfBirth", LocalDate.of(2001, 10, 10))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(0L);

        playerDAO.createPlayer(player);

        verify(session, times(1)).persist(player);
    }

    @Test
    public void testUpdatePlayer() {
        Player existingPlayer = new Player();
        existingPlayer.setId(1L);
        existingPlayer.setName("John");
        existingPlayer.setSurname("Doe");
        existingPlayer.setExperience(5);

        Player updatedPlayer = new Player();
        updatedPlayer.setName("Jane");
        updatedPlayer.setSurname("Smith");
        updatedPlayer.setExperience(10);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Player.class, 1L)).thenReturn(existingPlayer);

        playerDAO.updatePlayer(1L, updatedPlayer);

        assertEquals("Jane", existingPlayer.getName());
        assertEquals("Smith", existingPlayer.getSurname());
        assertEquals(10, existingPlayer.getExperience());
    }

    @Test
    public void testDeletePlayer() {
        Player player = new Player();
        player.setId(1L);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Player.class, 1L)).thenReturn(player);

        playerDAO.deletePlayer(1L);

        verify(session, times(1)).remove(player);
    }

    @Test
    public void testTransferPlayer() {
        Player player = new Player();
        player.setId(1L);
        player.setName("John");
        player.setSurname("Doe");
        player.setDateOfBirth(LocalDate.of(2000, 10, 10));
        player.setExperience(65);

        Team currentTeam = new Team();
        currentTeam.setId(1L);
        currentTeam.setName("Current Team");
        currentTeam.setBudget(BigDecimal.valueOf(500000));
        currentTeam.setCommission(5.0);

        Team targetTeam = new Team();
        targetTeam.setId(2L);
        targetTeam.setName("Target Team");
        targetTeam.setBudget(BigDecimal.valueOf(1000000));
        targetTeam.setCommission(5.0);

        player.setTeam(currentTeam);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Player.class, 1L)).thenReturn(player);
        when(session.get(Team.class, 2L)).thenReturn(targetTeam);

        int playerAge = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
        double transferCost = player.getExperience() * 100000.0 / playerAge;
        double commission = transferCost * (currentTeam.getCommission() / 100.0);
        double totalCost = transferCost + commission;

        BigDecimal expectedTargetBudget = targetTeam.getBudget().subtract(BigDecimal.valueOf(totalCost));
        BigDecimal expectedCurrentBudget = currentTeam.getBudget().add(BigDecimal.valueOf(totalCost));

        playerDAO.transferPlayer(1L, 2L);

        assertEquals(targetTeam, player.getTeam());
        assertEquals(expectedTargetBudget, targetTeam.getBudget());
        assertEquals(expectedCurrentBudget, currentTeam.getBudget());
        verify(session, times(1)).persist(player);
    }
}
