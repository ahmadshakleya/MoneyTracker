package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TicketUnevenSplit extends AbstractTicket {

    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    private final HashMap<Person, Double> terugbetaling;

    public TicketUnevenSplit(HashMap<Person, Double> terugbetalingen, double payerPersonalContribution, String description) {
        this.terugbetaling = terugbetalingen;
        this.description = description;
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
        this.total = terugbetalingen.values().stream().mapToDouble(Double::doubleValue).sum() + payerPersonalContribution;

    }


    @Override
    public HashMap<Person, Double> getTotalPerPerson() {
        return terugbetaling;
    }
}
