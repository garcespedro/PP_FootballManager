/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 *
 * @author joaof
 */
public class Standing implements IStanding {

    private ITeam team;
    private int points = 0;
    private int wins = 0;
    private int draws = 0;
    private int losses = 0;
    private int goalsScored = 0;
    private int goalsConceded = 0;

    public Standing(ITeam team) {
        if (team == null) {
            throw new IllegalStateException("Team must be initialized");
        }
        this.team = team;
    }

    @Override
    public ITeam getTeam() {
        if (team == null) {
            throw new IllegalStateException("Team is not initialized");
        }
        return team;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void addPoints(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        }
        this.points += i;
    }

    @Override
    public void addWin(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Points per win cannot be negative");
        }
        wins++;
        addPoints(3);
    }

    @Override
    public void addDraw(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Points per draw cannot be negative");
        }
        draws++;
        addPoints(1);
    }

    @Override
    public void addLoss(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Points per loss cannot be negative");
        }
        losses++;
        addPoints(0);
    }

    @Override
    public int getWins() {
        return wins;
    }

    @Override
    public int getDraws() {
        return draws;
    }

    @Override
    public int getLosses() {
        return losses;
    }

    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }
}

