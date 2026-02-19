
package models;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

import java.io.IOException;

/**
 * Representa uma equipe de futebol associada a um clube.
 * A equipe possui jogadores, uma formação e métodos para gerenciamento dos jogadores e cálculo da força do time.
 * 
 * Implementa a interface {@link ITeam}.
 */
public class Team implements ITeam {

    /** Número máximo de jogadores na equipe */
    private static final int MAX_PLAYERS = 11;

    /** Clube ao qual a equipe pertence */
    private final IClub club;

    /** Array dos jogadores que fazem parte da equipe */
    private IPlayer[] players;

    /** Quantidade atual de jogadores na equipe */
    private int playerCount;

    /** Formação tática da equipe */
    private IFormation formation;

    /**
     * Construtor que inicializa a equipe para um clube específico.
     * 
     * @param club clube ao qual a equipe pertence (não pode ser nulo)
     * @throws IllegalArgumentException se o clube for nulo
     */
    public Team(IClub club) {
        if (club == null) {
            throw new IllegalArgumentException("O clube não pode ser nulo.");
        }
        this.club = club;
        this.players = new IPlayer[MAX_PLAYERS];
        this.playerCount = 0;
    }

    /**
     * Adiciona um jogador à equipe.
     * 
     * @param player jogador a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException se o jogador for nulo
     * @throws IllegalStateException se a formação não estiver definida,
     *                               se o jogador não pertencer ao clube,
     *                               se a equipe já estiver completa,
     *                               se o jogador já estiver na equipe,
     *                               ou se a posição do jogador não for válida para a formação atual
     */
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

    /**
     * Retorna o clube ao qual a equipe pertence.
     * 
     * @return clube da equipe
     */
    @Override
    public IClub getClub() {
        return club;
    }

    /**
     * Retorna a formação tática atual da equipe.
     * 
     * @return formação da equipe
     * @throws IllegalStateException se a formação não estiver definida
     */
    @Override
    public IFormation getFormation() {
        if (formation == null) {
            throw new IllegalStateException("A formação não está definida.");
        }
        return formation;
    }

    /**
     * Retorna uma cópia do array de jogadores atualmente na equipe.
     * 
     * @return array dos jogadores da equipe
     */
    @Override
    public IPlayer[] getPlayers() {
        IPlayer[] copy = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            copy[i] = players[i];
        }
        return copy;
    }

    /**
     * Retorna o número de jogadores em uma posição específica na equipe.
     * 
     * @param position posição de jogador a ser contada (não pode ser nula)
     * @return quantidade de jogadores na posição
     * @throws IllegalArgumentException se a posição for nula
     */
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

    /**
     * Calcula a força média da equipe com base nas habilidades dos jogadores (chute, passe, resistência e velocidade).
     * 
     * @return valor inteiro representando a força média da equipe; 0 se não houver jogadores
     */
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

    /**
     * Verifica se uma determinada posição é válida para a formação atual da equipe.
     * 
     * @param position posição a ser validada
     * @return true se a posição for válida para a formação, false caso contrário
     */
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

    /**
     * Define a formação tática da equipe.
     * 
     * @param formation formação a ser definida (não pode ser nula)
     * @throws IllegalArgumentException se a formação for nula
     */
    @Override
    public void setFormation(IFormation formation) {
        if (formation == null) {
            throw new IllegalArgumentException("A formação não pode ser nula.");
        }
        this.formation = formation;
    }

    /**
     * Exporta os dados da equipe para JSON.
     * O método imprime o JSON no console, contendo o nome do clube e os jogadores.
     * 
     * @throws IOException exceção em caso de erro na exportação
     */
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