
package interfaces;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface ICardEvent extends IEvent {
    IPlayer getPlayer();
    String getCardType(); // "Amarelo" ou "Vermelho"
}