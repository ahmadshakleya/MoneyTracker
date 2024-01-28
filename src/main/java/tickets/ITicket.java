package tickets;

import org.json.simple.JSONObject;
import person.Person;

import java.util.Date;
import java.util.HashMap;

public interface ITicket {

    Person getPerson();
    void setPerson(Person person);
    Date getDate();

    void setDate(Date date);

    String getDescription();

    void setDescription(String description);

    double getTotal();

    void setTotal(double total);

    HashMap<Person, Double> getTotalPerPerson();

    void setTotalPerPerson(HashMap<Person, Double> terugbetaling);

    boolean getIsPaid();

    void setIsPaid(boolean isPaid);

    JSONObject toJson();

    String toOwnString();
}
