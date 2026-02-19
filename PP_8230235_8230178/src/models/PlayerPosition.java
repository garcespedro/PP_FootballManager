
package models;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class PlayerPosition implements IPlayerPosition {

    private final String description;

    public PlayerPosition(String description) {
        if (description == null) {
            throw new IllegalArgumentException("A descrição da posição não pode ser nula");
        }
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PlayerPosition{" +
                "description='" + description + '\'' +
                '}';
    }
}
