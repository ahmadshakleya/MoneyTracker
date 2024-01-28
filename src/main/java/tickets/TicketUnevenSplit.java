package tickets;

import org.json.simple.JSONObject;
import person.Person;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TicketUnevenSplit extends AbstractTicket {

    public TicketUnevenSplit(Person whoHasPaid, HashMap<Person, Double> terugbetalingen, double payerPersonalContribution, String description) {
        this.terugbetaling = terugbetalingen;
        this.description = description;
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime();
        this.total = terugbetalingen.values().stream().mapToDouble(Double::doubleValue).sum() + payerPersonalContribution;
        this.person = whoHasPaid;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("type:", "uneven");
        return jsonObject;
    }

    @Override
    public String toOwnString() {
        String text = super.toOwnString();
        text += "type: " + "uneven\n";
        return text;
    }
}
