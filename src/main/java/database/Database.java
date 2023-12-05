package database; //%TODO DELEEEEEET

import person.Person;
import register_entry.RegisterEntry;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Deprecated
public abstract class Database
{
    protected PropertyChangeSupport support;

    public Database()
    {
        support = new PropertyChangeSupport(this);
    }

    public abstract void addEntry(RegisterEntry entry);
    public abstract RegisterEntry getEntry(Person e);

    public void addObserver(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}


