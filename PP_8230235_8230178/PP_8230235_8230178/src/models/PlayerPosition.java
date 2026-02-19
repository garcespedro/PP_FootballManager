
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
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

/**
 * Representa a posição de um jogador em campo, como "Atacante", "Meio-campo", "Zagueiro", etc.
 */
public class PlayerPosition implements IPlayerPosition {

    /**
     * Descrição textual da posição do jogador.
     */
    private final String description;

    /**
     * Cria uma nova posição de jogador com a descrição fornecida.
     *
     * @param description Descrição da posição (ex: "Atacante").
     * @throws IllegalArgumentException se a descrição for nula.
     */
    public PlayerPosition(String description) {
        if (description == null) {
            throw new IllegalArgumentException("A descrição da posição não pode ser nula");
        }
        this.description = description;
    }

    /**
     * Retorna a descrição da posição do jogador.
     *
     * @return descrição da posição.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Retorna uma representação textual da posição do jogador.
     *
     * @return string com a descrição formatada da posição.
     */
    @Override
    public String toString() {
        return "PlayerPosition{" +
                "description='" + description + '\'' +
                '}';
    }
}
