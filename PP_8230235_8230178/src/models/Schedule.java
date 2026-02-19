/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

package models;

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import java.io.FileWriter;

public class Schedule implements ISchedule {

    private IMatch[] matches;
    private int matchCount;
    private int numberOfRounds;

    public Schedule(int numberOfRounds, int maxMatches) {
        if (numberOfRounds <= 0) {
            throw new IllegalArgumentException("Número de rodadas deve ser maior que 0");
        }
        this.numberOfRounds = numberOfRounds;
        this.matches = new IMatch[maxMatches];
        this.matchCount = 0;
    }

    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round <= 0 || round > numberOfRounds) {
            throw new IllegalArgumentException("Rodada inválida");
        }
        IMatch[] roundMatches = new IMatch[matchCount];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getRound() == round) {
                roundMatches[index++] = matches[i];
            }
        }
        IMatch[] result = new IMatch[index];
        System.arraycopy(roundMatches, 0, result, 0, index);
        return result;
    }

    @Override
    public IMatch[] getMatchesForTeam(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("O time não pode ser nulo");
        }
        IMatch[] teamMatches = new IMatch[matchCount];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getHomeTeam() == team || matches[i].getAwayTeam() == team) {
                teamMatches[index++] = matches[i];
            }
        }
        IMatch[] result = new IMatch[index];
        System.arraycopy(teamMatches, 0, result, 0, index);
        return result;
    }

    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    @Override
    public IMatch[] getAllMatches() {
        if (matchCount == 0) {
            throw new IllegalStateException("Jogos não foram definidos");
        }
        IMatch[] result = new IMatch[matchCount];
        System.arraycopy(matches, 0, result, 0, matchCount);
        return result;
    }

    @Override
    public void setTeam(ITeam team, int round) {
        if (team == null) {
            throw new IllegalArgumentException("O time não pode ser nulo");
        }
        if (round <= 0 || round > numberOfRounds) {
            throw new IllegalArgumentException("Número de rodada inválido");
        }

        boolean teamAssigned = false;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getRound() == round) {
                try {
                    matches[i].setTeam(team);
                    teamAssigned = true;
                    break;
                } catch (IllegalStateException e) {
                }
            }
        }

        if (!teamAssigned) {
            if (matchCount < matches.length) {
                Match newMatch = new Match(round, team.getClub(), null);  // temporariamente null
                newMatch.setTeam(team);
                matches[matchCount++] = newMatch;
            } else {
                throw new IllegalStateException("Não há mais partidas disponíveis para esta rodada");
            }
        }
    }

    @Override
    public void exportToJson() throws IOException {
        FileWriter writer = new FileWriter("schedule.json");
        writer.write("[\n");
        for (int i = 0; i < matchCount; i++) {
            IMatch match = matches[i];
            writer.write("  {\n");
            writer.write("    \"round\": " + match.getRound() + ",\n");
            writer.write("    \"homeClub\": \"" + match.getHomeClub().getName() + "\",\n");
            writer.write("    \"awayClub\": \"" + match.getAwayClub().getName() + "\",\n");
            writer.write("    \"played\": " + match.isPlayed() + "\n");
            writer.write(i < matchCount - 1 ? "  },\n" : "  }\n");
        }
        writer.write("]\n");
        writer.close();
    }
    
    public void setMatch(int index, IMatch match) {
        if (index < 0 || index >= matches.length) {
            throw new IllegalArgumentException("Índice de partida inválido");
        }
        matches[index] = match;
    }
}
