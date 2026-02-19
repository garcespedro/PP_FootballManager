
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
 * Representa um evento de cartão ocorrido durante uma partida de futebol.
 * Este evento está associado a um jogador e ao tipo de cartão recebido.
 */
public interface ICardEvent extends IEvent {

    /**
     * Obtém o jogador que recebeu o cartão.
     *
     * @return o jogador associado ao evento do cartão
     */
    IPlayer getPlayer();

    /**
     * Obtém o tipo do cartão recebido pelo jogador.
     * Pode ser "Amarelo" ou "Vermelho".
     *
     * @return o tipo do cartão como uma String
     */
    String getCardType();
}
