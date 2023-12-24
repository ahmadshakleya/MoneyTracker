package tickets;

import org.json.simple.JSONObject;
import person.Person;

import java.util.Date;
import java.util.HashMap;

public interface ITicket {

    Date getDate();

    void setDate(Date date);

    String getDescription();

    void setDescription(String description);

    double getTotal();

    void setTotal(double total);

    HashMap<Person, Double> getTotalPerPerson();

    boolean getIsPaid();

    void setIsPaid(boolean isPaid);

    JSONObject toJson();

    void setPaid(boolean paid);

    void setTerugbetaling(HashMap<Person, Double> terugbetaling);
}
