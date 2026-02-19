
package models;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

import java.io.IOException;

public class Club implements IClub {

    private final String name;
    private final String code;
    private final String country;
    private final int foundedYear;
    private final String stadiumName;
    private final String logo;

    private IPlayer[] players;
    private int playerCount;

    private static final int INITIAL_CAPACITY = 25;

    public Club(String name, String code, String country, int foundedYear, String stadiumName, String logo) {
        if (name == null || code == null || country == null || stadiumName == null || logo == null) {
            throw new IllegalArgumentException("Nenhum campo pode ser nulo.");
        }

        this.name = name;
        this.code = code;
        this.country = country;
        this.foundedYear = foundedYear;
        this.stadiumName = stadiumName;
        this.logo = logo;
        this.players = new IPlayer[INITIAL_CAPACITY];
        this.playerCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public int getFoundedYear() {
        return foundedYear;
    }

    @Override
    public String getStadiumName() {
        return stadiumName;
    }

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }

        if (isPlayer(player)) {
            throw new IllegalArgumentException("Jogador já está no clube.");
        }

        if (playerCount >= players.length) {
            expandCapacity();
        }

        players[playerCount++] = player;
    }

    @Override
    public boolean isPlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }

        for (int i = 0; i < playerCount; i++) {
            if (players[i].equals(player)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void removePlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }

        boolean found = false;
        for (int i = 0; i < playerCount; i++) {
            if (players[i].equals(player)) {
                found = true;
                for (int j = i; j < playerCount - 1; j++) {
                    players[j] = players[j + 1];
                }
                players[playerCount - 1] = null;
                playerCount--;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Jogador não pertence ao clube.");
        }
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
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
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if (selector == null || position == null) {
            throw new IllegalArgumentException("Seletor e posição não podem ser nulos.");
        }

        return selector.selectPlayer(this, position);
    }

    @Override
    public boolean isValid() {
        if (playerCount == 0) {
            throw new IllegalStateException("O clube está vazio.");
        }
        if (playerCount < 16) {
            throw new IllegalStateException("O clube precisa de pelo menos 16 jogadores.");
        }

        boolean hasGoalkeeper = false;

        for (int i = 0; i < playerCount; i++) {
            if (players[i].getPosition().getDescription().equalsIgnoreCase("Goalkeeper")) {
                hasGoalkeeper = true;
                break;
            }
        }

        if (!hasGoalkeeper) {
            throw new IllegalStateException("O clube precisa de pelo menos um guarda-redes.");
        }

        return true;
    }

    @Override
    public void exportToJson() throws IOException {
        System.out.println("{");
        System.out.println("  \"name\": \"" + name + "\",");
        System.out.println("  \"code\": \"" + code + "\",");
        System.out.println("  \"country\": \"" + country + "\",");
        System.out.println("  \"foundedYear\": " + foundedYear + ",");
        System.out.println("  \"stadiumName\": \"" + stadiumName + "\",");
        System.out.println("  \"logo\": \"" + logo + "\",");
        System.out.println("  \"players\": [");
        for (int i = 0; i < playerCount; i++) {
            players[i].exportToJson();
            if (i < playerCount - 1) {
                System.out.println(",");
            }
        }
        System.out.println("  ]");
        System.out.println("}");
    }

    private void expandCapacity() {
        int newCapacity = players.length + 10;
        IPlayer[] newArray = new IPlayer[newCapacity];
        for (int i = 0; i < playerCount; i++) {
            newArray[i] = players[i];
        }
        players = newArray;
    }
    
    public void reset() {
        this.playerCount = 0;
        this.players = new IPlayer[INITIAL_CAPACITY];
    }
}

