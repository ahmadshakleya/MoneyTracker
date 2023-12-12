package observers;

import person.Person;
import tickets.ITicket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractMap;
import java.util.List;
import java.util.Set;

public class PersonUpdaters implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        ITicket ticket = (ITicket) propertyChangeEvent.getNewValue();
        Person personWhoPaid = (Person) propertyChangeEvent.getOldValue();

        personWhoPaid.addExpense(ticket.getTotal());

        List<AbstractMap.SimpleEntry<Person, Double>> totalPerPerson =  ticket.getTotalPerPerson();
        for (AbstractMap.SimpleEntry<Person, Double> personDouble : totalPerPerson){
            personDouble.getKey().addAmountOwed(personDouble.getValue());
        }
    }
}
