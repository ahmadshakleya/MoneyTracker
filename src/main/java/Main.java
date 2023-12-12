import controller.TicketsDBController;
import database.TicketsDB;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import observers.PersonUpdaters;
import person.Person;
import factory.PersonFactory;
import tag.Tag;

import java.util.*;

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
        TicketsDBController tickets_controller = new TicketsDBController(ticketsDB);
        PersonUpdaters personUpdaters = new PersonUpdaters();

        tickets_controller.addObserver(personUpdaters);

        Person person1 = PersonFactory.makePerson("Persoon Nr 1");
        Person person2 = PersonFactory.makePerson("Persoon Nr 2");
        Person person3 = PersonFactory.makePerson("Persoon Nr 3");

        TicketFactoryMaker ticketFactoryMaker = new TicketFactoryMaker(tickets_controller);
        TicketFactoryEvenSplit ticketFactoryEven = ticketFactoryMaker.makeEvenTicketFactory();

        Set<Person> people_who_paid_nr1 = new HashSet<>();
        people_who_paid_nr1.add(person2);
        people_who_paid_nr1.add(person3);

        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("Ticket gemaakt");
        ticketFactoryEven.makeEvenTicket(person1, 100, people_who_paid_nr1, Tag.AIRPLANE, "wheeeee");

        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        TicketFactoryUnevenSplit ticketFactoryUneven = ticketFactoryMaker.makeUnevenTicketFactory();

        HashMap<Person, Double> pers2_tergebtalingen = new HashMap<>();
        pers2_tergebtalingen.put(person1, 20.0);
        pers2_tergebtalingen.put(person3, 40.0);

        System.out.println("uneven");
        ticketFactoryUneven.makeUnevenTicket(person2, pers2_tergebtalingen, Tag.AIRPLANE, "test");
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("terugbetaling");
        ticketFactoryUneven.makeUnevenTicket(person2, pers2_tergebtalingen, Tag.TERUGBETALING, "test");
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("\n1");
        print(tickets_controller.getGlobelBill(person1));

        System.out.println("\n3 ");
        print(tickets_controller.getGlobelBill(person3));

        System.out.println("\n2 ");
        print(tickets_controller.getGlobelBill(person2));



        /*
        ViewFrame view = new ViewFrame(tickets_register);
        view.initialize();

        EntryObserver printEntry = new EntryObserver();
        DatabaseObserver printUpdated = new DatabaseObserver();
        ticketsDB.addObserver(printEntry);
        ticketsDB.addObserver(printUpdated);
        ticketsDB.addObserver(view);

        sleep(3000);

        Set<Person> participants = new HashSet<>();
        participants.add(person1);
        participants.add(person2);
        ITicket ticket = new TicketEvenSplit(20, participants, "New Ticket");

        tickets_register.addTicket(person1, ticket);*/



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

    public void print(HashMap<Person, Double> map){
        for (Person person : map.keySet()){
            System.out.print(person.getName());
            System.out.print(": ");
            System.out.print(map.get(person));
        }
        System.out.println();
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
