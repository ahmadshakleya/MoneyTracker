package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketEvenSplit extends AbstractTicket {
    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    private final List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    public TicketEvenSplit(double total, Set<Person> people, String description) {
        this.total = total;
        Double terugbetalingPerPersoon = total / (people.size() + 1);
        terugbetaling = people.stream().map(person -> new AbstractMap.SimpleEntry<>(person, terugbetalingPerPersoon)).collect(Collectors.toList());
        this.description = description;

        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
    }

    @Override
    public List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson() {
        return terugbetaling;
    }
}
