package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketUnevenSplit implements ITicket{

    String description;
    boolean isPaid = false;

    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen){
        this.terugbetaling = terugbetalingen;
    }

    public TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen, String description){
        this.terugbetaling = terugbetalingen;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public double getTotal() {
        double total = 0;

        for (AbstractMap.SimpleEntry<Person, Double> people: terugbetaling){
            total += people.getValue();
        }

        return total;
    }

    @Override
    public List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson() {
        return terugbetaling;
    }

    @Override
    public boolean getIsPaid() {
        return isPaid;
    }

    @Override
    public boolean setIsPaid() {
        return isPaid;
    }
}
