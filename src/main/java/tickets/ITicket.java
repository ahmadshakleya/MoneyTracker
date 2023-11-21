package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.List;

public interface ITicket {
    abstract double getTotal();
    abstract List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson();
}
