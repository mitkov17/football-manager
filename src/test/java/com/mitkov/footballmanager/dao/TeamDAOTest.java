package com.mitkov.footballmanager.dao;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TeamDAO teamDAO;

    @Test
    public void testGetAllTeams() {
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team1");

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team2");

        Query<Team> mockQuery = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.players", Team.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(List.of(team1, team2));

        List<Team> teams = teamDAO.getAllTeams();

        assertNotNull(teams);
        assertEquals(2, teams.size());
        assertEquals("Team1", teams.get(0).getName());
        assertEquals("Team2", teams.get(1).getName());
    }

    @Test
    public void testGetTeam() {
        Team team = new Team();
        team.setId(5L);
        team.setName("Team5");

        Query<Team> mockQuery = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = :id", Team.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("id", 5L)).thenReturn(mockQuery);
        when(mockQuery.uniqueResult()).thenReturn(team);

        Team result = teamDAO.getTeam(5L);

        assertNotNull(result);
        assertEquals(5L, result.getId());
        assertEquals("Team5", result.getName());
    }

    @Test
    public void testCreateTeam() {
        Team team = new Team();
        team.setId(12L);
        team.setName("Team12");
        team.setBudget(BigDecimal.valueOf(100000));
        team.setCommission(5.0);

        Query<Long> mockQuery = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery("SELECT count(t) FROM Team t WHERE t.name = :name", Long.class))
                .thenReturn(mockQuery);

        when(mockQuery.setParameter("name", "Team12")).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(0L);

        teamDAO.createTeam(team);

        verify(session, times(1)).persist(team);
    }

    @Test
    public void testUpdateTeam() {
        Team team = new Team();
        team.setName("Old Team");
        team.setBudget(BigDecimal.valueOf(50000));
        team.setCommission(3.0);

        Team updatedTeam = new Team();
        updatedTeam.setName("New Team");
        updatedTeam.setBudget(BigDecimal.valueOf(100000));
        updatedTeam.setCommission(5.0);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Team.class, 1L)).thenReturn(team);

        teamDAO.updateTeam(1L, updatedTeam);

        assertEquals("New Team", team.getName());
        assertEquals(BigDecimal.valueOf(100000), team.getBudget());
        assertEquals(5.0, team.getCommission());
    }

    @Test
    public void testDeleteTeam() {
        Team team = new Team();
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Team.class, 1L)).thenReturn(team);

        teamDAO.deleteTeam(1L);

        verify(session, times(1)).remove(team);
    }
}
