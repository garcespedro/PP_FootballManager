
package interfaces;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface IFoulEvent extends IEvent {
    IPlayer getFoulingPlayer();  // Jogador que comete a falta
    IPlayer getFouledPlayer();   // Jogador que sofreu a falta
}
