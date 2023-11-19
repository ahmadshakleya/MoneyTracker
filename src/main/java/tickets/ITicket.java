package tickets;

import person.Person;
import jdk.internal.util.xml.impl.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ITicket {
    abstract double getTotal();
    abstract List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson();
}
