package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.List;

public interface ITicket {

    abstract int getHours();
    abstract int getMinutes();
    abstract int getSeconds();

    abstract String getDescription();
    abstract double getTotal();
    abstract List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson();
    abstract boolean getIsPaid();
    abstract void setIsPaid(boolean isPaid);
}
