package factory;

import tag.Tag;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;
import tickets.TicketUnevenSplit;

import java.util.AbstractMap;
import java.util.List;

public class TicketFactoryUnevenSplit {

    public TicketFactoryUnevenSplit() {
    }

    public ITicket makeUnevenTicket(List<AbstractMap.SimpleEntry<Person, Double>> terugbetalingen, List<Tag> tags, String description){
        ITicket ticket = new TicketUnevenSplit(terugbetalingen, description);
        return new TaggedTicket(ticket, tags);
    }
}
