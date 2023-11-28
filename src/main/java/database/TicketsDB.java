package database;

import person.Person;
import register_entry.RegisterEntry;
import register_entry.RegisterEntryNull;
import tickets.ITicket;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TicketsDB extends Database<ITicket>
{
    private final HashMap<Person, ArrayList<ITicket>> db;

    // Static variable to hold the single instance
    private static TicketsDB instance;

    // Private constructor to prevent instantiation
    private TicketsDB()
    {
        this.db = new HashMap<>();
    }

    @Override
    public void addEntry(ITicket... entry) {
        if (entry.length == 2 && entry[0] instanceof Person && entry[1] instanceof ITicket){
            db.get(entry[0]).add(entry[1]);
        } else {
            throw new IllegalArgumentException("Invalid arguments for TicketDB addEntry");
        }
    }

    // Static method to get the instance
    public static TicketsDB getInstance() {
        if (instance == null){
            instance = new TicketsDB();
        }
        return instance;
    }

    @Override
    public void addEntry(Person e, ITicket ticket)
    {
        this.db.put(e, ticket);
        ArrayList<String> newValue = new ArrayList<>();
        newValue.add(e.getFirstName()); // %TODO did was "newValue.add(e.getName());", tijdlijk gefixed zodat ik kon verder werken
        newValue.add(re.toString());
        support.firePropertyChange("Entry: ", null, newValue); // newValue is a list: the first index
                                                                                    // contains the name of the person
                                                                                    // and the second index contains the entry.
    }

    @Override
    public RegisterEntry getEntry(Person e)
    {
        return this.db.getOrDefault(e, new RegisterEntryNull());
    }
}
