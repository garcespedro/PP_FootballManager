package simulator;

/**
 *
 * @author garce
 */
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

public class EventManager implements IEventManager {

    private static final int MAX_EVENTS = 100;
    private final IEvent[] events;
    private int eventCount;

    public EventManager() {
        this.events = new IEvent[MAX_EVENTS];
        this.eventCount = 0;
    }

    @Override
    public void addEvent(IEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("O evento não pode ser nulo.");
        }

        for (int i = 0; i < eventCount; i++) {
            if (events[i] == event) {
                throw new IllegalStateException("O evento já foi adicionado.");
            }
        }

        if (eventCount >= MAX_EVENTS) {
            throw new IllegalStateException("Limite de eventos atingido.");
        }

        events[eventCount++] = event;
    }

    @Override
    public int getEventCount() {
        return eventCount;
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] copy = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            copy[i] = events[i];
        }
        return copy;
    }
}
