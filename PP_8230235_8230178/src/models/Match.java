
package models;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import java.io.IOException;
import simulator.Event;
import simulator.EventManager;
import simulator.GoalEvent;


/**
 *
 * @author garce
 */
public class Match implements IMatch {

    private final int round;
    private final IClub homeClub;
    private final IClub awayClub;

    private ITeam homeTeam;
    private ITeam awayTeam;

    private boolean played;

    private final EventManager eventManager = new EventManager();

    public Match(int round, IClub homeClub, IClub awayClub) {
        if (homeClub == null || awayClub == null) {
            throw new IllegalArgumentException("Clubes não podem ser nulos.");
        }
        if (homeClub.equals(awayClub)) {
            throw new IllegalArgumentException("Os clubes devem ser diferentes.");
        }

        this.round = round;
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.played = false;
    }

    @Override
    public IClub getHomeClub() {
        return homeClub;
    }

    @Override
    public IClub getAwayClub() {
        return awayClub;
    }

    @Override
    public boolean isPlayed() {
        return played;
    }

    @Override
    public void setPlayed() {
        this.played = true;
    }

    @Override
    public ITeam getHomeTeam() {
        if (homeTeam == null) {
            throw new IllegalStateException("Home team ainda não definido.");
        }
        return homeTeam;
    }

    @Override
    public ITeam getAwayTeam() {
        if (awayTeam == null) {
            throw new IllegalStateException("Away team ainda não definido.");
        }
        return awayTeam;
    }

    @Override
    public void setTeam(ITeam team) {
        if (team == null) {
            throw new IllegalArgumentException("Team não pode ser nulo.");
        }
        if (played) {
            throw new IllegalStateException("A partida já foi jogada.");
        }

        IClub club = team.getClub();

        if (club.equals(homeClub)) {
            if (homeTeam != null) {
                throw new IllegalStateException("Home team já definido.");
            }
            homeTeam = team;
        } else if (club.equals(awayClub)) {
            if (awayTeam != null) {
                throw new IllegalStateException("Away team já definido.");
            }
            awayTeam = team;
        } else {
            throw new IllegalStateException("O clube do time não pertence a esta partida.");
        }
    }

    @Override
    public int getTotalByEvent(Class eventClass, IClub club) {
        if (eventClass == null || club == null) {
            throw new IllegalArgumentException("Classe de evento e clube não podem ser nulos.");
        }
        int count = 0;
        IEvent[] events = eventManager.getEvents();
        for (int i = 0; i < events.length; i++) {
            IEvent event = events[i];
            if (eventClass.isInstance(event)) {
                if (event instanceof Event) {
                    Event specificEvent = (Event) event;
                    if (specificEvent.getClub().equals(club)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public boolean isValid() {
        return homeTeam != null &&
               awayTeam != null &&
               !homeTeam.getClub().equals(awayTeam.getClub()) &&
               homeTeam.getClub().equals(homeClub) &&
               awayTeam.getClub().equals(awayClub) &&
               homeTeam.getFormation() != null &&
               awayTeam.getFormation() != null;
    }

    @Override
    public ITeam getWinner() {
        if (!played) return null;

        int homeGoals = getTotalByEvent(GoalEvent.class, homeClub);
        int awayGoals = getTotalByEvent(GoalEvent.class, awayClub);

        if (homeGoals > awayGoals) {
            return homeTeam;
        } else if (awayGoals > homeGoals) {
            return awayTeam;
        } else {
            return null;
        }
    }

    @Override
    public int getRound() {
        return round;
    }

    @Override
    public void exportToJson() throws IOException {
        System.out.println("{ \"round\": " + round +
                ", \"homeClub\": \"" + homeClub.getName() + "\"" +
                ", \"awayClub\": \"" + awayClub.getName() + "\"" +
                ", \"played\": " + played + " }");
    }

    @Override
    public void addEvent(IEvent event) {
        eventManager.addEvent(event);
    }

    @Override
    public IEvent[] getEvents() {
        return eventManager.getEvents();
    }

    @Override
    public int getEventCount() {
        return eventManager.getEventCount();
    }
}