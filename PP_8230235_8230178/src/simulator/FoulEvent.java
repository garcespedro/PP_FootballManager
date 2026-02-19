package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import interfaces.IFoulEvent;

public class FoulEvent extends Event implements IFoulEvent {

    private final IPlayer foulingPlayer;
    private final IPlayer fouledPlayer;

    public FoulEvent(int minute, IPlayer foulingPlayer, IPlayer fouledPlayer, IClub club) {
        super(minute,
              "Falta cometida por " + foulingPlayer.getName() + " sobre " + fouledPlayer.getName(),
              foulingPlayer,
              club);

        if (foulingPlayer == null || fouledPlayer == null) {
            throw new IllegalArgumentException("Jogadores da falta não podem ser nulos.");
        }

        this.foulingPlayer = foulingPlayer;
        this.fouledPlayer = fouledPlayer;
    }

    @Override
    public IPlayer getFoulingPlayer() {
        return foulingPlayer;
    }

    @Override
    public IPlayer getFouledPlayer() {
        return fouledPlayer;
    }
}
