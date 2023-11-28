package boekhouder;

import database.Database;
import database.TicketsDB;
import person.Person;
import register_entry.RegisterEntry;
import tickets.ITicket;

public class Boekhouder implements IBoekhouder{
    private TicketsDB db_tickets;

    public Boekhouder() {
        this.db_tickets = TicketsDB.getInstance();
    }

    @Override
    public void addTicket(Person e, ITicket ticket) {
        db_tickets.addEntry(e, ticket);
    }


    /*


    public void removeTicket(Person e, ITicket ticket) {
        if (ticket.getIsPaid()) {
            db_tickets.removeEntry(e, ticket);
        }
    }
    */

}
