package factory;

import controller.MoneyTrackerController;
import exceptions.ticketException;
import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
import tickets.TicketEvenSplit;

import java.util.Set;

public class TicketFactoryEvenSplit {

    private final MoneyTrackerController controller;

    public TicketFactoryEvenSplit(MoneyTrackerController controller){
        this.controller = controller;
    }

    public void makeEvenTicket(Person whoHasPaid, double totaal, Set<Person> people, Tag tag, String description) throws Exception{
        if (tag.equals(Tag.TERUGBETALING)){
            throw new ticketException("Gebruik uneven tickets. Anders ga je ook van 'whoHasPaid' aftrekken");
        }

        ITicket ticket = new TicketEvenSplit(whoHasPaid, totaal, people, description);
        ITicket taggedTicket = new TaggedTicket(ticket, tag);
        controller.addTicket(whoHasPaid, taggedTicket);
    }
}
