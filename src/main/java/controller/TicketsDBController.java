package controller;


import database.TicketsDB;
import person.Person;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TicketsDBController {
    private final TicketsDB ticketsDB;

    public TicketsDBController(TicketsDB ticketsDB) {
        this.ticketsDB = ticketsDB;
    }

    public void addTicket(Person person, ITicket ticket) {
        ticketsDB.addEntry(person, ticket);
    }

    public ArrayList<ITicket> getTicketsForPerson(Person person) {
        return ticketsDB.getEntry(person);
    }

    /**
     * Haalt een lijst op van mensen aan wie de opgegeven persoon geld verschuldigd is.
     *
     * @param person De persoon voor wie we gaan zoeken wie hij gaat terug betalen
     * @return Lijst van mensen aan wie de persoon geld verschuldigd is.
     */
    public HashMap<Person, Double> getGlobelBill(Person person) {
        return ticketsDB.getPeopleCreditorsOf(person);

    }

    public void addObserver(PropertyChangeListener listener) {
        ticketsDB.addObserver(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        ticketsDB.removeObserver(listener);
    }

}
