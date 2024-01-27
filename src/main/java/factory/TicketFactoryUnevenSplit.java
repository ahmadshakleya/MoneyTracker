package factory;

import controller.MoneyTrackerController;
import exceptions.NegativeNumberException;
import exceptions.ticketException;
import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
import tickets.TicketUnevenSplit;

import java.util.HashMap;

public class TicketFactoryUnevenSplit {
    private final MoneyTrackerController controller;

    public TicketFactoryUnevenSplit(MoneyTrackerController controller){
        this.controller = controller;
    }

    public void makeUnevenTicket(Person whoHasPaid, double payerPersonalContribution, HashMap<Person, Double> terugbetalingen, Tag tag, String description) throws Exception {
        if (tag.equals(Tag.TERUGBETALING) && payerPersonalContribution != 0.0){
            throw new ticketException("voor terugbetaling moet 'payerPersonalContribution' == 0.0");
        }

        if (payerPersonalContribution < 0.0){
            throw new NegativeNumberException("kan niet negatief betalen");
        }

        ITicket ticket = new TicketUnevenSplit(terugbetalingen, payerPersonalContribution, description);
        ITicket taggedTicket = new TaggedTicket(ticket, tag);
        controller.addTicket(whoHasPaid, taggedTicket);
    }
}
