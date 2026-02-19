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
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.simulation.MatchSimulatorStrategy;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import simulator.GoalEvent;

/**
 * Representa uma temporada esportiva com um conjunto de clubes,
 * calendário de jogos, classificação e lógica para simulação das partidas.
 * 
 * Implementa a interface {@link ISeason}.
 */
public class Season implements ISeason {
    /** Ano da temporada */
    private final int year;
    
    /** Nome da temporada */
    private final String name;
    
    /** Número máximo de clubes permitidos na temporada */
    private final int maxTeams;
    
    /** Número máximo de rodadas na temporada */
    private final int maxRounds;
    
    /** Array dos clubes participantes */
    private IClub[] clubs;
    
    /** Objeto que gerencia o calendário de jogos */
    private Schedule schedule;
    
    /** Rodada atual da temporada */
    private int currentRound;
    
    /** Indica se a temporada foi concluída */
    private boolean seasonComplete;
    
    /** Estratégia para simular as partidas */
    private MatchSimulatorStrategy matchSimulatorStrategy;
    
    /** Array de objetos que representam a classificação dos times */
    private IStanding[] standings;
    
    /** Capacidade inicial padrão do array de clubes */
    private static final int INITIAL_CLUB_CAPACITY = 20;

    /**
     * Construtor da temporada.
     * 
     * @param year Ano da temporada
     * @param name Nome da temporada
     * @param maxTeams Número máximo de clubes permitidos
     * @param maxRounds Número máximo de rodadas na temporada
     */
    public Season(int year, String name, int maxTeams, int maxRounds) {
        this.year = year;
        this.name = name;
        this.maxTeams = maxTeams;
        this.maxRounds = maxRounds;
        this.clubs = new IClub[maxTeams];
        this.schedule = new Schedule(maxRounds, maxTeams * (maxTeams - 1) / 2);
        this.currentRound = 1;
        this.seasonComplete = false;
        this.matchSimulatorStrategy = null;
        this.standings = null;
    }

    /**
     * Retorna o ano da temporada.
     * 
     * @return ano da temporada
     */
    @Override
    public int getYear() {
        return year;
    }

