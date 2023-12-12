package tickets;

import person.Person;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketEvenSplit extends AbstractTicket {
    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    private final HashMap<Person, Double> terugbetaling;

    public TicketEvenSplit(double total, Set<Person> people, String description) {
        this.total = total;
        double terugbetalingPerPersoon = total / (people.size() + 1);
        //terugbetalingPerPersoon = (double) Math.round(terugbetalingPerPersoon*100)/100;
        terugbetaling = (HashMap<Person, Double>) people.stream().collect(Collectors.toMap(person -> person, person -> terugbetalingPerPersoon));
        this.description = description;
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
    }

    @Override
    public HashMap<Person, Double> getTotalPerPerson() {
        return terugbetaling;
    }
}
