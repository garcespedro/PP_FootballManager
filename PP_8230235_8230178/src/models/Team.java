
package models;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

import java.io.IOException;

public class Team implements ITeam {

    private static final int MAX_PLAYERS = 11;

    private final IClub club;
    private IPlayer[] players;
    private int playerCount;
    private IFormation formation;

    public Team(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("O clube não pode ser nulo.");
        }
        this.club = club;
        this.players = new IPlayer[MAX_PLAYERS];
        this.playerCount = 0;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("O jogador não pode ser nulo.");
        }
        if (formation == null) {
            throw new IllegalStateException("A formação ainda não foi definida.");
        }
        if (!club.isPlayer(player)) {
            throw new IllegalStateException("O jogador não pertence ao clube.");
        }
        if (playerCount >= MAX_PLAYERS) {
            throw new IllegalStateException("A equipa já está completa.");
        }
        for (int i = 0; i < playerCount; i++) {
            if (players[i] == player) {
                throw new IllegalStateException("O jogador já está na equipa.");
            }
        }
        if (!isValidPositionForFormation(player.getPosition())) {
            throw new IllegalStateException("Posição inválida para a formação.");
        }

        players[playerCount++] = player;
    }

    @Override
    public IClub getClub() {
        return club;
    }

    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalStateException("A formação não está definida.");
        }
        return formation;
    }

    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] copy = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            copy[i] = players[i];
        }
        return copy;
    }

    @Override
    public int getPositionCount(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("A posição não pode ser nula.");
        }
        int count = 0;
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getPosition().getDescription().equalsIgnoreCase(position.getDescription())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getTeamStrength() {
        if (playerCount == 0) return 0;

        int total = 0;
        for (int i = 0; i < playerCount; i++) {
            IPlayer p = players[i];
            total += (p.getShooting() + p.getPassing() + p.getStamina() + p.getSpeed()) / 4;
        }
        return total / playerCount;
    }

    @Override
    public boolean isValidPositionForFormation(IPlayerPosition position) {
        if (formation instanceof Formation) {
            Formation concreteFormation = (Formation) formation;
            IPlayerPosition[] validPositions = concreteFormation.getPositions();
            for (int i = 0; i < validPositions.length; i++) {
                if (validPositions[i].getDescription().equalsIgnoreCase(position.getDescription())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("A formação não pode ser nula.");
        }
        this.formation = formation;
    }

    @Override
    public void exportToJson() throws IOException {
        System.out.println("{ \"club\": \"" + club.getName() + "\", \"players\": [");
        for (int i = 0; i < playerCount; i++) {
            players[i].exportToJson();
            if (i < playerCount - 1) {
                System.out.println(",");
            }
        }
        System.out.println("] }");
    }
}

