package com.mitkov.footballmanager.dao;

import com.mitkov.footballmanager.exceptions.InsufficientBudgetException;
import com.mitkov.footballmanager.exceptions.PlayerAlreadyExistsException;
import com.mitkov.footballmanager.exceptions.PlayerNotFoundException;
import com.mitkov.footballmanager.exceptions.TeamNotFoundException;
import com.mitkov.footballmanager.models.Player;
import com.mitkov.footballmanager.models.Team;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerDAO {

    private final SessionFactory sessionFactory;

    public List<Player> getAllPlayers() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("SELECT p FROM Player p", Player.class).getResultList();
    }

    public Player getPlayer(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Player player = session.get(Player.class, id);
        if (player == null) {
            throw new PlayerNotFoundException(id);
        }
        return player;
    }

    @Transactional
    public void createPlayer(Player player) {
        Session session = sessionFactory.getCurrentSession();

        long count = session.createQuery("SELECT count(p) FROM Player p WHERE p.name = :name AND p.surname = :surname AND p.dateOfBirth = :dateOfBirth", Long.class)
                .setParameter("name", player.getName())
                .setParameter("surname", player.getSurname())
                .setParameter("dateOfBirth", player.getDateOfBirth())
                .getSingleResult();

        if (count > 0) {
            throw new PlayerAlreadyExistsException(player.getName(), player.getSurname());
        }

        session.persist(player);
    }

    @Transactional
    public void updatePlayer(Long id, Player updatedPlayer) {
        Session session = sessionFactory.getCurrentSession();
        Player playerToBeUpdated = session.get(Player.class, id);
        if (playerToBeUpdated == null) {
            throw new PlayerNotFoundException(id);
        }

        playerToBeUpdated.setName(updatedPlayer.getName());
        playerToBeUpdated.setSurname(updatedPlayer.getSurname());
        playerToBeUpdated.setExperience(updatedPlayer.getExperience());
        playerToBeUpdated.setDateOfBirth(updatedPlayer.getDateOfBirth());
    }

    @Transactional
    public void deletePlayer(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Player.class, id));
    }

    @Transactional
    public void transferPlayer(Long playerId, Long teamId) {

        Session session = sessionFactory.getCurrentSession();

        Player player = session.get(Player.class, playerId);
        if (player == null) {
            throw new PlayerNotFoundException(playerId);
        }

        Team targetTeam = session.get(Team.class, teamId);
        if (targetTeam == null) {
            throw new TeamNotFoundException(teamId);
        }

        Team currentTeam = player.getTeam();
        if (currentTeam == null) {
            player.setTeam(targetTeam);
            session.persist(player);
            return;
        }

        int playerAge = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
        double transferCost = player.getExperience() * 100000.0 / playerAge;
        double commission = transferCost * (currentTeam.getCommission() / 100.0);
        double totalCost = transferCost + commission;

        if (targetTeam.getBudget().compareTo(BigDecimal.valueOf(totalCost)) < 0) {
            throw new InsufficientBudgetException();
        }

        targetTeam.setBudget(targetTeam.getBudget().subtract(BigDecimal.valueOf(totalCost)));
        currentTeam.setBudget(currentTeam.getBudget().add(BigDecimal.valueOf(totalCost)));

        player.setTeam(targetTeam);

        session.persist(currentTeam);
        session.persist(targetTeam);
        session.persist(player);
    }
}
