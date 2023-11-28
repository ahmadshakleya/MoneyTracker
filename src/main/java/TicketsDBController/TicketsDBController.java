package TicketsDBController;


import database.TicketsDB;
import person.Person;
import tickets.ITicket;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class TicketsDBController {
    private final TicketsDB ticketsDB;

    public TicketsDBController() {
        this.ticketsDB = TicketsDB.getInstance();
    }

    public void addTicket(Person person, ITicket ticket) {
        ticketsDB.addEntry(person, ticket);
    }

    public ArrayList<ITicket> getTicketsForPerson(Person person) {
        return ticketsDB.getEntry(person);
    }

    public void addObserver(PropertyChangeListener listener) {
        ticketsDB.addObserver(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        ticketsDB.removeObserver(listener);
    }

}
