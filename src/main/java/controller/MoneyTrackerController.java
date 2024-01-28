package controller;

import database.PersonDB;
import database.TicketsDB;
import exceptions.PersonAlreadyExists;
import factory.PersonFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import person.Person;
import tag.Tag;
import tickets.ITicket;
import tickets.TicketEvenSplit;
import tickets.TicketUnevenSplit;
import tickets.decorators.TaggedTicket;

import java.beans.PropertyChangeListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    public ArrayList<Person> getAllPeople(){
        return personDB.getAllPeople();
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
    public HashMap<Person, Double> getPeopleCreditorsOf(Person person) {
        return ticketsDB.getPeopleCreditorsOf(person);
    }

    public HashMap<Person, HashMap<Person, Double>> getGlobalBill() {
        HashMap<Person, HashMap<Person, Double>> globalBill = new HashMap<>();
        for (Person i : personDB.getAllPeople()){
            globalBill.put(i, getPeopleCreditorsOf(i));
        }
        return globalBill;
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

    public void resetDB() {
        personDB.reset();
        ticketsDB.reset();
    }

    public void saveData() {
        try {
            JSONObject finalObject = new JSONObject();

            JSONObject ticketDBJson = ticketsDB.toJson();
            JSONArray personDBJson = personDB.toJson();

            finalObject.put("ticketDB", ticketDBJson);
            finalObject.put("personDB", personDBJson);

            FileWriter file = new FileWriter("data.json");
            file.write(finalObject.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        resetDB();

        try {
            JSONObject jsonData = (JSONObject) new JSONParser().parse(new String(Files.readAllBytes(Paths.get("data.json"))));

            JSONArray personDBJson = (JSONArray) jsonData.get("personDB");
            JSONObject ticketDBJson = (JSONObject) jsonData.get("ticketDB");

            List<Person> ticketsPersons = new ArrayList<>();

            // PersoonDB
            for (Object obj: personDBJson){
                JSONObject jsonObject = (JSONObject) obj;
                Person loadedPerson = makePerson((String) jsonObject.get("name"));
                loadedPerson.setExpensesPaid((double) jsonObject.get("expensesPaid"));
                ticketsPersons.add(loadedPerson);
            }

            // TicketDB
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            for (Person person: ticketsPersons){
                JSONArray ticketArray = (JSONArray) ticketDBJson.get(person.getName());

                if (ticketArray == null){
                    continue;
                }

                for (Object obj: ticketArray){
                    JSONObject ticketData = (JSONObject) obj;
                    ITicket ticket;
                    if (ticketData.get("type:").equals("uneven")){
                        HashMap<Person, Double> ticketMap = new HashMap<>();
                        JSONObject terugbtalingen = (JSONObject) ticketData.get("repayments");
                        for (Object nameObj: terugbtalingen.keySet()){
                            String name = (String) nameObj;
                            ticketMap.put(personDB.getEntry(name), (double) terugbtalingen.get(nameObj));
                        }

                        TicketUnevenSplit ticketUnevenSplit = new TicketUnevenSplit(person,ticketMap, 0.0, (String) ticketData.get("description"));
                        ticket = ticketUnevenSplit;
                    } else {
                        HashSet<Person> ticketMap = new HashSet<>();
                        JSONObject terugbtalingen = (JSONObject) ticketData.get("repayments");
                        for (Object nameObj: terugbtalingen.keySet()){
                            String name = (String) nameObj;
                            ticketMap.add(personDB.getEntry(name));
                        }

                        TicketEvenSplit ticketEvenSplit = new TicketEvenSplit(person, (double) ticketData.get("total"), ticketMap,  (String) ticketData.get("description"));
                        ticket = ticketEvenSplit;
                    }



                    ticket.setDate(formatter.parse((String) ticketData.get("date")));
                    ticket.setIsPaid((boolean) ticketData.get("isPaid"));
                    ticket.setTotal((double) ticketData.get("total"));

                    JSONArray decorators = (JSONArray) ticketData.get("Decorator");

                    TaggedTicket taggedTicket;
                    for (Object decObj: decorators){
                        Tag tag = Tag.valueOf((String) decObj);
                        taggedTicket = new TaggedTicket(ticket, tag);
                        ticket = taggedTicket;
                    }

                    ticketsDB.loadEntry(person, ticket);
                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public PersonDB getPersonDB() {
        return personDB;
    }

    public TicketsDB getTicketsDB() {
        return ticketsDB;
    }
}
