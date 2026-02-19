
package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;

public class Event implements IEvent {

    private final int minute;
    private final String description;
    private final IPlayer player;
    private final IClub club;

    public Event(int minute, String description, IPlayer player, IClub club) {
        if (minute < 0 || minute > 120) {
            throw new IllegalArgumentException("O minuto do evento deve estar entre 0 e 120.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do evento não pode ser nula ou vazia.");
        }

        this.minute = minute;
        this.description = description;
        this.player = player;
        this.club = club;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public IClub getClub() {
        return club;
    }

    @Override
    public void exportToJson() throws IOException {
        String json = "{\n" +
                "  \"minute\": " + minute + ",\n" +
                "  \"description\": \"" + description + "\",\n" +
                "  \"player\": \"" + (player != null ? player.getName() : "N/A") + "\",\n" +
                "  \"club\": \"" + (club != null ? club.getName() : "N/A") + "\"\n" +
                "}";
        System.out.println(json);
    }
}
