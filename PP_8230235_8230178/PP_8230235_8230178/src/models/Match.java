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

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import simulator.Event;
import simulator.EventManager;
import simulator.GoalEvent;

import java.io.IOException;

/**
 * Representa uma partida de futebol entre dois clubes numa determinada ronda.
 * Mantém a informação dos clubes em casa e fora, as equipas que jogam, os eventos da partida,
 * o estado se a partida já foi jogada e oferece métodos para consultar resultados e detalhes.
 */
public class Match implements IMatch {

    /**
     * Número da rodada em que a partida ocorre.
     */
    private final int round;

    /**
     * Clube mandante da partida.
     */
    private final IClub homeClub;

    /**
     * Clube visitante da partida.
     */
    private final IClub awayClub;

    /**
     * Time escalado pelo clube mandante.
     * Inicialmente nulo até ser definido.
     */
    private ITeam homeTeam;

    /**
     * Time escalado pelo clube visitante.
     * Inicialmente nulo até ser definido.
     */
    private ITeam awayTeam;

    /**
     * Indica se a partida já foi jogada.
     */
    private boolean played;

    /**
     * Gerenciador de eventos associados à partida (como gols, cartões etc.).
     */
    private final EventManager eventManager = new EventManager();


    /**
     * Constrói uma nova partida entre dois clubes.
     *
     * @param round     Número da rodada.
     * @param homeClub  Clube mandante.
     * @param awayClub  Clube visitante.
     * @throws IllegalArgumentException se os clubes forem nulos ou iguais.
     */
    public Match(int round, IClub homeClub, IClub awayClub) {
        if (homeClub == null || awayClub == null) {
            throw new IllegalArgumentException("Clubes não podem ser nulos.");
        }
        if (homeClub.equals(awayClub)) {
            throw new IllegalArgumentException("Os clubes devem ser diferentes.");
        }

        this.round = round;
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.played = false;
    }

    /**
     * @return Clube mandante.
     */
    @Override
    public IClub getHomeClub() {
        return homeClub;
    }

    /**
     * @return Clube visitante.
     */
    @Override
    public IClub getAwayClub() {
        return awayClub;
    }

    /**
     * @return true se a partida foi jogada, false caso contrário.
     */
    @Override
    public boolean isPlayed() {
        return played;
    }

    /**
     * Marca a partida como jogada.
     */
    @Override
    public void setPlayed() {
        this.played = true;
    }

