package tickets;

import org.json.simple.JSONObject;
import person.Person;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketEvenSplit extends AbstractTicket {


    public TicketEvenSplit(double total, Set<Person> people, String description) {
        this.total = total;
        double terugbetalingPerPersoon = total / (people.size() + 1);
        //terugbetalingPerPersoon = (double) Math.round(terugbetalingPerPersoon*100)/100;
        terugbetaling = (HashMap<Person, Double>) people.stream().collect(Collectors.toMap(person -> person, person -> terugbetalingPerPersoon));
        this.description = description;
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("type:", "even");
        return jsonObject;
    }
}
