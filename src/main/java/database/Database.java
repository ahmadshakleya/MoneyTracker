package database;

import employee.Employee;
import register_entry.RegisterEntry;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class Database
{
    protected PropertyChangeSupport support;

    public Database()
    {
        support = new PropertyChangeSupport(this);
    }

    public abstract void addEntry(Employee e, RegisterEntry re);
    public abstract RegisterEntry getEntry(Employee e);

    public void addObserver(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
