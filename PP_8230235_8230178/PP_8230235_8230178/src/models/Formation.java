
package models;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

/**
 * Representa uma formação tática de futebol, contendo uma lista de posições e um seletor de jogadores.
 * Implementa a interface IFormation.
 */
public class Formation implements IFormation {

    /**
     * Nome legível da formação, por exemplo, "4-3-3".
     */
    private final String displayName;

    /**
     * Array de posições que compõem esta formação.
     */
    private final IPlayerPosition[] positions;

    /**
     * Seletor usado para escolher jogadores para as posições desta formação.
     */
    private final IPlayerSelector selector;

    /**
     * Construtor que inicializa a formação com o nome, posições e seletor fornecidos.
     * 
     * @param displayName Nome legível da formação (não pode ser nulo)
     * @param positions Array das posições da formação (não pode ser nulo)
     * @param selector Seletor de jogadores para a formação (não pode ser nulo)
     * @throws IllegalArgumentException se algum parâmetro for nulo
     */
    public Formation(String displayName, IPlayerPosition[] positions, IPlayerSelector selector) {
        if (displayName == null || positions == null || selector == null) {
            throw new IllegalArgumentException("Display name, positions, and selector must not be null.");
        }

        this.displayName = displayName;
        this.positions = positions;
        this.selector = selector;
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Calcula a vantagem tática desta formação em relação a outra,
     * baseado na diferença do número de médios ("Midfielder").
     * 
     * @param other Formação adversária para comparação (não pode ser nula)
     * @return Valor positivo se esta formação tem mais médios, negativo se menos, zero se igual
     * @throws IllegalArgumentException se a formação adversária for nula
     * @throws IllegalStateException se a formação adversária não for do tipo Formation
     */
    @Override
    public int getTacticalAdvantage(IFormation other) {
        if (other == null) {
            throw new IllegalArgumentException("A formação adversária não pode ser nula.");
        }

        if (!(other instanceof Formation)) {
            throw new IllegalStateException("A comparação requere que estejam no mesmo formato.");
        }

        Formation opponent = (Formation) other;
        int myMidfielders = countPlayersByPosition("Midfielder");
        int opponentMidfielders = opponent.countPlayersByPosition("Midfielder");

        return myMidfielders - opponentMidfielders;
    }

    /**
     * Conta quantas posições na formação correspondem a uma descrição de posição dada.
     * 
     * @param positionDescription Descrição da posição a contar (exemplo: "Midfielder")
     * @return Número de posições com essa descrição
     */
    private int countPlayersByPosition(String positionDescription) {
        int count = 0;
        for (int i = 0; i < positions.length; i++) {
            IPlayerPosition pos = positions[i];
            if (pos != null && pos.getDescription() != null &&
                pos.getDescription().equalsIgnoreCase(positionDescription)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtém as posições que constituem esta formação.
     * 
     * @return Array das posições
     */
    public IPlayerPosition[] getPositions() {
        return positions;
    }

    /**
     * Obtém o seletor de jogadores utilizado por esta formação.
     * 
     * @return Seletor de jogadores
     */
    public IPlayerSelector getSelector() {
        return selector;
    }
}

