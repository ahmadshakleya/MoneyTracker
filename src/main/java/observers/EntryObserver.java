package observers;

import register_entry.RegisterEntry;
import tickets.ITicket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class EntryObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ITicket value1 = (ITicket) evt.getNewValue();
        System.out.println(value1.getDescription() + " is added to the database");
    }
}
