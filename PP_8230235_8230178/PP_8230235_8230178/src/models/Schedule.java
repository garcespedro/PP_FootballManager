/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Implementação de um agendador de partidas, responsável por armazenar e gerenciar
 * os jogos de acordo com as rodadas e os times envolvidos.
 */
public class Schedule implements ISchedule {

    /**
     * Array que armazena todas as partidas.
     */
    private IMatch[] matches;

    /**
     * Quantidade atual de partidas registradas.
     */
    private int matchCount;

    /**
     * Número total de rodadas previstas.
     */
    private int numberOfRounds;

    /**
     * Cria um novo agendador de partidas com o número de rodadas e de clubes informados.
     *
     * @param numberOfRounds número total de rodadas.
     * @param numberOfClubs  número total de clubes participantes.
     * @throws IllegalArgumentException se os parâmetros forem menores ou iguais a 0.
     */
    public Schedule(int numberOfRounds, int numberOfClubs) {
        if (numberOfRounds <= 0 || numberOfClubs <= 0) {
            throw new IllegalArgumentException("Rodadas e clubes devem ser maiores que 0");
        }
        this.numberOfRounds = numberOfRounds;
        int maxMatches = numberOfRounds * Math.max(numberOfClubs / 2, 1);
        this.matches = new IMatch[maxMatches];
        this.matchCount = 0;
    }

    /**
     * Retorna todas as partidas agendadas para uma rodada específica.
     *
     * @param round número da rodada.
     * @return array de partidas da rodada.
     * @throws IllegalArgumentException se a rodada for inválida.
     */
    @Override
    public IMatch[] getMatchesForRound(int round) {
        if (round <= 0 || round > numberOfRounds) {
            throw new IllegalArgumentException("Rodada inválida: " + round);
        }
        IMatch[] roundMatches = new IMatch[matchCount];
        int index = 0;
        for (int i = 0; i < matchCount; i++) {
            if (matches[i].getRound() == round) {
                roundMatches[index++] = matches[i];
            }
        }
        return copyArray(roundMatches, index);
    }

    /**
     * Retorna todas as partidas em que o time especificado participou.
     *
     * @param team time desejado.
     * @return array de partidas do time.
     * @throws IllegalArgumentException se o time for nulo.
     */
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
        return copyArray(teamMatches, index);
    }

    /**
     * Retorna o número total de rodadas agendadas.
     *
     * @return número de rodadas.
     */
    @Override
    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    /**
     * Retorna todas as partidas cadastradas no agendamento.
     *
     * @return array de partidas.
     * @throws IllegalStateException se nenhuma partida foi registrada.
     */
    @Override
    public IMatch[] getAllMatches() {
        if (matchCount == 0) {
            throw new IllegalStateException("Jogos não foram definidos.");
        }
        return copyArray(matches, matchCount);
    }

    /**
     * Define um time para uma partida em uma rodada específica.
     * Se não houver partida naquela rodada, uma nova é criada.
     *
     * @param team  time a ser adicionado.
     * @param round número da rodada.
     * @throws IllegalArgumentException se o time for nulo ou rodada inválida.
     * @throws IllegalStateException    se não houver espaço para nova partida na rodada.
     */
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
                matches[i].setTeam(team);
                teamAssigned = true;
                break;
            }
        }

        if (!teamAssigned) {
            if (matchCount < matches.length) {
                Match newMatch = new Match(round, team.getClub(), null);
                newMatch.setTeam(team);
                matches[matchCount++] = newMatch;
            } else {
                throw new IllegalStateException("Não há mais partidas disponíveis para esta rodada.");
            }
        }
    }

    /**
     * Exporta todas as partidas do cronograma para um arquivo JSON chamado "schedule.json".
     *
     * @throws IOException se ocorrer erro ao escrever o arquivo.
     */
    @Override
    public void exportToJson() throws IOException {
        try (FileWriter writer = new FileWriter("schedule.json")) {
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
        }
    }

    /**
     * Define uma partida diretamente em um índice específico do array de partidas.
     *
     * @param index índice no array.
     * @param match partida a ser definida.
     * @throws IllegalArgumentException se o índice for inválido.
     */
    public void setMatch(int index, IMatch match) {
        if (index < 0 || index >= matches.length) {
            throw new IllegalArgumentException("Índice de partida inválido: " + index);
        }
        if (matches[index] == null) {
            matchCount++;
        }
        matches[index] = match;
    }

    /**
     * Cria uma cópia de um array de partidas com o tamanho especificado.
     *
     * @param source array original.
     * @param length tamanho da cópia.
     * @return nova cópia do array com o número exato de elementos válidos.
     */
    private IMatch[] copyArray(IMatch[] source, int length) {
        IMatch[] result = new IMatch[length];
        System.arraycopy(source, 0, result, 0, length);
        return result;
    }
}

