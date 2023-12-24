package controller;

import database.PersonDB;
import database.TicketsDB;
import exceptions.PersonAlreadyExists;
import factory.PersonFactory;
import person.Person;
import tickets.ITicket;

import java.beans.PropertyChangeListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.FileWriter;
import java.io.IOException;

public class MoneyTrackerController {
    private final PersonDB personDB;
    private final TicketsDB ticketsDB;

    public MoneyTrackerController() {
        this.personDB = PersonDB.getInstance();
        this.ticketsDB = TicketsDB.getInstance();
    }

    public Person makePerson(String name) throws Exception {
        if (personDB.getEntry(name) != null) {
            throw new PersonAlreadyExists("they already exist");
        }

        Person person = PersonFactory.makePerson(name);
        personDB.addEntry(person);
        return person;
    }

    public void addTicket(Person person, ITicket ticket) {
        ticketsDB.addEntry(person, ticket);
    }

    public ArrayList<ITicket> getTicketsForPerson(Person person) {
        return ticketsDB.getEntry(person);
    }

    public Person getPerson(String name) {
        return personDB.getEntry(name);
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

    public void addPersonDBObserver(PropertyChangeListener listener) {
        personDB.addObserver(listener);
    }

    public void removePersonDBObserver(PropertyChangeListener listener) {
        personDB.removeObserver(listener);
    }

    public void addTicketsDBObserver(PropertyChangeListener listener) {
        ticketsDB.addObserver(listener);
    }

    public void removeTicketsDBObserver(PropertyChangeListener listener) {
        ticketsDB.removeObserver(listener);
    }

    public void resetDB(){
        personDB.reset();
        ticketsDB.reset();
    }
}
