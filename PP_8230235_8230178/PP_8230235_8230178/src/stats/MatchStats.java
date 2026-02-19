
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

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import simulator.GoalEvent;
import simulator.Event;

/**
 * Classe que calcula e apresenta estatísticas textuais básicas de um jogo,
 * incluindo posse de bola e número de remates para cada equipa.
 * 
 * A posse de bola é estimada com base na quantidade de eventos atribuídos a cada equipa,
 * enquanto o número de remates é calculado a partir dos eventos que mencionam remates
 * ou gols (GoalEvent).
 */
public class MatchStats {

    /**
     * Referência ao jogo que será analisado para calcular as estatísticas.
    */
    private final IMatch match;

    /**
     * Número de remates efetuados pela equipa da casa.
    */
    private int homeShots;

    /**
     * Número de remates efetuados pela equipa visitante.
    */
    private int awayShots;

    /**
     * Percentagem estimada de posse de bola da equipa da casa,
     * calculada com base na quantidade de eventos atribuídos à equipa.
    */
    private int homePossession;

    /**
     * Percentagem estimada de posse de bola da equipa visitante,
     * complementar à posse da equipa da casa (soma 100%).
    */
    private int awayPossession;


    /**
     * Construtor que recebe o jogo a ser analisado e calcula as estatísticas iniciais.
     * 
     * @param match O jogo para o qual as estatísticas serão computadas.
     */
    public MatchStats(IMatch match) {
        this.match = match;
        computeStats();
    }

    /**
     * Método interno que computa as estatísticas de remates e posse de bola
     * a partir dos eventos do jogo.
     * 
     * Considera como remates os eventos cujo texto contenha "remate" (case-insensitive)
     * ou eventos do tipo GoalEvent.
     * A posse é calculada proporcionalmente à quantidade de eventos ocorridos para cada equipa.
     */
    private void computeStats() {
        IClub home = match.getHomeClub();
        IClub away = match.getAwayClub();

        int homeEvents = 0, awayEvents = 0, totalEvents = 0;
        homeShots = 0; 
        awayShots = 0;

        for (IEvent event : match.getEvents()) {
            // Verifica se o evento é uma instância da classe Event (ou subclass)
            if (event instanceof Event) {
                Event ev = (Event) event;
                IClub eventClub = ev.getClub();

                if (eventClub != null && eventClub.equals(home)) {
                    homeEvents++;
                    // Conta remates ou gols para a equipa da casa
                    if (ev.getDescription().toLowerCase().contains("remate") || ev instanceof GoalEvent)
                        homeShots++;
                } else if (eventClub != null && eventClub.equals(away)) {
                    awayEvents++;
                    // Conta remates ou gols para a equipa visitante
                    if (ev.getDescription().toLowerCase().contains("remate") || ev instanceof GoalEvent)
                        awayShots++;
                }
                totalEvents++;
            }
        }
        // Calcula posse de bola em percentagem
        homePossession = totalEvents > 0 ? (homeEvents * 100) / totalEvents : 50;
        awayPossession = 100 - homePossession;
    }

    /**
     * Imprime no console as estatísticas formatadas de remates e posse de bola,
     * incluindo barras visuais para representar a posse.
     */
    public void printStats() {
        System.out.println("Remates: " + homeShots + " - " + awayShots);
        System.out.println("Posse de bola: " + bar(homePossession) + " " + homePossession + "% | " +
                           bar(awayPossession) + " " + awayPossession + "%");
    }

    /**
     * Gera uma barra visual representando a posse em blocos de 5%.
     * 
     * @param percent Percentagem da posse.
     * @return String contendo a barra visual.
     */
    private String bar(int percent) {
        int bars = percent / 5;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bars; i++) sb.append("|");
        return sb.toString();
    }
}
