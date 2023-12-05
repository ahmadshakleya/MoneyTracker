package tickets.decorators;

import person.Person;
import tickets.ITicket;

import java.util.AbstractMap;
import java.util.Date;
import java.util.List;

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
    public Date getDate();

    @Override
    public double getTotal() {
        return decoratedTicket.getTotal();
    }

    @Override
    public List<AbstractMap.SimpleEntry<Person, Double>> getTotalPerPerson() {
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
}
