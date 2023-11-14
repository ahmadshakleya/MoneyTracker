package observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class NameEntryObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ArrayList<String> value1 = (ArrayList<String>) evt.getNewValue();
        System.out.println(value1.get(0) + " " +value1.get(1));
    }
}
