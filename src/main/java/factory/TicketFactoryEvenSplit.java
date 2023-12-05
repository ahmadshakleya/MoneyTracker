package factory;

import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
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
