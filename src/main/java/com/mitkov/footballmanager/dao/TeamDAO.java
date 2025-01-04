package com.mitkov.footballmanager.dao;

import com.mitkov.footballmanager.exceptions.TeamAlreadyExistsException;
import com.mitkov.footballmanager.exceptions.TeamNotFoundException;
import com.mitkov.footballmanager.models.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class TeamDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TeamDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Team> getAllTeams() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT t FROM Team t LEFT JOIN FETCH t.players", Team.class)
                .getResultList();
    }

    public Team getTeam(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Team team = session.createQuery(
                        "SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = :id", Team.class)
                .setParameter("id", id)
                .uniqueResult();
        if (team == null) {
            throw new TeamNotFoundException(id);
        }
        return team;
    }

    @Transactional
    public void saveTeam(Team team) {
        Session session = sessionFactory.getCurrentSession();

        long count = session.createQuery("select count(t) from Team t where t.name = :name", Long.class)
                .setParameter("name", team.getName())
                .getSingleResult();

        if (count > 0) {
            throw new TeamAlreadyExistsException(team.getName());
        }

        session.persist(team);
    }

    @Transactional
    public void updateTeam(Long id, Team updatedTeam) {
        Session session = sessionFactory.getCurrentSession();
        Team teamToBeUpdated = session.get(Team.class, id);
        if (teamToBeUpdated == null) {
            throw new TeamNotFoundException(id);
        }

        teamToBeUpdated.setName(updatedTeam.getName());
        teamToBeUpdated.setBudget(updatedTeam.getBudget());
        teamToBeUpdated.setCommission(updatedTeam.getCommission());
    }

    @Transactional
    public void deleteTeam(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Team.class, id));
    }
}
