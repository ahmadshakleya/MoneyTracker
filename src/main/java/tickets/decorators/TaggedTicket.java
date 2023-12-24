package tickets.decorators;

import tag.Tag;
import tickets.ITicket;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class TaggedTicket extends TicketDecorator {
    private final Tag tag;

    public TaggedTicket(ITicket ticket, Tag tag) {
        super(ticket);
        this.tag = tag;
    }

    public Tag getTag(){
        return tag;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        JSONArray jsonArray;

        if (json.containsKey("Decorator")) {
            jsonArray = (JSONArray) json.get("Decorator");
        } else {
            jsonArray = new JSONArray();
        }

        jsonArray.add(this.tag.toString());
        json.put("Decorator", jsonArray);

        return json;
    }
}
