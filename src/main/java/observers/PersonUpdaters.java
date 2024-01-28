package observers;

import person.Person;
import tag.Tag;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonUpdaters implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

        if (propertyChangeEvent.getPropertyName().equals("ticketAdded")) {
            ArrayList<Object> list = (ArrayList<Object>) propertyChangeEvent.getNewValue();


            TaggedTicket ticket = (TaggedTicket) list.get(1);
            Person personWhoPaid = (Person) list.get(0);

            personWhoPaid.addExpense(ticket.getTotal());
        }
    }
}
