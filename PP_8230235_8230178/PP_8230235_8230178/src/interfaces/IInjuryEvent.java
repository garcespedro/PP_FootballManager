
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
 * Representa um evento de lesão ocorrido durante uma partida de futebol,
 * contendo informações sobre o jogador lesionado e a gravidade da lesão.
 */
public interface IInjuryEvent extends IEvent {

    /**
     * Obtém o jogador que sofreu a lesão.
     *
     * @return o jogador lesionado
     */
    IPlayer getInjuredPlayer();

    /**
     * Indica se a lesão sofrida pelo jogador é grave.
     *
     * @return true se a lesão for grave, false caso contrário
     */
    boolean isSevere();
}
