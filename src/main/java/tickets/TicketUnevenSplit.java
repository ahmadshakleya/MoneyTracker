package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;

public class TicketUnevenSplit extends AbstractTicket {

    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    private final List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    public TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen, String description) {
        this.terugbetaling = terugbetalingen;
        this.description = description;

        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();

        this.total = terugbetaling.stream().mapToDouble(AbstractMap.SimpleEntry::getValue).sum();

    }


    @Override
    public List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson() {
        return terugbetaling;
    }
}
