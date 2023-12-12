package factory;

import controller.TicketsDBController;
import database.TicketsDB;
import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
import tickets.TicketUnevenSplit;

import java.util.AbstractMap;
import java.util.List;

public class TicketFactoryUnevenSplit {
    private final TicketsDBController controller;

    public TicketFactoryUnevenSplit(TicketsDBController controller){
        this.controller = controller;
    }

    public void makeUnevenTicket(Person whoHasPaid, List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen, List<Tag> tags, String description){
        ITicket ticket = new TicketUnevenSplit(terugbetalingen, description);
        ITicket taggedTicket = new TaggedTicket(ticket, tags);
        controller.addTicket(whoHasPaid, taggedTicket);
    }
}
