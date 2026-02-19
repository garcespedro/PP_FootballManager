
package simulator;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import interfaces.ICardEvent;

/**
 * Representa um evento de cartão (amarelo ou vermelho) em uma partida de futebol.
 * Estende a classe {@link Event} e implementa a interface {@link ICardEvent}.
 */
public class CardEvent extends Event implements ICardEvent {

    /** Jogador que recebeu o cartão */
    private final IPlayer player;

    /** Tipo do cartão, que pode ser "Amarelo" ou "Vermelho" */
    private final String cardType;

    /**
     * Construtor para criar um evento de cartão.
     * 
     * @param minute minuto em que o cartão foi dado
     * @param player jogador que recebeu o cartão
     * @param club clube do jogador
     * @param cardType tipo do cartão ("Amarelo" ou "Vermelho")
     * @throws IllegalArgumentException se o tipo do cartão não for "Amarelo" ou "Vermelho"
     */
    public CardEvent(int minute, IPlayer player, IClub club, String cardType) {
        super(minute, "Cartão " + cardType, player, club);

        if (!cardType.equalsIgnoreCase("Amarelo") && !cardType.equalsIgnoreCase("Vermelho")) {
            throw new IllegalArgumentException("Tipo de cartão inválido: " + cardType);
        }

        this.player = player;
        this.cardType = cardType;
    }

    /**
     * Retorna o jogador que recebeu o cartão.
     * 
     * @return jogador que recebeu o cartão
     */
    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     * Retorna o tipo do cartão ("Amarelo" ou "Vermelho").
     * 
     * @return tipo do cartão
     */
    @Override
    public String getCardType() {
        return this.cardType;
    }
}
