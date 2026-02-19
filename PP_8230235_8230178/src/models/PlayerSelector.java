
package models;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

public class PlayerSelector implements IPlayerSelector {

    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) {
            throw new IllegalArgumentException("Clube e posição não podem ser nulos.");
        }

        IPlayer[] players = club.getPlayers();

        if (players == null || players.length == 0) {
            throw new IllegalStateException("The team is empty.");
        }

        for (int i = 0; i < players.length; i++) {
            IPlayer player = players[i];

            if (player != null && player.getPosition() != null) {
                String pos1 = player.getPosition().getDescription();
                String pos2 = position.getDescription();

                if (pos1 != null && pos1.equalsIgnoreCase(pos2)) {
                    return player;
                }
            }
        }

        throw new IllegalStateException("Não existe o jogador de acordo com a posição indicada : " + position.getDescription());
    }
}
