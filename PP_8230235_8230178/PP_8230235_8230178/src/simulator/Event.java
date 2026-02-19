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
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;

/**
 * Representa um evento ocorrido durante uma partida de futebol.
 * Cada evento está associado a um minuto da partida, uma descrição,
 * um jogador e um clube.
 * Implementa a interface {@link IEvent}.
 */
public class Event implements IEvent {

    /** Minuto em que o evento ocorreu, entre 0 e 120 */
    private final int minute;

    /** Descrição textual do evento */
    private final String description;

    /** Jogador relacionado ao evento (pode ser null) */
    private final IPlayer player;

    /** Clube relacionado ao evento (pode ser null) */
    private final IClub club;

    /**
     * Construtor para criar um evento.
     *
     * @param minute minuto em que o evento ocorreu (0 a 120)
     * @param description descrição do evento, não pode ser nula ou vazia
     * @param player jogador relacionado ao evento (pode ser null)
     * @param club clube relacionado ao evento (pode ser null)
     * @throws IllegalArgumentException se minuto estiver fora do intervalo 0-120 ou descrição for nula/vazia
     */
    public Event(int minute, String description, IPlayer player, IClub club) {
        if (minute < 0 || minute > 120) {
            throw new IllegalArgumentException("O minuto do evento deve estar entre 0 e 120.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do evento não pode ser nula ou vazia.");
        }

        this.minute = minute;
        this.description = description;
        this.player = player;
        this.club = club;
    }

    /**
     * Retorna o minuto em que o evento ocorreu.
     *
     * @return minuto do evento
     */
    @Override
    public int getMinute() {
        return minute;
    }

    /**
     * Retorna a descrição do evento.
     *
     * @return descrição do evento
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Retorna o jogador relacionado ao evento.
     *
     * @return jogador associado ao evento, ou null se não houver
     */
    public IPlayer getPlayer() {
        return player;
    }

    /**
     * Retorna o clube relacionado ao evento.
     *
     * @return clube associado ao evento, ou null se não houver
     */
    public IClub getClub() {
        return club;
    }

    /**
     * Exporta os dados do evento no formato JSON para saída padrão.
     *
     * @throws IOException caso ocorra erro de I/O durante a exportação
     */
    @Override
    public void exportToJson() throws IOException {
        String json = "{\n" +
                "  \"minute\": " + minute + ",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"player\": \"" + (player != null ? player.getName() : "N/A") + "\",\n" +
                "  \"club\": \"" + (club != null ? club.getName() : "N/A") + "\"\n" +
                "}";
        System.out.println(json);
    }
}
