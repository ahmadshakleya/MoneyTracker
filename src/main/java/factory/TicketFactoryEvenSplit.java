package factory;

import Tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.TaggedTicket;
import tickets.TicketEvenSplit;

import java.util.List;
import java.util.Set;

public class TicketFactoryEvenSplit {

    public TicketFactoryEvenSplit(){

    }

    public ITicket makeEvenTicket(double totaal, Set<Person> people, List<Tag> tags, String description){
        ITicket ticket = new TicketEvenSplit(totaal, people, description);
        return new TaggedTicket(ticket, tags);
    }
}
