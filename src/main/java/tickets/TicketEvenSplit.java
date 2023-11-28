package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TicketEvenSplit implements ITicket{

    double total;
    String description;
    boolean isPaid = false;
    private int hours;
    private int minutes;
    private int seconds;

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
        this.description = "NO DESCRIPTION";

        Calendar calendar = Calendar.getInstance();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
    }

    public TicketEvenSplit(double total, Set<Person> people, String description){
        this.total = total;
        Double terugbetalingPerPersoon = total / people.size();
        terugbetaling = people.stream().map(person -> new AbstractMap.SimpleEntry<>(person, terugbetalingPerPersoon)).collect(Collectors.toList());
        this.description = description;

        Calendar calendar = Calendar.getInstance();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
    }

    @Override
    public int getHours() {
        return this.hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int getSeconds() {
        return this.seconds;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getTotal() {
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
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
