
package interfaces;

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

/**
 * Representa um evento de substituição durante uma partida de futebol,
 * indicando o jogador que saiu e o jogador que entrou em campo.
 */
public interface ISubstitutionEvent extends IEvent {

    /**
     * Obtém o jogador que foi substituído, ou seja, saiu de campo.
     *
     * @return o jogador que saiu
     */
    IPlayer getPlayerOut();

    /**
     * Obtém o jogador que entrou em campo na substituição.
     *
     * @return o jogador que entrou
     */
    IPlayer getPlayerIn();
}

