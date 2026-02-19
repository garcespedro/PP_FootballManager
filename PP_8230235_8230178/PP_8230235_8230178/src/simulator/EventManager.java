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
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

/**
 * Gerencia um conjunto de eventos relacionados a uma partida ou temporada.
 * Implementa a interface {@link IEventManager}.
 * Limita o número máximo de eventos a 100.
 */
public class EventManager implements IEventManager {

    /** Número máximo de eventos que podem ser armazenados */
    private static final int MAX_EVENTS = 100;

    /** Array interno para armazenar os eventos */
    private final IEvent[] events;

    /** Quantidade atual de eventos armazenados */
    private int eventCount;

    /**
     * Construtor que inicializa o gerenciador de eventos.
     * O array interno é criado com capacidade para {@code MAX_EVENTS}.
     */
    public EventManager() {
        this.events = new IEvent[MAX_EVENTS];
        this.eventCount = 0;
    }

    /**
     * Adiciona um novo evento ao gerenciador.
     *
     * @param event o evento a ser adicionado; não pode ser nulo
     * @throws IllegalArgumentException se o evento for nulo
     * @throws IllegalStateException se o evento já foi adicionado ou se o limite máximo foi atingido
     */
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

    /**
     * Retorna o número total de eventos atualmente armazenados.
     *
     * @return quantidade de eventos armazenados
     */
    @Override
    public int getEventCount() {
        return eventCount;
    }

    /**
     * Retorna uma cópia do array contendo todos os eventos armazenados.
     *
     * @return array contendo os eventos armazenados
     */
    @Override
    public IEvent[] getEvents() {
        IEvent[] copy = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            copy[i] = events[i];
        }
        return copy;
    }
    
    public int contEvents (IEvent event){
        int count = 0;
        
        for (int i = 0; i < events.length; i++){
            if (event instanceof FoulEvent){
                count++;
            }
            
            if (event instanceof CardEvent){
                count++;
            }
            
            if (event instanceof GoalEvent){
                count++;
            }
            
            if (event instanceof InjuryEvent){
                count++;
            }
            
            if (event instanceof SubstitutionEvent){
                count++;
            }
            
        }
        
        return count;
    }
}
