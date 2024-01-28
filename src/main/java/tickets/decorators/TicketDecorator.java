package tickets.decorators;

import org.json.simple.JSONObject;
import person.Person;
import tickets.ITicket;

import java.util.Date;
import java.util.HashMap;

public abstract class TicketDecorator implements ITicket {
    protected ITicket decoratedTicket;

    public TicketDecorator(ITicket ticket) {
        this.decoratedTicket = ticket;
    }

    @Override
    public String getDescription(){
        return decoratedTicket.getDescription();
    }

    @Override
    public Date getDate() {return decoratedTicket.getDate();};

    @Override
    public double getTotal() {
        return decoratedTicket.getTotal();
    }

    @Override
    public HashMap<Person, Double> getTotalPerPerson() {
        return decoratedTicket.getTotalPerPerson();
    }

    @Override
    public boolean getIsPaid() {
        return decoratedTicket.getIsPaid();
    }

    @Override
    public void setIsPaid(boolean isPaid) {
        decoratedTicket.setIsPaid(isPaid);
    }

    @Override
    public JSONObject toJson() {
        return decoratedTicket.toJson();
    }

    @Override
    public String toOwnString() {
        return decoratedTicket.toOwnString();
    }

    @Override
    public void setDate(Date date) {decoratedTicket.setDate(date);}

    @Override
    public void setDescription(String description) {decoratedTicket.setDescription(description);}

    @Override
    public void setTotal(double total) {decoratedTicket.setTotal(total);}

    @Override
    public void setTotalPerPerson(HashMap<Person, Double> terugbetaling) {decoratedTicket.setTotalPerPerson(terugbetaling);}
}
