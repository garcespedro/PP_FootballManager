
package stats;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import simulator.GoalEvent;

/**
 * Estatísticas individuais de jogadores, incluindo o número de golos e assistências.
 */
public class PlayerStats {

    /**
     * Jogador a que estas estatísticas pertencem.
     */
    private final IPlayer player;

    /**
     * Número total de golos marcados pelo jogador.
     */
    private int goals;

    /**
     * Número total de assistências feitas pelo jogador.
     */
    private int assists;

    /**
     * Construtor que inicializa as estatísticas para um jogador específico.
     * 
     * @param player O jogador cujas estatísticas serão acompanhadas.
     */
    public PlayerStats(IPlayer player) {
        this.player = player;
        this.goals = 0;
        this.assists = 0;
    }

    /**
     * Incrementa o contador de golos do jogador.
     */
    public void addGoal() {
        goals++;
    }

    /**
     * Incrementa o contador de assistências do jogador.
     */
    public void addAssist() {
        assists++;
    }

    /**
     * Obtém o número total de golos marcados pelo jogador.
     * 
     * @return Número de golos.
     */
    public int getGoals() {
        return goals;
    }

    /**
     * Obtém o número total de assistências feitas pelo jogador.
     * 
     * @return Número de assistências.
     */
    public int getAssists() {
        return assists;
    }

    /**
     * Obtém o jogador associado a estas estatísticas.
     * 
     * @return O jogador.
     */
    public IPlayer getPlayer() {
        return player;
    }

    /**
     * Calcula as estatísticas de golos e assistências de todos os jogadores fornecidos,
     * baseando-se nos eventos dos jogos indicados.
     * 
     * @param players Array dos jogadores a analisar.
     * @param matches Array dos jogos nos quais serão procurados os eventos de golos.
     * @return Array com as estatísticas de cada jogador.
     */
    public static PlayerStats[] computeStats(IPlayer[] players, IMatch[] matches) {
        PlayerStats[] stats = new PlayerStats[players.length];
        for (int i = 0; i < players.length; i++) {
            stats[i] = new PlayerStats(players[i]);
        }

        for (IMatch match : matches) {
            IEvent[] events = match.getEvents();
            for (IEvent event : events) {
                if (event instanceof GoalEvent) {
                    GoalEvent goalEvent = (GoalEvent) event;
                    IPlayer scorer = goalEvent.getPlayer();
                    IPlayer assist = goalEvent.getAssistPlayer();

                    for (PlayerStats stat : stats) {
                        if (stat.getPlayer().equals(scorer)) {
                            stat.addGoal();
                        }
                        if (assist != null && stat.getPlayer().equals(assist)) {
                            stat.addAssist();
                        }
                    }
                }
            }
        }

        return stats;
    }
}
