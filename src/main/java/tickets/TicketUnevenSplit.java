package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.List;

public class TicketUnevenSplit implements ITicket{
    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen){
        this.terugbetaling = terugbetalingen;
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
}
