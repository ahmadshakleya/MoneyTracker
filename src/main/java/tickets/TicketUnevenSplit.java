package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketUnevenSplit implements ITicket{

    private String description;
    private boolean isPaid = false;
    private final int hours;
    private final int minutes;
    private final int seconds;
    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    List<AbstractMap.SimpleEntry<Person, Double>> terugbetaling;

    public TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen){
        this.terugbetaling = terugbetalingen;

        Calendar calendar = Calendar.getInstance();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
    }

    public TicketUnevenSplit(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen, String description){
        this.terugbetaling = terugbetalingen;
        this.description = description;

        Calendar calendar = Calendar.getInstance();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
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
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
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
}
