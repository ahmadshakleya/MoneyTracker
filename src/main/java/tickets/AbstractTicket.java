package tickets;

import org.json.simple.JSONObject;
import person.Person;

import java.util.Date;
import java.util.HashMap;

import person.Person;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTicket implements ITicket {

    protected Date date;
    protected double total;
    protected String description;
    protected boolean isPaid = false;

    /**
     * Lijst van terugbetalingen per persoon.
     * SimpleEntry.getKey() voor de naam te krijgen
     * SimpleEntry.getValue() voor de waarde te krijgen
     */
    protected HashMap<Person, Double> terugbetaling;


    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public boolean getIsPaid() {
        return isPaid;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public HashMap<Person, Double> getTotalPerPerson() {
        return terugbetaling;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setTotalPerPerson(HashMap<Person, Double> terugbetaling) {
        this.terugbetaling = terugbetaling;
    }

    @Override
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public JSONObject toJson(){
        JSONObject ticketJson = new JSONObject();
        ticketJson.put("date", date.toString());
        ticketJson.put("total", total);
        ticketJson.put("description", description);
        ticketJson.put("isPaid", isPaid);

        JSONObject repaymentsJson = new JSONObject();
        for (Person key : terugbetaling.keySet()){
            repaymentsJson.put(key.getName(), terugbetaling.get(key));
        }
        ticketJson.put("repayments", repaymentsJson);
        return ticketJson;
    }

    public String toOwnString(){
        String text= new String();

        text += "Date: " + date.toString() + "\n";
        text += "Total: " + total + "\n";
        text += "Description: " + description + "\n";
        text += "IsPaid: " + isPaid + "\n";
        for (Person key : terugbetaling.keySet()){
            text += key.getName() + ": " + terugbetaling.get(key) + "\n";
        }
        return text;

    }
}
