
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
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

import java.io.IOException;

/**
 * Representa um clube de futebol com informações básicas e uma lista de jogadores.
 * Implementa a interface IClub para fornecer operações relacionadas ao clube.
 */
public class Club implements IClub {

    /**
     * Nome do clube.
     */
    private final String name;

    /**
     * Código identificador do clube.
     */
    private final String code;

    /**
     * País onde o clube está localizado.
     */
    private final String country;

    /**
     * Ano de fundação do clube.
     */
    private final int foundedYear;

    /**
     * Nome do estádio do clube.
     */
    private final String stadiumName;

    /**
     * Logo do clube (pode ser um caminho para imagem ou um identificador).
     */
    private final String logo;

    /**
     * Array interno que armazena os jogadores do clube.
     */
    private IPlayer[] players;

    /**
     * Contador do número atual de jogadores no clube.
     */
    private int playerCount;

    /**
     * Capacidade inicial do array de jogadores.
     */
    private static final int INITIAL_CAPACITY = 25;

    /**
     * Construtor para criar um novo clube com os dados fornecidos.
     * 
     * @param name Nome do clube (não pode ser nulo)
     * @param code Código do clube (não pode ser nulo)
     * @param country País do clube (não pode ser nulo)
     * @param foundedYear Ano de fundação
     * @param stadiumName Nome do estádio (não pode ser nulo)
     * @param logo Logo do clube (não pode ser nulo)
     * @throws IllegalArgumentException se algum parâmetro obrigatório for nulo
     */
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

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    @Override
    public String getCode() {
        return code;
    }

    /** {@inheritDoc} */
    @Override
    public String getCountry() {
        return country;
    }

    /** {@inheritDoc} */
    @Override
    public int getFoundedYear() {
        return foundedYear;
    }

    /** {@inheritDoc} */
    @Override
    public String getStadiumName() {
        return stadiumName;
    }

    /** {@inheritDoc} */
    @Override
    public String getLogo() {
        return logo;
    }

    /**
     * Adiciona um jogador ao clube, expandindo a capacidade do array se necessário.
     * 
     * @param player Jogador a adicionar (não pode ser nulo e não pode estar já no clube)
     * @throws IllegalArgumentException se o jogador for nulo ou já existir no clube
     */
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

    /**
     * Verifica se um jogador pertence ao clube.
     * 
     * @param player Jogador a verificar (não pode ser nulo)
     * @return true se o jogador estiver no clube, false caso contrário
     * @throws IllegalArgumentException se o jogador for nulo
     */
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

    /**
     * Remove um jogador do clube.
     * 
     * @param player Jogador a remover (não pode ser nulo e deve pertencer ao clube)
     * @throws IllegalArgumentException se o jogador for nulo ou não pertencer ao clube
     */
    @Override
    public void removePlayer(IPlayer player) {
        if (player == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo.");
        }

        boolean found = false;
        for (int i = 0; i < playerCount; i++) {
            if (players[i].equals(player)) {
                found = true;
                // desloca os jogadores seguintes uma posição para trás
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

    /** {@inheritDoc} */
    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Retorna uma cópia do array de jogadores atuais do clube.
     * 
     * @return Array com os jogadores do clube
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
     * Seleciona um jogador para uma posição usando um seletor externo.
     * 
     * @param selector Objeto que define o critério de seleção (não pode ser nulo)
     * @param position Posição desejada do jogador (não pode ser nulo)
     * @return Jogador selecionado pelo seletor, ou null se nenhum jogador for adequado
     * @throws IllegalArgumentException se seletor ou posição forem nulos
     */
    @Override
    public IPlayer selectPlayer(IPlayerSelector selector, IPlayerPosition position) {
        if (selector == null || position == null) {
            throw new IllegalArgumentException("Seletor e posição não podem ser nulos.");
        }

        return selector.selectPlayer(this, position);
    }

    /**
     * Valida o clube, garantindo que tenha jogadores suficientes e pelo menos um guarda-redes.
     * 
     * @return true se o clube for válido
     * @throws IllegalStateException se o clube estiver vazio, tiver menos de 16 jogadores ou não possuir guarda-redes
     */
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

    /**
     * Exporta os dados do clube em formato JSON para a saída standard.
     * 
     * @throws IOException Em caso de erro de escrita (não comum com System.out)
     */
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

    /**
     * Expande a capacidade do array interno de jogadores em 10 posições.
     */
    private void expandCapacity() {
        int newCapacity = players.length + 10;
        IPlayer[] newArray = new IPlayer[newCapacity];
        for (int i = 0; i < playerCount; i++) {
            newArray[i] = players[i];
        }
        players = newArray;
    }

    /**
     * Reseta o clube, limpando a lista de jogadores e restaurando a capacidade inicial.
     */
    public void reset() {
        this.playerCount = 0;
        this.players = new IPlayer[INITIAL_CAPACITY];
    }
}


