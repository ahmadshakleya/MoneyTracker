package factory;

import controller.TicketsDBController;
import database.TicketsDB;
import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
import tickets.TicketEvenSplit;

import java.util.List;
import java.util.Set;

public class TicketFactoryEvenSplit {

    private final TicketsDBController controller;

    public TicketFactoryEvenSplit(TicketsDBController controller){
        this.controller = controller;
    }

    public void makeEvenTicket(Person whoHasPaid, double totaal, Set<Person> people, List<Tag> tags, String description){
        ITicket ticket = new TicketEvenSplit(totaal, people, description);
        ITicket taggedTicket = new TaggedTicket(ticket, tags);
        controller.addTicket(whoHasPaid, taggedTicket);
    }
}
