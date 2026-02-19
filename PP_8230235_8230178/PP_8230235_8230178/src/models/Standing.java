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

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 * Representa a posição de um time na tabela de classificação,
 * armazenando pontos, vitórias, empates, derrotas, gols marcados e sofridos.
 * 
 * Implementa a interface {@link IStanding}.
 */
public class Standing implements IStanding {

    /** Time ao qual esta classificação pertence */
    private ITeam team;

    /** Pontos acumulados */
    private int points;

    /** Número de vitórias */
    private int wins;

    /** Número de empates */
    private int draws;

    /** Número de derrotas */
    private int losses;

    /** Gols marcados */
    private int goalsScored;

    /** Gols sofridos */
    private int goalsConceded;

    /**
     * Construtor que inicializa a classificação para um time específico.
     * 
     * @param team time que será representado na classificação
     * @throws IllegalStateException se o time for nulo
     */
    public Standing(ITeam team) {
        if (team == null) throw new IllegalStateException("Team must be initialized");
        this.team = team;
        reset();
    }

    /**
     * Reseta todas as estatísticas da classificação para zero.
     */
    public void reset() {
        points = 0;
        wins = 0;
        draws = 0;
        losses = 0;
        goalsScored = 0;
        goalsConceded = 0;
    }

    /**
     * Retorna o time associado a esta classificação.
     * 
     * @return o time
     */
    @Override
    public ITeam getTeam() {
        return team;
    }

    /**
     * Retorna os pontos acumulados pelo time.
     * 
     * @return pontos do time
     */
    @Override
    public int getPoints() {
        return points;
    }

    /**
     * Adiciona pontos ao total da classificação.
     * 
     * @param p pontos a adicionar (não pode ser negativo)
     * @throws IllegalArgumentException se pontos forem negativos
     */
    public void addPoints(int p) {
        if (p < 0) throw new IllegalArgumentException("Points cannot be negative");
        points += p;
    }

    /**
     * Incrementa o número de vitórias e adiciona os pontos correspondentes.
     */
    public void addWin() {
        wins++;
        addPoints(3);
    }

    /**
     * Incrementa o número de empates e adiciona os pontos correspondentes.
     */
    public void addDraw() {
        draws++;
        addPoints(1);
    }

    /**
     * Incrementa o número de derrotas.
     */
    public void addLoss() {
        losses++;
    }

    /**
     * Retorna o número de vitórias.
     * 
     * @return número de vitórias
     */
    @Override
    public int getWins() {
        return wins;
    }

    /**
     * Retorna o número de empates.
     * 
     * @return número de empates
     */
    @Override
    public int getDraws() {
        return draws;
    }

    /**
     * Retorna o número de derrotas.
     * 
     * @return número de derrotas
     */
    @Override
    public int getLosses() {
        return losses;
    }

    /**
     * Retorna o total de partidas jogadas.
     * 
     * @return soma de vitórias, empates e derrotas
     */
    @Override
    public int getTotalMatches() {
        return wins + draws + losses;
    }

    /**
     * Retorna o total de gols marcados pelo time.
     * 
     * @return gols marcados
     */
    @Override
    public int getGoalScored() {
        return goalsScored;
    }

    /**
     * Retorna o total de gols sofridos pelo time.
     * 
     * @return gols sofridos
     */
    @Override
    public int getGoalsConceded() {
        return goalsConceded;
    }

    /**
     * Retorna a diferença de gols (gols marcados menos gols sofridos).
     * 
     * @return diferença de gols
     */
    @Override
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    /**
     * Atualiza a classificação do time com base no resultado de uma partida.
     * Apenas atualiza se a partida foi jogada e envolve o time desta classificação.
     * 
     * @param match partida usada para atualizar a classificação
     */
    public void updateFromMatch(IMatch match) {
        if (!match.isPlayed()) return;

        if (!(match instanceof Match)) {
            System.err.println("Ignorando match que não é da classe Match.");
            return;
        }

        Match realMatch = (Match) match;

        ITeam home = realMatch.getHomeTeam();
        ITeam away = realMatch.getAwayTeam();

        int homeGoals = realMatch.getHomeGoals();
        int awayGoals = realMatch.getAwayGoals();

        boolean isHome = team.equals(home);
        boolean isAway = team.equals(away);

        if (!isHome && !isAway) return;

        int scored = isHome ? homeGoals : awayGoals;
        int conceded = isHome ? awayGoals : homeGoals;

        goalsScored += scored;
        goalsConceded += conceded;

        if (homeGoals == awayGoals) {
            addDraw();
        } else if ((isHome && homeGoals > awayGoals) || (isAway && awayGoals > homeGoals)) {
            addWin();
        } else {
            addLoss();
        }
    }

    /**
     * Método não suportado para adicionar múltiplas vitórias.
     * 
     * @throws UnsupportedOperationException sempre
     */
    @Override
    public void addWin(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método não suportado para adicionar múltiplos empates.
     * 
     * @throws UnsupportedOperationException sempre
     */
    @Override
    public void addDraw(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método não suportado para adicionar múltiplas derrotas.
     * 
     * @throws UnsupportedOperationException sempre
     */
    @Override
    public void addLoss(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

