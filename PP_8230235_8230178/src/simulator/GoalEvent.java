package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;

public class GoalEvent extends Event implements IGoalEvent {

    private final IPlayer scorer;
    private final IPlayer assist;

    public GoalEvent(int minute, IPlayer scorer, IClub club, IPlayer assist) {
        super(minute, "Golo marcado por " + (scorer != null ? scorer.getName() : "Desconhecido"), scorer, club);

        if (scorer == null) {
            throw new IllegalArgumentException("O jogador que marcou o golo não pode ser nulo.");
        }
        if (club == null) {
            throw new IllegalArgumentException("O clube não pode ser nulo.");
        }

        this.scorer = scorer;
        this.assist = assist;
    }

    @Override
    public IPlayer getPlayer() {
        return scorer;
    }

    public IPlayer getAssistPlayer() {
        return assist;
    }

    @Override
    public void exportToJson() throws IOException {
        String json = "{\n" +
                "  \"minute\": " + getMinute() + ",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"scorer\": \"" + scorer.getName() + "\",\n" +
                "  \"club\": \"" + (getClub() != null ? getClub().getName() : "N/A") + "\",\n" +
                "  \"assist\": \"" + (assist != null ? assist.getName() : "Nenhuma") + "\"\n" +
                "}";
        System.out.println(json);
    }
}
