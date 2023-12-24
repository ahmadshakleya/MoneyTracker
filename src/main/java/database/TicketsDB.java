package database;

import org.json.JSONArray;
import person.Person;
import tag.Tag;
import tickets.ITicket;
import tickets.decorators.TaggedTicket;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class TicketsDB {
    // Static variable to hold the single instance
    private static TicketsDB instance;
    private HashMap<Person, ArrayList<ITicket>> db;
    protected PropertyChangeSupport support;

    // Private constructor to prevent instantiation
    private TicketsDB() {
        support = new PropertyChangeSupport(this);
        this.db = new HashMap<>();
    }

    // Static method to get the instance
    public static TicketsDB getInstance() {
        if (instance == null) {
            instance = new TicketsDB();
        }
        return instance;
    }

    public void addEntry(Person person, ITicket ticket) {
        ArrayList<ITicket> ticketsForPerson = db.computeIfAbsent(person, k -> new ArrayList<>());
        ticketsForPerson.add(ticket);

        //Set<Person> peopleWhoAreEffected = ticket.getTotalPerPerson().stream().map(AbstractMap.SimpleEntry::getKey).collect(Collectors.toSet());
        //peopleWhoAreEffected.add(person);
        support.firePropertyChange("Entry: ", person, ticket);
    }

    public ArrayList<ITicket> getEntry(Person person) {
        return this.db.get(person);
    }

    public void removeTicket(Person person, ITicket ticket) {
        this.db.get(person).remove(ticket);
    }

    public void addObserver(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void reset(){
        support = new PropertyChangeSupport(this);
        this.db = new HashMap<>();
    }

    //mensen aan wie de persoon geld verschuldigd is
    public HashMap<Person, Double> getPeopleCreditorsOf(Person person) {
        HashMap<Person, Double> peopleCreditorsOfPerson = new HashMap<>();

        //basis
        for (Map.Entry<Person, ArrayList<ITicket>> entry : this.db.entrySet()) {
            for (ITicket ticket : entry.getValue()) {
                TaggedTicket taggedTicket = (TaggedTicket) ticket;

                HashMap<Person, Double> totalPerPerson = ticket.getTotalPerPerson();
                if (totalPerPerson.containsKey(person)) {
                    peopleCreditorsOfPerson.put(entry.getKey(), peopleCreditorsOfPerson.getOrDefault(entry.getKey(), 0.0) - totalPerPerson.get(person));
                }
            }
        }

        //afterekken van hun schulden tegen over de persoon
        ArrayList<ITicket> tickets = this.db.get(person);
        if (tickets != null) {
            for (ITicket ticket : tickets) {
                if (ticket.getIsPaid()) {
                    continue;
                }
                HashMap<Person, Double> totalPerPerson = ticket.getTotalPerPerson();

                for (Person creditor : totalPerPerson.keySet()) {
                    peopleCreditorsOfPerson.put(creditor, peopleCreditorsOfPerson.getOrDefault(creditor, 0.0) + totalPerPerson.get(creditor));

                }
            }
        }
        return peopleCreditorsOfPerson;
    }
}
