package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TicketEvenSplit implements ITicket{

    double total;

    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    public TicketEvenSplit(double total, Set<Person> people){
        this.total = total;
        Double terugbetalingPerPersoon = total / people.size();
        terugbetaling = people.stream().map(person -> new AbstractMap.SimpleEntry<>(person, terugbetalingPerPersoon)).collect(Collectors.toList());
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson() {
        return terugbetaling;
    }
}
