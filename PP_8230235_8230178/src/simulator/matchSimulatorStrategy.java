/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulator;

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;

/**
 *
 * @author joaof
 */
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.util.Random;

public class matchSimulatorStrategy implements MatchSimulatorStrategy {

    private final Random random = new Random();

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

        for (int i = 0; i < homeGoals; i++) {
            IPlayer scorer = randomPlayer(homePlayers);
            IPlayer assist = random.nextBoolean() ? randomPlayer(homePlayers) : null;
            int minute = randomMinute();
            match.addEvent(new GoalEvent(minute, scorer, homeClub, assist));
        }

        for (int i = 0; i < awayGoals; i++) {
            IPlayer scorer = randomPlayer(awayPlayers);
            IPlayer assist = random.nextBoolean() ? randomPlayer(awayPlayers) : null;
            int minute = randomMinute();
            match.addEvent(new GoalEvent(minute, scorer, awayClub, assist));
        }
        int extraEvents = 5 + random.nextInt(6);
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

        match.setPlayed();
    }
    
    private IPlayer randomPlayer(IPlayer[] players) {
        if (players == null || players.length == 0) throw new IllegalStateException("Equipa não tem jogadores.");
        return players[random.nextInt(players.length)];
    }

    private int randomMinute() {
        return random.nextInt(90) + 1;
    }
}
