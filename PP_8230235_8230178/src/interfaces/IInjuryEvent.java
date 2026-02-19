
package interfaces;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface IInjuryEvent extends IEvent {
    IPlayer getInjuredPlayer();
    boolean isSevere(); // Indica se a lesão é grave
}
