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
import interfaces.ISubstitutionEvent;

/**
 * Representa um evento de substituição durante uma partida de futebol.
 * Extende a classe {@link Event} e implementa a interface {@link ISubstitutionEvent}.
 * 
 * Cada evento contém o jogador que saiu, o jogador que entrou, o minuto em que ocorreu e o clube associado.
 */
public class SubstitutionEvent extends Event implements ISubstitutionEvent {

    private final IPlayer playerOut;
    private final IPlayer playerIn;

    /**
     * Construtor para criar um evento de substituição.
     * 
     * @param minute    O minuto da partida em que ocorreu a substituição (0-120).
     * @param playerOut O jogador que saiu do campo (não pode ser nulo).
     * @param playerIn  O jogador que entrou no campo (não pode ser nulo).
     * @param club      O clube associado à substituição.
     * @throws IllegalArgumentException se algum dos jogadores for nulo.
     */
    public SubstitutionEvent(int minute, IPlayer playerOut, IPlayer playerIn, IClub club) {
        super(minute, "Substituição: Sai " + playerOut.getName() + ", Entra " + playerIn.getName(), playerIn, club);

        if (playerOut == null || playerIn == null) {
            throw new IllegalArgumentException("Jogadores da substituição não podem ser nulos.");
        }

        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    /**
     * Obtém o jogador que saiu do campo.
     * 
     * @return O jogador substituído (playerOut).
     */
    @Override
    public IPlayer getPlayerOut() {
        return playerOut;
    }

    /**
     * Obtém o jogador que entrou em campo.
     * 
     * @return O jogador que entrou (playerIn).
     */
    @Override
    public IPlayer getPlayerIn() {
        return playerIn;
    }
}
