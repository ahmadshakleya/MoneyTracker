package tickets;

import person.Person;

import java.util.AbstractMap;
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
    public int getHours() {
        return decoratedTicket.getHours();
    }

    @Override
    public int getMinutes() {
        return decoratedTicket.getMinutes();
    }

    @Override
    public int getSeconds() {
        return decoratedTicket.getSeconds();
    }

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