    /**
     * Adiciona um clube à temporada, se houver espaço.
     * 
     * @param iclub Clube a ser adicionado
     * @return true se o clube foi adicionado com sucesso, false caso contrário
     */
    @Override
    public boolean addClub(IClub iclub) {
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] == null) {
                clubs[i] = iclub;
                return true;
            }
        }
        return false;
    }

    /**
     * Remove um clube da temporada.
     * 
     * @param iclub Clube a ser removido
     * @return true se o clube foi removido, false se não encontrado
     */
    @Override
    public boolean removeClub(IClub iclub) {
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] != null && clubs[i].equals(iclub)) {
                clubs[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Inicializa a tabela de classificação com os clubes atualmente registrados.
     */
    private void initializeStandings() {
        int numberOfCurrentTeams = getNumberOfCurrentTeams();
        standings = new IStanding[numberOfCurrentTeams];
        int index = 0;
        for (int i = 0; i < clubs.length; i++) {
            if (clubs[i] != null) {
                ITeam team = new Team(clubs[i]);
                standings[index++] = new Standing(team);
            }
        }
    }

    /**
     * Gera o calendário de jogos para a temporada.
     * 
     * @throws IllegalStateException se houver menos de dois clubes registrados
     * ou se a geração ultrapassar o número esperado de partidas
     */
    @Override
    public void generateSchedule() {
        if (getNumberOfCurrentTeams() < 2) {
            throw new IllegalStateException("A temporada precisa de pelo menos dois clubes para gerar o agendamento.");
        }

        initializeStandings();

        int matchCounter = 0;
        int maxMatches = maxRounds * (getNumberOfCurrentTeams() / 2);

        for (int round = 1; round <= maxRounds; round++) {
            for (int i = 0; i < clubs.length; i++) {
                for (int j = i + 1; j < clubs.length; j++) {
                    if (clubs[i] != null && clubs[j] != null) {
                        if (matchCounter >= maxMatches) {
                            throw new IllegalStateException("Tentativa de criar mais partidas do que o esperado.");
                        }
                        IMatch match = new Match(round, clubs[i], clubs[j]);
                        schedule.setMatch(matchCounter, match);
                        matchCounter++;
                    }
                }
            }
        }
    }

    /**
     * Atualiza a classificação com base nos resultados das partidas já jogadas.
     */
    public void updateStandings() {
        if (standings == null) {
            initializeStandings();
        }
        for (int i = 0; i < standings.length; i++) {
            ((Standing) standings[i]).reset();
        }

        IMatch[] allMatches = schedule.getAllMatches();

        for (int i = 0; i < allMatches.length; i++) {
            IMatch match = allMatches[i];
            if (match.isPlayed()) {
                for (int j = 0; j < standings.length; j++) {
                    ((Standing) standings[j]).updateFromMatch(match);
                }
            }
        }
    }

    /**
     * Retorna todas as partidas agendadas da temporada.
     * 
     * @return array com todas as partidas
     */
    @Override
    public IMatch[] getMatches() {
        return schedule.getAllMatches();
    }

    /**
     * Retorna as partidas agendadas para uma rodada específica.
     * 
     * @param round número da rodada
     * @return array com as partidas da rodada
     */
    @Override
    public IMatch[] getMatches(int round) {
        return schedule.getMatchesForRound(round);
    }

    /**
     * Simula todas as partidas da rodada atual.
     * 
     * @throws IllegalStateException se a temporada já foi concluída
     * ou se a estratégia de simulação não foi definida
     */
    @Override
    public void simulateRound() {
        if (currentRound > maxRounds) {
            throw new IllegalStateException("A temporada já foi concluída.");
        }

        IMatch[] matchesForRound = schedule.getMatchesForRound(currentRound);
        for (IMatch match : matchesForRound) {
            if (!match.isPlayed()) {
                if (matchSimulatorStrategy == null) {
                    throw new IllegalStateException("Match simulator strategy não foi definido.");
                }
                matchSimulatorStrategy.simulate(match);
            }
        }

        updateStandings();

        currentRound++;
    }

    /**
     * Simula toda a temporada até o final.
     */
    @Override
    public void simulateSeason() {
        while (currentRound <= maxRounds) {
            simulateRound();
        }
        seasonComplete = true;
    }

    /**
     * Retorna o número da rodada atual.
     * 
     * @return rodada atual
     */
    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Indica se a temporada já foi concluída.
     * 
     * @return true se concluída, false caso contrário
     */
    @Override
    public boolean isSeasonComplete() {
        return seasonComplete;
    }

    /**
     * Reseta a temporada, limpando clubes, classificações, calendário e status.
     */
    @Override
    public void resetSeason() {
        this.clubs = new IClub[INITIAL_CLUB_CAPACITY];
        this.standings = null;
        this.schedule = new Schedule(maxRounds, maxTeams * (maxTeams - 1) / 2);
        this.currentRound = 1;
        this.seasonComplete = false;
    }

    /**
     * Exibe o resultado de uma partida formatado.
     * 
     * @param match partida a ser exibida
     * @return string com o resultado ou mensagem indicando que a partida não foi jogada
     */
    @Override
    public String displayMatchResult(IMatch match) {
        if (match.isPlayed()) {
            int homeGoals = match.getTotalByEvent(GoalEvent.class, match.getHomeClub());
            int awayGoals = match.getTotalByEvent(GoalEvent.class, match.getAwayClub());
            return match.getHomeClub().getName() + " " + homeGoals + " - " + awayGoals + " " + match.getAwayClub().getName();
        } else {
            return match.getHomeClub().getName() + " vs " + match.getAwayClub().getName() + " - Jogo não jogado";
        }
    }

    /**
     * Define a estratégia a ser utilizada para simular as partidas.
     * 
     * @param mss estratégia de simulação
     */
    @Override
    public void setMatchSimulator(MatchSimulatorStrategy mss) {
        this.matchSimulatorStrategy = mss;
    }

    /**
     * Retorna a classificação atual da liga.
     * 
     * @return array com a classificação dos times
     */
    @Override
    public IStanding[] getLeagueStandings() {
        if (standings == null) {
            initializeStandings();
        }
        updateStandings();
        return standings;
    }

    /**
     * Retorna o calendário de jogos da temporada.
     * 
     * @return objeto que representa o calendário
     */
    @Override
    public ISchedule getSchedule() {
        return schedule;
    }

    /**
     * Retorna a pontuação atribuída por vitória.
     * 
     * @return pontos por vitória (normalmente 3)
     */
    @Override
    public int getPointsPerWin() {
        return 3;
    }

    /**
     * Retorna a pontuação atribuída por empate.
     * 
     * @return pontos por empate (normalmente 1)
     */
    @Override
    public int getPointsPerDraw() {
        return 1;
    }

    /**
     * Retorna a pontuação atribuída por derrota.
     * 
     * @return pontos por derrota (normalmente 0)
     */
    @Override
    public int getPointsPerLoss() {
        return 0;
    }

    /**
     * Retorna o nome da temporada.
     * 
     * @return nome da temporada
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retorna o número máximo de clubes permitidos na temporada.
     * 
     * @return número máximo de clubes
     */
    @Override
    public int getMaxTeams() {
        return maxTeams;
    }

    /**
     * Retorna o número máximo de rodadas da temporada.
     * 
     * @return número máximo de rodadas
     */
    @Override
    public int getMaxRounds() {
        return maxRounds;
    }

    /**
     * Retorna o número total de partidas agendadas na temporada.
     * 
     * @return número total de partidas
     */
    @Override
    public int getCurrentMatches() {
        return schedule.getAllMatches().length;
    }

    /**
     * Retorna o número de clubes atualmente registrados na temporada.
     * 
     * @return número de clubes atuais
     */
    @Override
    public int getNumberOfCurrentTeams() {
        int count = 0;
        for (IClub club : clubs) {
            if (club != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retorna um array com os clubes atualmente registrados na temporada.
     * 
     * @return array com os clubes atuais
     */
    @Override
    public IClub[] getCurrentClubs() {
        IClub[] currentClubs = new IClub[getNumberOfCurrentTeams()];
        int index = 0;
        for (IClub club : clubs) {
            if (club != null) {
                currentClubs[index++] = club;
            }
        }
        return currentClubs;
    }

    /**
     * Exporta os dados da temporada para formato JSON.
     * 
     * @throws IOException em caso de erro na exportação
     * @throws UnsupportedOperationException se o método não for suportado
     */
    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}