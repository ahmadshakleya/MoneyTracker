package observers;

import person.Person;
import tag.Tag;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

public class PersonUpdaters implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        TaggedTicket ticket = (TaggedTicket) propertyChangeEvent.getNewValue();
        Person personWhoPaid = (Person) propertyChangeEvent.getOldValue();

        personWhoPaid.addExpense(ticket.getTotal());

        double teken;
        if (ticket.getTag() == Tag.TERUGBETALING){
            teken = -1.0;
        }else {
            teken = 1.0;
        }

        HashMap<Person, Double> totalPerPerson =  ticket.getTotalPerPerson();
        for (Person person : totalPerPerson.keySet()){
            person.changeAmountOwed(totalPerPerson.get(person) * teken);
        }
    }
}
