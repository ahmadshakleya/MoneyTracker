import TicketsDBController.TicketsDBController;
import database.TicketsDB;
import person.Person;
import factory.PersonFactory;
import observers.DatabaseObserver;
import observers.EntryObserver;
import tickets.ITicket;
import tickets.TicketEvenSplit;

import java.util.HashSet;
import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    public Main()
    {

    }

    public void run()
    {
        TicketsDB ticketsDB = TicketsDB.getInstance();
        TicketsDBController tickets_register = new TicketsDBController(ticketsDB);
        Person person1 = new Person("Ahmad", "Shakleya");
        Person person2 = new Person("Berkay", "Yildirim");

        EntryObserver printEntry = new EntryObserver();
        DatabaseObserver printUpdated = new DatabaseObserver();
        ticketsDB.addObserver(printEntry);
        ticketsDB.addObserver(printUpdated);

        sleep(3000);

        Set<Person> participants = new HashSet<>();
        participants.add(person1);
        participants.add(person2);
        ITicket ticket = new TicketEvenSplit(20, participants);

        tickets_register.addTicket(person1, ticket);
        //tickets_register.addTicket(person2, ticket);

        // Replace with your own objects
        /*Person test1 = new Person("a", "b");
        Person test2 = new Person("aaa", "bbbb");

        System.out.println(test1.getID());
        System.out.println(test2.getID());*/
        /*RegistrationController register= new RegistrationController(timedb);
        PersonFactory factory = new PersonFactory();

        ViewFrame view = new ViewFrame(register);
        view.initialize();

        // Replace with your own observers
        EntryObserver printEntry = new EntryObserver();
        DatabaseObserver printUpdated = new DatabaseObserver();
        timedb.addObserver(printEntry);
        timedb.addObserver(printUpdated);
        timedb.addObserver(view);

        // Replace with your own person creation methods
        Person e1 = factory.getPerson(1, "Alice", "Programmer");
        Person e2 = factory.getPerson(2, "Bob", "CustomerService");
        Person e3 = factory.getPerson(3, "Charlie", "Manager");

        sleep(3000);

        register.checkIn(e1);
        register.checkIn(e2);
        register.checkIn(e3);

        sleep(1000);
        register.checkOut(e1);
        sleep(1000);
        register.checkOut(e2);
        sleep(1000);
        register.checkOut(e3);*/
    }

    public void sleep(int millis)
    {
        try
        {
            System.out.print("Sleeping [    ]\r");
            Thread.sleep(millis);
            System.out.println("Sleeping [ OK ]");
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
