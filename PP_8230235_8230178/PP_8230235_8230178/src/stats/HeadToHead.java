
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

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import simulator.GoalEvent;

/**
 * Histórico de confrontos diretos entre duas equipas.
 */
/**
 * Classe utilitária para analisar e apresentar o histórico de confrontos diretos (head-to-head)
 * entre dois clubes de futebol, baseado em uma lista de jogos fornecida.
 */
public class HeadToHead {

    /**
     * Imprime no console o histórico de confrontos entre dois clubes, incluindo:
     * - Número total de jogos disputados entre os clubes
     * - Número de vitórias de cada clube
     * - Número de empates
     * - Total de golos marcados por cada clube nesses confrontos
     * 
     * O método considera partidas onde um dos clubes foi mandante e o outro visitante, e vice-versa.
     * 
     * @param clubA O primeiro clube a ser comparado (não pode ser nulo)
     * @param clubB O segundo clube a ser comparado (não pode ser nulo)
     * @param matches Array contendo os jogos a serem analisados (pode conter jogos de várias equipes)
     */
    public static void printHistory(IClub clubA, IClub clubB, IMatch[] matches) {
        int jogos = 0, vitoriasA = 0, vitoriasB = 0, empates = 0, golosA = 0, golosB = 0;

        for (IMatch match : matches) {
            IClub home = match.getHomeClub();
            IClub away = match.getAwayClub();

            // Verifica se o jogo é entre os clubes em qualquer ordem
            if ((home.equals(clubA) && away.equals(clubB)) || (home.equals(clubB) && away.equals(clubA))) {
                jogos++;
                // Conta os golos marcados por cada clube no jogo, usando eventos de golos
                int goalsA = match.getTotalByEvent(GoalEvent.class, clubA);
                int goalsB = match.getTotalByEvent(GoalEvent.class, clubB);

                golosA += goalsA;
                golosB += goalsB;

                if (goalsA > goalsB) vitoriasA++;
                else if (goalsB > goalsA) vitoriasB++;
                else empates++;
            }
        }

        System.out.println("Histórico de confrontos entre " + clubA.getName() + " e " + clubB.getName() + ":");
        System.out.println("Jogos: " + jogos + " | Vitórias " + clubA.getName() + ": " + vitoriasA +
                           " | Vitórias " + clubB.getName() + ": " + vitoriasB + " | Empates: " + empates);
        System.out.println("Golos: " + clubA.getName() + " " + golosA + " - " + golosB + " " + clubB.getName());
    }
}