    /**
     * @return Time mandante.
     * @throws IllegalStateException se o time ainda não foi definido.
     */
    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) {
            throw new IllegalStateException("Home team ainda não definido.");
        }
        return homeTeam;
    }

    /**
     * @return Time visitante.
     * @throws IllegalStateException se o time ainda não foi definido.
     */
    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException("Away team ainda não definido.");
        }
        return awayTeam;
    }

    /**
     * Define um time para a partida, validando se ele pertence a um dos clubes.
     *
     * @param team Time a ser adicionado.
     * @throws IllegalArgumentException se o time for nulo.
     * @throws IllegalStateException se o time já foi definido ou a partida já foi jogada.
     */
    @Override
    public void setTeam(ITeam team) {
        if (team == null) throw new IllegalArgumentException("Team não pode ser nulo.");
        if (played) throw new IllegalStateException("A partida já foi jogada.");

        IClub club = team.getClub();

        if (club.equals(homeClub)) {
            if (homeTeam != null) throw new IllegalStateException("Home team já definido.");
            homeTeam = team;
        } else if (club.equals(awayClub)) {
            if (awayTeam != null) throw new IllegalStateException("Away team já definido.");
            awayTeam = team;
        } else {
            throw new IllegalStateException("O clube do time não pertence a esta partida.");
        }
    }

    /**
     * @return Total de gols do clube mandante.
     */
    public int getHomeGoals() {
        int count = 0;
        for (IEvent event : eventManager.getEvents()) {
            if (event instanceof GoalEvent) {
                GoalEvent goal = (GoalEvent) event;
                if (goal.getClub().equals(homeClub)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return Total de gols do clube visitante.
     */
    public int getAwayGoals() {
        int count = 0;
        for (IEvent event : eventManager.getEvents()) {
            if (event instanceof GoalEvent) {
                GoalEvent goal = (GoalEvent) event;
                if (goal.getClub().equals(awayClub)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Retorna o total de gols de um clube específico nesta partida.
     *
     * @param club Clube para o qual contar os gols.
     * @return Número de gols.
     */
    public int getGoalsForClub(IClub club) {
        if (club.equals(homeClub)) return getHomeGoals();
        if (club.equals(awayClub)) return getAwayGoals();
        return 0;
    }

    /**
     * Verifica se a partida está em um estado válido para ser jogada.
     *
     * @return true se for válida, false caso contrário.
     */
    @Override
    public boolean isValid() {
        return homeTeam != null &&
               awayTeam != null &&
               !homeTeam.getClub().equals(awayTeam.getClub()) &&
               homeTeam.getClub().equals(homeClub) &&
               awayTeam.getClub().equals(awayClub) &&
               homeTeam.getFormation() != null &&
               awayTeam.getFormation() != null;
    }

    /**
     * Determina o time vencedor da partida.
     *
     * @return Time vencedor, ou null em caso de empate ou se a partida não foi jogada.
     */
    @Override
    public ITeam getWinner() {
        if (!played) return null;

        int homeGoals = getHomeGoals();
        int awayGoals = getAwayGoals();

        if (homeGoals > awayGoals) return homeTeam;
        else if (awayGoals > homeGoals) return awayTeam;
        else return null;
    }

    /**
     * @return Número da rodada da partida.
     */
    @Override
    public int getRound() {
        return round;
    }

    /**
     * Exporta os dados da partida em formato JSON simples para o console.
     *
     * @throws IOException se ocorrer erro durante a exportação.
     */
    @Override
    public void exportToJson() throws IOException {
        System.out.println("{");
        System.out.println("  \"round\": " + round + ",");
        System.out.println("  \"homeClub\": \"" + homeClub.getName() + "\",");
        System.out.println("  \"awayClub\": \"" + awayClub.getName() + "\",");
        System.out.println("  \"played\": " + played + ",");
        System.out.println("  \"homeGoals\": " + getHomeGoals() + ",");
        System.out.println("  \"awayGoals\": " + getAwayGoals() + ",");
        System.out.println("  \"events\": [");
        IEvent[] events = eventManager.getEvents();
        for (int i = 0; i < events.length; i++) {
            events[i].exportToJson();
            if (i < events.length - 1) {
                System.out.println(",");
            }
        }
        System.out.println("  ]");
        System.out.println("}");
    }

    /**
     * Adiciona um evento à partida.
     *
     * @param event Evento a ser adicionado.
     * @throws IllegalStateException se a partida já foi jogada.
     */
    @Override
    public void addEvent(IEvent event) {
        if (played) throw new IllegalStateException("Não pode adicionar eventos após a partida ser concluída.");
        eventManager.addEvent(event);
    }

    /**
     * @return Lista de eventos registrados na partida.
     */
    @Override
    public IEvent[] getEvents() {
        return eventManager.getEvents();
    }

    /**
     * @return Quantidade total de eventos registrados.
     */
    @Override
    public int getEventCount() {
        return eventManager.getEventCount();
    }

    /**
     * Conta o total de eventos de um tipo específico para um determinado clube.
     *
     * @param eventClass Classe do evento a ser contado.
     * @param club       Clube ao qual os eventos pertencem.
     * @return Total de eventos do tipo especificado para o clube.
     * @throws IllegalArgumentException se os argumentos forem nulos.
     */
    @Override
    public int getTotalByEvent(Class eventClass, IClub club) {
        if (eventClass == null || club == null) {
            throw new IllegalArgumentException("Classe de evento e clube não podem ser nulos.");
        }
        int count = 0;
        IEvent[] events = eventManager.getEvents();
        for (IEvent event : events) {
            if (eventClass.isInstance(event)) {
                if (event instanceof Event) {
                    Event specificEvent = (Event) event;
                    if (specificEvent.getClub() != null && specificEvent.getClub().equals(club)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
