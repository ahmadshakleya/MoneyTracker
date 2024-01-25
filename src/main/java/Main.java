import controller.MoneyTrackerController;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import observers.PersonUpdaters;
import person.Person;
import tag.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public Main() {

    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.load();
        //main.run();
    }

    public void run() throws Exception {

        // mag verwijder worden. Deze code zit nu in een integration test
        MoneyTrackerController controller = new MoneyTrackerController();
        PersonUpdaters personUpdaters = new PersonUpdaters();

        controller.addTicketsDBObserver(personUpdaters);

        Person person1 = controller.makePerson("Persoon Nr 1");
        Person person2 = controller.makePerson("Persoon Nr 2");
        Person person3 = controller.makePerson("Persoon Nr 3");

        TicketFactoryMaker ticketFactoryMaker = new TicketFactoryMaker(controller);
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
        ticketFactoryUneven.makeUnevenTicket(person2, 10.0, pers2_tergebtalingen, Tag.AIRPLANE, "test");
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("terugbetaling");
        ticketFactoryUneven.makeUnevenTicket(person2, 0.0, pers2_tergebtalingen, Tag.TERUGBETALING, "test");
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);

        System.out.println("\n1");
        print(controller.getPeopleCreditorsOf(person1));

        System.out.println("\n3 ");
        print(controller.getPeopleCreditorsOf(person3));

        System.out.println("\n2 ");
        print(controller.getPeopleCreditorsOf(person2));

        Person per4 = controller.makePerson("per4");
        Person per5 = controller.makePerson("per5");

        HashMap<Person, Double> pay5 = new HashMap<>();
        HashMap<Person, Double> pay3 = new HashMap<>();

        pay5.put(person2, 40.0);
        ticketFactoryUneven.makeUnevenTicket(per5, 0.0, pay5, Tag.CONCERT, "test");

        pay3.put(per4, 20.0);
        ticketFactoryUneven.makeUnevenTicket(person3, 0.0, pay3, Tag.CONCERT, "test");


        System.out.println();
        System.out.println("\n1");
        print(controller.getPeopleCreditorsOf(person1));
        System.out.println(person1);

        System.out.println("\n2");
        print(controller.getPeopleCreditorsOf(person2));
        System.out.println(person2);
        System.out.println("\n3");
        print(controller.getPeopleCreditorsOf(person3));
        System.out.println(person3);
        System.out.println("\n4");
        print(controller.getPeopleCreditorsOf(per4));
        System.out.println(per4);
        System.out.println("\n5");
        print(controller.getPeopleCreditorsOf(per5));
        System.out.println(per5);

        controller.saveData();

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

    public void load(){
        MoneyTrackerController controller = new MoneyTrackerController();
        PersonUpdaters personUpdaters = new PersonUpdaters();

        controller.addTicketsDBObserver(personUpdaters);

        controller.loadData();
        List<Person> people = controller.getAllPeople();

        for (Person person : people){
            System.out.println(person.getName());
            System.out.println(person);
            print(controller.getPeopleCreditorsOf(person));
            System.out.println("");
        }
    }

    public void print(HashMap<Person, Double> map) {
        for (Person person : map.keySet()) {
            System.out.print(person.getName());
            System.out.print(": ");
            System.out.print(map.get(person));
            System.out.print(" ");
        }
        System.out.println();
    }

    public void sleep(int millis) {
        try {
            System.out.print("Sleeping [    ]\r");
            Thread.sleep(millis);
            System.out.println("Sleeping [ OK ]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
