/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

/**
 *
 * @author joaof
 */
import com.ppstudios.footballmanager.api.contracts.league.*;
import com.ppstudios.footballmanager.api.contracts.team.*;
import com.ppstudios.footballmanager.api.contracts.player.*;
import com.ppstudios.footballmanager.api.contracts.match.*;

import models.*;

public class Engine {

    private League liga;
    private Season temporada;
    private IFormation formacaoEscolhida = null;

    public Engine(League liga) {
        this.liga = liga;
        this.temporada = (Season) liga.getSeasons()[0]; // Assumimos uma única época ativa
    }

    // === MENU 1: Visualizar Equipas e Jogadores ===
    public void listClubs() {
        IClub[] clubes = temporada.getCurrentClubs();
        for (int i = 0; i < clubes.length; i++) {
            System.out.println((i + 1) + ". " + clubes[i].getName());
        }
    }

    public void showClubPlayers(int index) {
        IClub[] clubes = temporada.getCurrentClubs();
        if (index < 1 || index > clubes.length) {
            System.out.println("Índice inválido.");
            return;
        }

        IClub club = clubes[index - 1];
        IPlayer[] jogadores = club.getPlayers();

        System.out.println("Jogadores de " + club.getName() + ":");
        for (int i = 0; i < jogadores.length; i++) {
            IPlayer p = jogadores[i];
            System.out.println("- " + p.getName() + " [" + p.getPosition().getDescription() + "] Nº " + p.getNumber());
        }
    }

    // === MENU 2: Ver Calendário ===
    public void showCalendar() {
        ISchedule calendario = temporada.getSchedule();
        for (int r = 1; r <= calendario.getNumberOfRounds(); r++) {
            System.out.println("\nJornada " + r);
            IMatch[] jogos = calendario.getMatchesForRound(r);
            for (int j = 0; j < jogos.length; j++) {
                IMatch jogo = jogos[j];
                System.out.println(" - " + temporada.displayMatchResult(jogo));
            }
        }
    }

    // === MENU 3: Ver Classificação ===
    public void showStandings() {
        IStanding[] tabela = temporada.getLeagueStandings();
        System.out.println("\n=== CLASSIFICAÇÃO ===");
        for (int i = 0; i < tabela.length; i++) {
            IStanding s = tabela[i];
            System.out.printf("%d. %s | Pts: %d | V: %d | E: %d | D: %d | GM: %d | GS: %d | DG: %d\n",
                i + 1,
                s.getTeam().getClub().getName(),
                s.getPoints(),
                s.getWins(),
                s.getDraws(),
                s.getLosses(),
                s.getGoalScored(),
                s.getGoalsConceded(),
                s.getGoalDifference()
            );
        }
    }

    // === MENU 4: Escolha de Formação ===
    public void setFormationByChoice(int option) {
        switch (option) {
            case 1 : formacaoEscolhida = getFormation433();
            case 2 : formacaoEscolhida = getFormation442();
            case 3 : formacaoEscolhida = getFormation352();
            default : {
                System.out.println("Opção inválida, a formação padrão 4-3-3 será usada.");
                formacaoEscolhida = getFormation433();
            }
        }
        System.out.println("Formação configurada: " + formacaoEscolhida.getDisplayName());
    }

    // === MENU 5: Jogar Jornada ===
    public void playNextRound() {
        if (temporada.isSeasonComplete()) {
            System.out.println("A temporada terminou.");
            return;
        }

        prepareTeamsForRound();
        temporada.simulateRound();
        System.out.println("Jornada " + temporada.getCurrentRound() + " simulada com sucesso.");
    }

    // === Preparação de Equipas ===
    private void prepareTeamsForRound() {
        if (formacaoEscolhida == null) {
            System.out.println("Formação não escolhida. A usar 4-3-3 por defeito.");
            formacaoEscolhida = getFormation433();
        }

        IMatch[] jogos = temporada.getMatches(temporada.getCurrentRound());

        for (int i = 0; i < jogos.length; i++) {
            IMatch jogo = jogos[i];

            ITeam equipaCasa = createTeam(jogo.getHomeClub(), (Formation) formacaoEscolhida);
            ITeam equipaFora = createTeam(jogo.getAwayClub(), (Formation) formacaoEscolhida);

            try {
                jogo.setTeam(equipaCasa);
                jogo.setTeam(equipaFora);
            } catch (Exception e) {
                System.out.println("Erro a definir equipas: " + e.getMessage());
            }
        }
    }

    private ITeam createTeam(IClub club, Formation formation) {
        ITeam team = new Team(club);
        team.setFormation(formation);

        IPlayerSelector selector = formation.getSelector();
        IPlayerPosition[] posicoes = formation.getPositions();

        for (int i = 0; i < posicoes.length; i++) {
            try {
                IPlayer p = selector.selectPlayer(club, posicoes[i]);
                team.addPlayer(p);
            } catch (Exception e) {
                System.out.println("Erro ao adicionar jogador para posição " + posicoes[i].getDescription());
            }
        }

        return team;
    }

    // === Formações ===
    private IFormation getFormation433() {
        IPlayerSelector selector = new PlayerSelector();
        IPlayerPosition[] pos = {
            new PlayerPosition("Goalkeeper"),
            new PlayerPosition("Defender"), new PlayerPosition("Defender"),
            new PlayerPosition("Defender"), new PlayerPosition("Defender"),
            new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"),
            new PlayerPosition("Forward"), new PlayerPosition("Forward"), new PlayerPosition("Forward")
        };
        return new Formation("4-3-3", pos, selector);
    }

    private IFormation getFormation442() {
        IPlayerSelector selector = new PlayerSelector();
        IPlayerPosition[] pos = {
            new PlayerPosition("Goalkeeper"),
            new PlayerPosition("Defender"), new PlayerPosition("Defender"),
            new PlayerPosition("Defender"), new PlayerPosition("Defender"),
            new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"),
            new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"),
            new PlayerPosition("Forward"), new PlayerPosition("Forward")
        };
        return new Formation("4-4-2", pos, selector);
    }

    private IFormation getFormation352() {
        IPlayerSelector selector = new PlayerSelector();
        IPlayerPosition[] pos = {
            new PlayerPosition("Goalkeeper"),
            new PlayerPosition("Defender"), new PlayerPosition("Defender"), new PlayerPosition("Defender"),
            new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"),
            new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"), new PlayerPosition("Midfielder"),
            new PlayerPosition("Forward"), new PlayerPosition("Forward")
        };
        return new Formation("3-5-2", pos, selector);
    }
}
