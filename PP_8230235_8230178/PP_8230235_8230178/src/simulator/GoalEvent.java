package simulator;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;

/**
 * Representa um evento de golo (gol) em uma partida.
 * Estende a classe {@link Event} e implementa a interface {@link IGoalEvent}.
 * Contém informações sobre o jogador que marcou o golo e, opcionalmente, o jogador que fez a assistência.
 */
public class GoalEvent extends Event implements IGoalEvent {

    /** Jogador que marcou o golo */
    private final IPlayer scorer;

    /** Jogador que fez a assistência; pode ser nulo caso não haja assistência */
    private final IPlayer assist;

    /**
     * Cria um novo evento de golo.
     *
     * @param minute minuto em que o golo foi marcado; deve estar entre 0 e 120
     * @param scorer jogador que marcou o golo; não pode ser nulo
     * @param club clube do jogador que marcou o golo; não pode ser nulo
     * @param assist jogador que fez a assistência; pode ser nulo caso não haja assistência
     * @throws IllegalArgumentException se o jogador que marcou o golo ou o clube for nulo
     */
    public GoalEvent(int minute, IPlayer scorer, IClub club, IPlayer assist) {
        super(minute, "Golo marcado por " + (scorer != null ? scorer.getName() : "Desconhecido"), scorer, club);

        if (scorer == null) {
            throw new IllegalArgumentException("O jogador que marcou o golo não pode ser nulo.");
        }
        if (club == null) {
            throw new IllegalArgumentException("O clube não pode ser nulo.");
        }

        this.scorer = scorer;
        this.assist = assist;
    }

    /**
     * Retorna o jogador que marcou o golo.
     *
     * @return jogador que marcou o golo
     */
    @Override
    public IPlayer getPlayer() {
        return scorer;
    }

    /**
     * Retorna o jogador que fez a assistência, se houver.
     *
     * @return jogador que fez a assistência ou {@code null} se não houver
     */
    public IPlayer getAssistPlayer() {
        return assist;
    }

    /**
     * Exporta o evento de golo para o formato JSON e imprime no console.
     *
     * @throws IOException se ocorrer erro na exportação (não esperado neste contexto)
     */
    @Override
    public void exportToJson() throws IOException {
        String json = "{\n" +
                "  \"minute\": " + getMinute() + ",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"scorer\": \"" + scorer.getName() + "\",\n" +
                "  \"club\": \"" + (getClub() != null ? getClub().getName() : "N/A") + "\",\n" +
                "  \"assist\": \"" + (assist != null ? assist.getName() : "Nenhuma") + "\"\n" +
                "}";
        System.out.println(json);
    }
}
