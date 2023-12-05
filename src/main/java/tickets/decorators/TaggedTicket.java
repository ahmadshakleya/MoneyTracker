package tickets.decorators;

import Tag.Tag;
import tickets.ITicket;
import tickets.decorators.TicketDecorator;

import java.util.List;

public class TaggedTicket extends TicketDecorator {
    List<Tag> tags;

    public TaggedTicket(ITicket ticket, List<Tag> tags) {
        super(ticket);
        this.tags = tags;
    }

    public List<Tag> getTags(){
        return tags;
    }
}
