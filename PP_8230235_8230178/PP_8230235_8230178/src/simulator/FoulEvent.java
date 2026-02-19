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
import interfaces.IFoulEvent;

/**
 * Representa um evento de falta em uma partida.
 * Estende a classe {@link Event} e implementa a interface {@link IFoulEvent}.
 * Contém informações sobre o jogador que cometeu a falta e o jogador que sofreu a falta.
 */
public class FoulEvent extends Event implements IFoulEvent {

    /** Jogador que cometeu a falta */
    private final IPlayer foulingPlayer;

    /** Jogador que sofreu a falta */
    private final IPlayer fouledPlayer;

    /**
     * Cria um novo evento de falta.
     *
     * @param minute minuto em que a falta ocorreu; deve estar entre 0 e 120
     * @param foulingPlayer jogador que cometeu a falta; não pode ser nulo
     * @param fouledPlayer jogador que sofreu a falta; não pode ser nulo
     * @param club clube do jogador que cometeu a falta
     * @throws IllegalArgumentException se algum dos jogadores for nulo
     */
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

    /**
     * Retorna o jogador que cometeu a falta.
     *
     * @return jogador que cometeu a falta
     */
    @Override
    public IPlayer getFoulingPlayer() {
        return foulingPlayer;
    }

    /**
     * Retorna o jogador que sofreu a falta.
     *
     * @return jogador que sofreu a falta
     */
    @Override
    public IPlayer getFouledPlayer() {
        return fouledPlayer;
    }
}
