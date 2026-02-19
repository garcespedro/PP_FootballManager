
package models;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class Formation implements IFormation {

    private final String displayName;
    private final IPlayerPosition[] positions;
    private final IPlayerSelector selector;

    public Formation(String displayName, IPlayerPosition[] positions, IPlayerSelector selector) {
        if (displayName == null || positions == null || selector == null) {
            throw new IllegalArgumentException("Display name, positions, and selector must not be null.");
        }

        this.displayName = displayName;
        this.positions = positions;
        this.selector = selector;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

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

    public IPlayerPosition[] getPositions() {
        return positions;
    }

    public IPlayerSelector getSelector() {
        return selector;
    }
}
