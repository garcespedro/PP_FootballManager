
package interfaces;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public interface ISubstitutionEvent extends IEvent {
    IPlayer getPlayerOut();
    IPlayer getPlayerIn();
}
