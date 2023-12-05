package tickets;

import person.Person;

import java.util.AbstractMap;
import java.util.Date;
import java.util.List;

public interface ITicket {

    Date getDate();
    String getDescription();
    double getTotal();
    List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson();
    boolean getIsPaid();
    void setIsPaid(boolean isPaid);
}
