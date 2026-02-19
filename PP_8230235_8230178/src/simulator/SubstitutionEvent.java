package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import interfaces.ISubstitutionEvent;

public class SubstitutionEvent extends Event implements ISubstitutionEvent {

    private final IPlayer playerOut;
    private final IPlayer playerIn;

    public SubstitutionEvent(int minute, IPlayer playerOut, IPlayer playerIn, IClub club) {
        super(minute, "Substituição: Sai " + playerOut.getName() + ", Entra " + playerIn.getName(), playerIn, club);

        if (playerOut == null || playerIn == null) {
            throw new IllegalArgumentException("Jogadores da substituição não podem ser nulos.");
        }

        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    @Override
    public IPlayer getPlayerOut() {
        return playerOut;
    }

    @Override
    public IPlayer getPlayerIn() {
        return playerIn;
    }
}

