
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
 * Representa um evento de falta ocorrido durante uma partida de futebol,
 * incluindo o jogador que cometeu a falta e o jogador que sofreu a falta.
 */
public interface IFoulEvent extends IEvent {

    /**
     * Obtém o jogador que cometeu a falta.
     *
     * @return o jogador responsável pela falta
     */
    IPlayer getFoulingPlayer();

    /**
     * Obtém o jogador que sofreu a falta.
     *
     * @return o jogador que sofreu a falta
     */
    IPlayer getFouledPlayer();
}

