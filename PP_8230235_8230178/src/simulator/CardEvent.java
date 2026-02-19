
package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import interfaces.ICardEvent;

public class CardEvent extends Event implements ICardEvent {

    private final IPlayer player;
    private final String cardType; // "Amarelo" ou "Vermelho"

    public CardEvent(int minute, IPlayer player, IClub club, String cardType) {
        super(minute, "Cartão " + cardType, player, club);

        if (!cardType.equalsIgnoreCase("Amarelo") && !cardType.equalsIgnoreCase("Vermelho")) {
            throw new IllegalArgumentException("Tipo de cartão inválido: " + cardType);
        }

        this.player = player;
        this.cardType = cardType;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    @Override
    public String getCardType() {
        return this.cardType;
    }
}
