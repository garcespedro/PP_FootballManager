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
import interfaces.IInjuryEvent;

/**
 * Representa um evento de lesão em uma partida.
 * Estende a classe {@link Event} e implementa a interface {@link IInjuryEvent}.
 * Contém informações sobre o jogador lesionado e a gravidade da lesão.
 */
public class InjuryEvent extends Event implements IInjuryEvent {

    /** Jogador que sofreu a lesão */
    private final IPlayer injuredPlayer;

    /** Indica se a lesão é grave */
    private final boolean severe;

    /**
     * Cria um novo evento de lesão.
     *
     * @param minute minuto em que a lesão ocorreu; deve estar entre 0 e 120
     * @param injuredPlayer jogador que sofreu a lesão; não pode ser nulo
     * @param severe indica se a lesão é grave (true) ou não (false)
     * @param club clube do jogador lesionado; pode ser nulo
     * @throws IllegalArgumentException se o jogador lesionado for nulo
     */
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

    /**
     * Retorna o jogador que sofreu a lesão.
     *
     * @return jogador lesionado
     */
    @Override
    public IPlayer getInjuredPlayer() {
        return injuredPlayer;
    }

    /**
     * Indica se a lesão é grave.
     *
     * @return {@code true} se a lesão for grave, {@code false} caso contrário
     */
    @Override
    public boolean isSevere() {
        return severe;
    }
}
