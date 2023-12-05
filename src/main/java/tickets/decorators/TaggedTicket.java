package tickets.decorators;

import tag.Tag;
import tickets.ITicket;

import java.util.List;

public class TaggedTicket extends TicketDecorator {
    private final List<Tag> tags;

    public TaggedTicket(ITicket ticket, List<Tag> tags) {
        super(ticket);
        this.tags = tags;
    }

    public List<Tag> getTags(){
        return tags;
    }
}
