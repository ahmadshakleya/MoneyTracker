package database;

import person.Person;
import register_entry.RegisterEntry;
import register_entry.RegisterEntryNull;
import tickets.ITicket;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

public class PersonDB
{
    private final Set<Person> db;

    // Static variable to hold the single instance
    private static TicketsDB instance;
    protected PropertyChangeSupport support;

    // Private constructor to prevent instantiation
    private PersonDB()
    {
        support = new PropertyChangeSupport(this);
        this.db = new HashSet<>();
    }

    // Static method to get the instance
    public static PersonDB getInstance() {
        if (instance == null){
            instance = new PersonDB();
        }
        return instance;
    }

    public void addEntry(Person person, ITicket ticket)
    {
        db.get(person).add(ticket);
        ArrayList<String> newValue = new ArrayList<>();
        newValue.add(person.getFirstName());
        support.firePropertyChange("Entry: ", newValue, ticket); // Persoon en Ticket


    }

    public ArrayList<ITicket> getEntry(Person person)
    {
        return this.db.get(person);
    }

    public void addObserver(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
