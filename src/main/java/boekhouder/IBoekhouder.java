package boekhouder;

import person.Person;
import tickets.ITicket;

public interface IBoekhouder {
    void addTicket(Person e, ITicket ticket);
    //void removeTicket(Person e, ITicket ticket);
}
