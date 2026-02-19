/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/

import com.ppstudios.footballmanager.api.contracts.league.*;
import com.ppstudios.footballmanager.api.contracts.team.*;
import com.ppstudios.footballmanager.api.contracts.player.*;
import com.ppstudios.footballmanager.api.contracts.match.*;
import java.util.Scanner;
import models.*;
import stats.PlayerStats;
import stats.MatchStats;
import stats.HeadToHead;

/**
 * Classe principal que gerencia a lógica do motor do jogo de futebol,
 * incluindo manipulação de liga, temporada, formações, simulação de rodadas,
 * exibição de dados e estatísticas.
 */
public class Engine {

    /**
    * Liga de futebol gerida pelo motor do jogo.
    * Contém informações sobre as temporadas, clubes, calendário, etc.
    */
    private League liga;

   /**
    * Temporada atual da liga que está a ser simulada.
    * Representa o campeonato em curso com jogos, classificações e equipas.
    */
    private Season temporada;

   /**
    * Formação tática escolhida para montar as equipas na simulação das jornadas.
    * Pode ser 4-3-3, 4-4-2, 3-5-2, ou outra formação suportada.
    * Inicialmente nula até ser selecionada pelo utilizador.
    */
    private IFormation formacaoEscolhida = null;

    /**
     * Cria uma nova instância do motor com a liga especificada.
     * Assume que existe pelo menos uma temporada ativa na liga.
     * 
     * @param liga a liga que será gerida pelo motor
     */
    public Engine(League liga) {
        this.liga = liga;
        this.temporada = (Season) liga.getSeasons()[0]; // Assumimos uma única época ativa
    }

    /**
     * Lista os clubes participantes da temporada atual.
     */
    public void listClubs() {
        IClub[] clubes = temporada.getCurrentClubs();
        for (int i = 0; i < clubes.length; i++) {
            System.out.println((i + 1) + ". " + clubes[i].getName());
        }
    }

    /**
     * Exibe os jogadores do clube selecionado pelo índice.
     * 
     * @param index índice do clube na lista (começando em 1)
     */
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

    /**
     * Exibe o calendário completo das jornadas da temporada.
     */
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

    /**
     * Atualiza a tabela de classificação da temporada com base nos jogos já realizados.
     */
    public void updateStandings() {
        IStanding[] tabela = temporada.getLeagueStandings();
        IMatch[] jogos = temporada.getMatches();

        // Resetar todas as classificações
        for (IStanding s : tabela) {
            ((Standing) s).reset();
        }

        // Atualizar com os resultados dos jogos já jogados
        for (IMatch match : jogos) {
            if (match.isPlayed()) {
                for (IStanding s : tabela) {
                    ((Standing) s).updateFromMatch(match);
                }
            }
        }
    }

    /**
     * Exibe a tabela de classificação atualizada da temporada.
     */
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

    /**
     * Define a formação tática escolhida pelo utilizador.
     * 
     * @param option número da opção da formação (1: 4-3-3, 2: 4-4-2, 3: 3-5-2)
     */
    public void setFormationByChoice(int option) {
        switch (option) {
            case 1:
                formacaoEscolhida = getFormation433();
                break;
            case 2:
                formacaoEscolhida = getFormation442();
                break;
            case 3:
                formacaoEscolhida = getFormation352();
                break;
            default:
                System.out.println("Opção inválida, a formação padrão 4-3-3 será usada.");
                formacaoEscolhida = getFormation433();
        }
        System.out.println("Formação configurada: " + formacaoEscolhida.getDisplayName());
    }

    /**
     * Simula a próxima jornada da temporada.
     * Se a temporada já terminou, informa o utilizador.
     */
    public void playNextRound() {
        if (temporada.isSeasonComplete()) {
            System.out.println("A temporada terminou.");
            return;
        }

        int jornadaAntes = temporada.getCurrentRound();
        prepareTeamsForRound();
        temporada.simulateRound();
        System.out.println("Jornada " + jornadaAntes + " simulada com sucesso.");
    }

    /**
     * Prepara as equipas para a próxima jornada, definindo formações e jogadores.
     */
    private void prepareTeamsForRound() {
        if (formacaoEscolhida == null) {
            System.out.println("Formação não escolhida. A usar 4-3-3 por defeito.");
            formacaoEscolhida = getFormation433();
        }

        int currentRound = temporada.getCurrentRound();
        System.out.println("Preparar equipas para a jornada " + currentRound);

        IMatch[] jogos = temporada.getMatches(currentRound);

        for (IMatch jogo : jogos) {
            IClub homeClub = jogo.getHomeClub();
            IClub awayClub = jogo.getAwayClub();

            ITeam equipaCasa = createTeam(homeClub, (Formation) formacaoEscolhida);
            ITeam equipaFora = createTeam(awayClub, (Formation) formacaoEscolhida);

            try {
                jogo.setTeam(equipaCasa);
                jogo.setTeam(equipaFora);
            } catch (Exception e) {
                System.out.println("Erro a definir equipas no jogo " + homeClub.getName() + " vs " + awayClub.getName());
                System.out.println("Detalhes: " + e.getMessage());
            }
        }
    }

