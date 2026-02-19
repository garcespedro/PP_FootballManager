/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulator;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.util.Random;

import java.util.Random;

/**
 * Implementação da estratégia de simulação de um jogo de futebol.
 * Esta classe simula aleatoriamente os eventos de uma partida,
 * como golos, faltas, cartões, lesões e substituições.
 */
public class matchSimulatorStrategy implements MatchSimulatorStrategy {

    private final Random random = new Random();

    /**
     * Simula um jogo, adicionando eventos aleatórios ao objeto {@link IMatch}.
     * 
     * @param match O jogo a ser simulado (não pode ser nulo, deve ser válido e ainda não jogado).
     * @throws IllegalArgumentException se o jogo for nulo.
     * @throws IllegalStateException se o jogo não for válido ou já foi jogado.
     */
    @Override
    public void simulate(IMatch match) {
        if (match == null) throw new IllegalArgumentException("O jogo não pode ser nulo.");
        if (!match.isValid()) throw new IllegalStateException("O jogo não está válido.");
        if (match.isPlayed()) throw new IllegalStateException("O jogo já foi jogado.");

        ITeam homeTeam = match.getHomeTeam();
        ITeam awayTeam = match.getAwayTeam();

        IClub homeClub = homeTeam.getClub();
        IClub awayClub = awayTeam.getClub();

        IPlayer[] homePlayers = homeTeam.getPlayers();
        IPlayer[] awayPlayers = awayTeam.getPlayers();

        int homeGoals = random.nextInt(5);
        int awayGoals = random.nextInt(5);

        // Adiciona eventos de golos para a equipa da casa
        for (int i = 0; i < homeGoals; i++) {
            IPlayer scorer = randomPlayer(homePlayers);
            IPlayer assist = random.nextBoolean() ? randomPlayer(homePlayers) : null;
            int minute = randomMinute();
            match.addEvent(new GoalEvent(minute, scorer, homeClub, assist));
        }

        // Adiciona eventos de golos para a equipa visitante
        for (int i = 0; i < awayGoals; i++) {
            IPlayer scorer = randomPlayer(awayPlayers);
            IPlayer assist = random.nextBoolean() ? randomPlayer(awayPlayers) : null;
            int minute = randomMinute();
            match.addEvent(new GoalEvent(minute, scorer, awayClub, assist));
        }

        // Adiciona eventos extra (faltas, cartões, lesões, substituições)
        int extraEvents = 5 + random.nextInt(6); // entre 5 e 10 eventos extras
        for (int i = 0; i < extraEvents; i++) {
            boolean isHome = random.nextBoolean();
            IClub club = isHome ? homeClub : awayClub;
            IPlayer[] players = isHome ? homePlayers : awayPlayers;
            int minute = randomMinute();

            switch (random.nextInt(4)) {
                case 0:
                    IPlayer fouling = randomPlayer(players);
                    IPlayer fouled = randomPlayer(players);
                    match.addEvent(new FoulEvent(minute, fouling, fouled, club));
                    break;
                case 1:
                    IPlayer player = randomPlayer(players);
                    String type = random.nextBoolean() ? "Amarelo" : "Vermelho";
                    match.addEvent(new CardEvent(minute, player, club, type));
                    break;
                case 2:
                    IPlayer injured = randomPlayer(players);
                    boolean severe = random.nextBoolean();
                    match.addEvent(new InjuryEvent(minute, injured, severe, club));
                    break;
                case 3:
                    IPlayer out = randomPlayer(players);
                    IPlayer in = randomPlayer(players);
                    match.addEvent(new SubstitutionEvent(minute, out, in, club));
                    break;
            }
        }

        // Marca o jogo como jogado
        match.setPlayed();
    }

    /**
     * Seleciona aleatoriamente um jogador da lista fornecida.
     * 
     * @param players Array de jogadores disponíveis.
     * @return Um jogador aleatório.
     * @throws IllegalStateException se o array for nulo ou vazio.
     */
    private IPlayer randomPlayer(IPlayer[] players) {
        if (players == null || players.length == 0) throw new IllegalStateException("Equipa não tem jogadores.");
        return players[random.nextInt(players.length)];
    }

    /**
     * Gera um minuto aleatório entre 1 e 90 (inclusive).
     * 
     * @return Minuto aleatório.
     */
    private int randomMinute() {
        return random.nextInt(90) + 1;
    }
}
