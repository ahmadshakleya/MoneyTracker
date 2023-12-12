package tickets.decorators;

import tag.Tag;
import tickets.ITicket;

public class TaggedTicket extends TicketDecorator {
    private final Tag tag;

    public TaggedTicket(ITicket ticket, Tag tag) {
        super(ticket);
        this.tag = tag;
    }

    public Tag getTag(){
        return tag;
    }
}
