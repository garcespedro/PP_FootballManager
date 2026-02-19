/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import simulator.GoalEvent;

/**
 *
 * @author joaof
 */

public class Season implements ISeason {
    private final int year;
    private final String name;
    private final int maxTeams;
    private final int maxRounds;
    private IClub[] clubs;
    private Schedule schedule;
    private int currentRound;
    private boolean seasonComplete;
    private MatchSimulatorStrategy matchSimulatorStrategy;
    private int clubCount;
    private static final int INITIAL_CLUB_CAPACITY = 20;

    public Season(int year, String name, int maxTeams, int maxRounds) {
        this.year = year;
        this.name = name;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.clubs = new IClub[maxTeams];
        this.schedule = new Schedule(maxRounds, maxTeams * (maxTeams - 1) / 2);
        this.currentRound = 1;
        this.seasonComplete = false;
        this.clubs = new IClub[INITIAL_CLUB_CAPACITY];
        this.clubCount = 0;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public boolean addClub(IClub iclub) {
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] == null) {
                clubs[i] = iclub;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeClub(IClub iclub) {
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] != null && clubs[i].equals(iclub)) {
                clubs[i] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public void generateSchedule() {
        if (getNumberOfCurrentTeams() < 2) {
            throw new IllegalStateException("A temporada precisa de pelo menos dois clubes para gerar o agendamento.");
        }

        int matchCounter = 0;
        for (int round = 1; round <= maxRounds; round++) {
            for (int i = 0; i < clubs.length; i++) {
                for (int j = i + 1; j < clubs.length; j++) {
                    if (clubs[i] != null && clubs[j] != null) {
                        IMatch match = new Match(round, clubs[i], clubs[j]);
                        schedule.setMatch(matchCounter, match);
                        matchCounter++;
                    }
                }
            }
        }
    }

    @Override
    public IMatch[] getMatches() {
        return schedule.getAllMatches();
    }

    @Override
    public IMatch[] getMatches(int round) {
        return schedule.getMatchesForRound(round);
    }

    @Override
    public void simulateRound() {
        if (currentRound > maxRounds) {
            throw new IllegalStateException("A temporada já foi concluída.");
        }

        IMatch[] matchesForRound = schedule.getMatchesForRound(currentRound);
        for (IMatch match : matchesForRound) {
            if (!match.isPlayed()) {
                if (matchSimulatorStrategy == null) {
                    throw new IllegalStateException("Match simulator strategy não foi definido.");
                }
                matchSimulatorStrategy.simulate(match);
            }
        }
        currentRound++;
    }

    @Override
    public void simulateSeason() {
        while (currentRound <= maxRounds) {
            simulateRound();
        }
        seasonComplete = true;
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public boolean isSeasonComplete() {
        return seasonComplete;
    }

    @Override
    public void resetSeason() {
        this.clubCount = 0;
        this.clubs = new IClub[INITIAL_CLUB_CAPACITY];
        try {
            this.schedule = new Schedule(38, 100);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar o cronograma da temporada: " + e.getMessage());
        }
        this.currentRound = 1;
    }

    @Override
    public String displayMatchResult(IMatch match) {
        if (match.isPlayed()) {
            int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
            int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
            String result = match.getHomeClub().getName() + " " + homeGoals
                            + " - " + awayGoals
                            + " " + match.getAwayClub().getName();
            return result;
        } else {
            return match.getHomeClub().getName() + " vs " + match.getAwayClub().getName() + " - Jogo não jogado";
        }
    }

    @Override
    public void setMatchSimulator(MatchSimulatorStrategy mss) {
        this.matchSimulatorStrategy = mss;
    }
    
    @Override
    public IStanding[] getLeagueStandings() {
        int numberOfCurrentTeams = getNumberOfCurrentTeams();
        IStanding[] standings = new IStanding[numberOfCurrentTeams];
        int index = 0;
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] != null) {
                ITeam team = new Team(clubs[i]);
                standings[index++] = new Standing(team);
            }
        }
        return standings;
    }

    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    @Override
    public int getPointsPerWin() {
        return 3;
    }

    @Override
    public int getPointsPerDraw() {
        return 1;
    }

    @Override
    public int getPointsPerLoss() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    @Override
    public int getCurrentMatches() {
        return schedule.getAllMatches().length;
    }

    @Override
    public int getNumberOfCurrentTeams() {
        int count = 0;
        for (IClub club : clubs) {
            if (club != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public IClub[] getCurrentClubs() {
        IClub[] currentClubs = new IClub[getNumberOfCurrentTeams()];
        int index = 0;
        for (IClub club : clubs) {
            if (club != null) {
                currentClubs[index++] = club;
            }
        }
        return currentClubs;
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