    /**
     * Cria uma equipa para um clube com a formação especificada,
     * selecionando jogadores adequados para as posições.
     * 
     * @param club clube para o qual será criada a equipa
     * @param formation formação tática escolhida
     * @return equipa criada com jogadores selecionados
     */
    private ITeam createTeam(IClub club, Formation formation) {
        ITeam team = new Team(club);
        team.setFormation(formation);
        IPlayerSelector selector = formation.getSelector();
        IPlayerPosition[] posicoes = formation.getPositions();

        System.out.println("\nMontando equipa para " + club.getName());

        IPlayer[] jogadoresSelecionados = new IPlayer[posicoes.length];
        int count = 0;

        for (int i = 0; i < posicoes.length; i++) {
            IPlayer selecionado = null;
            for (IPlayer p : club.getPlayers()) {
                if (p.getPosition().getDescription().equalsIgnoreCase(posicoes[i].getDescription()) &&
                    !jogadorJaSelecionado(p, jogadoresSelecionados, count)) {
                    selecionado = p;
                    break;
                }
            }

            if (selecionado != null) {
                try {
                    team.addPlayer(selecionado);
                    jogadoresSelecionados[count++] = selecionado;
                    System.out.println(" -> " + selecionado.getName() + " [" + posicoes[i].getDescription() + "]");
                } catch (Exception e) {
                    System.out.println(" -> Erro ao adicionar " + selecionado.getName() + ": " + e.getMessage());
                }
            } else {
                System.out.println(" -> Nenhum jogador disponível para posição " + posicoes[i].getDescription());
            }
        }

        return team;
    }

    /**
     * Verifica se um jogador já foi selecionado para a equipa.
     * 
     * @param jogador jogador a verificar
     * @param selecionados array de jogadores já selecionados
     * @param limite número de jogadores já selecionados válidos no array
     * @return true se o jogador já foi selecionado, false caso contrário
     */
    private boolean jogadorJaSelecionado(IPlayer jogador, IPlayer[] selecionados, int limite) {
        for (int i = 0; i < limite; i++) {
            if (selecionados[i] != null && selecionados[i].equals(jogador)) {
                return true;
            }
        }
        return false;
    }
    
    // === Formações ===

    /**
     * Retorna a formação 4-3-3 padrão.
     * 
     * @return formação 4-3-3
     */
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

    /**
     * Retorna a formação 4-4-2 padrão.
     * 
     * @return formação 4-4-2
     */
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

    /**
     * Retorna a formação 3-5-2 padrão.
     * 
     * @return formação 3-5-2
     */
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

    // === MÉTODOS DE ESTATÍSTICAS ===

    /**
     * Exibe as estatísticas de jogadores de um clube escolhido pelo utilizador.
     */
    public void showPlayerStats() {
        IClub[] clubes = temporada.getCurrentClubs();
        for (int i = 0; i < clubes.length; i++)
            System.out.println((i + 1) + ". " + clubes[i].getName());
        System.out.print("Escolha a equipa: ");
        int idx = readInt();
        if (idx < 1 || idx > clubes.length) {
            System.out.println("Índice inválido.");
            return;
        }
        IClub club = clubes[idx - 1];
        PlayerStats[] stats = PlayerStats.computeStats(club.getPlayers(), temporada.getMatches());
        System.out.println("Jogador | Golos | Assistências");
        for (PlayerStats ps : stats)
            System.out.printf("%s | %d | %d\n", ps.getPlayer().getName(), ps.getGoals(), ps.getAssists());
    }

    /**
     * Exibe as estatísticas de um jogo escolhido pelo utilizador.
     */
    public void showMatchStats() {
        IMatch[] matches = temporada.getMatches();
        for (int i = 0; i < matches.length; i++)
            System.out.println((i + 1) + ". " + temporada.displayMatchResult(matches[i]));
        System.out.print("Escolha o jogo: ");
        int idx = readInt();
        if (idx < 1 || idx > matches.length) {
            System.out.println("Índice inválido.");
            return;
        }
        MatchStats ms = new MatchStats(matches[idx - 1]);
        ms.printStats();
    }

    /**
     * Exibe o histórico de confrontos diretos entre duas equipas escolhidas pelo utilizador.
     */
    public void showHeadToHead() {
        IClub[] clubes = temporada.getCurrentClubs();
        for (int i = 0; i < clubes.length; i++)
            System.out.println((i + 1) + ". " + clubes[i].getName());
        System.out.print("Escolha a primeira equipa: ");
        int idxA = readInt();
        System.out.print("Escolha a segunda equipa: ");
        int idxB = readInt();
        if (idxA < 1 || idxA > clubes.length || idxB < 1 || idxB > clubes.length || idxA == idxB) {
            System.out.println("Índice(s) inválido(s).");
            return;
        }
        HeadToHead.printHistory(clubes[idxA - 1], clubes[idxB - 1], temporada.getMatches());
    }

    /**
     * Método auxiliar para leitura segura de números inteiros a partir da consola.
     * 
     * @return inteiro lido
     */
    private int readInt() {
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNextInt()) {
            System.out.print("Por favor insira um número válido: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
