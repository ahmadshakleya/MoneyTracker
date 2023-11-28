package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.List;

public interface ITicket {

    abstract String getDescription();
    abstract double getTotal();
    abstract List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson();
    abstract boolean getIsPaid();
    abstract void setIsPaid(boolean isPaid);
}
