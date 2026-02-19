package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import interfaces.IInjuryEvent;

public class InjuryEvent extends Event implements IInjuryEvent {

    private final IPlayer injuredPlayer;
    private final boolean severe;

    public InjuryEvent(int minute, IPlayer injuredPlayer, boolean severe, IClub club) {
        super(minute,
              "Lesão de " + injuredPlayer.getName() + (severe ? " (grave)" : ""),
              injuredPlayer,
              club);

        if (injuredPlayer == null) {
            throw new IllegalArgumentException("O jogador lesionado não pode ser nulo.");
        }

        this.injuredPlayer = injuredPlayer;
        this.severe = severe;
    }

    @Override
    public IPlayer getInjuredPlayer() {
        return injuredPlayer;
    }

    @Override
    public boolean isSevere() {
        return severe;
    }
}
