package TestsDieWeVoorDeOpdrachtMoestenMaken;

import controller.MoneyTrackerController;
import database.PersonDB;
import database.TicketsDB;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import observers.PersonUpdaters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import person.Person;
import tag.Tag;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class MoneyTrackerController_ITest {
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private Person person5;

    private MoneyTrackerController controller;

    public MoneyTrackerController_ITest() throws Exception {

    }

    @Before
    public void initialize() throws Exception {
        controller = new MoneyTrackerController();
        resetSingleton(TicketsDB.class, "instance");
        resetSingleton(PersonDB.class, "instance");
        PersonUpdaters personUpdaters = new PersonUpdaters();
        controller.addTicketsDBObserver(personUpdaters);

        person1 = controller.makePerson("persoon 1");
        person2 = controller.makePerson("persoon 2");
        person3 = controller.makePerson("persoon 3");
        person4 = controller.makePerson("persoon 4");
        person5 = controller.makePerson("persoon 5");

        TicketFactoryMaker ticketFactoryMaker = new TicketFactoryMaker(controller);

        TicketFactoryEvenSplit ticketFactoryEven = ticketFactoryMaker.makeEvenTicketFactory();
        TicketFactoryUnevenSplit ticketFactoryUneven = ticketFactoryMaker.makeUnevenTicketFactory();

        Set<Person> pers2_pers3 = new HashSet<>();
        pers2_pers3.add(person2);
        pers2_pers3.add(person3);

        HashMap<Person, Double> pers1_20_pers3_40 = new HashMap<>();
        pers1_20_pers3_40.put(person1, 20.0);
        pers1_20_pers3_40.put(person3, 40.0);

        HashMap<Person, Double> person2_40 = new HashMap<>();
        person2_40.put(person2, 40.0);

        HashMap<Person, Double> person4_20 = new HashMap<>();
        person4_20.put(person4, 20.0);

        ticketFactoryEven.makeEvenTicket(person1, 100, pers2_pers3, Tag.AIRPLANE, "wheeeee");
        ticketFactoryUneven.makeUnevenTicket(person2, 20.0, pers1_20_pers3_40, Tag.AIRPLANE, "test");
        ticketFactoryUneven.makeUnevenTicket(person2, 0.0, pers1_20_pers3_40, Tag.TERUGBETALING, "test");
        ticketFactoryUneven.makeUnevenTicket(person5, 0.0, person2_40, Tag.CONCERT, "test");
        ticketFactoryUneven.makeUnevenTicket(person3, 0.0, person4_20, Tag.CONCERT, "test");
    }

    @Test
    public void test_getExpensesPaid() throws Exception {
        Assert.assertEquals(100.0, person1.getExpensesPaid(), 0.001);
        Assert.assertEquals(140.0, person2.getExpensesPaid(), 0.001);
        Assert.assertEquals(20.0, person3.getExpensesPaid(), 0.001);
        Assert.assertEquals(0.0, person4.getExpensesPaid(), 0.001);
        Assert.assertEquals(40.0, person5.getExpensesPaid(), 0.001);
    }

    @Test
    public void test_getGlobalBill() throws Exception {

        HashMap<Person, Double> creditorsOfPerson1 = controller.getPeopleCreditorsOf(person1);
        Assert.assertEquals(-6.6666, creditorsOfPerson1.get(person2) , 0.001);
        Assert.assertEquals(33.3333, creditorsOfPerson1.get(person3), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson1.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson1.getOrDefault(person4, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson1.getOrDefault(person5, 0.0), 0.001);


        HashMap<Person, Double> creditorsOfPerson2 = controller.getPeopleCreditorsOf(person2);
        Assert.assertEquals(6.6666, creditorsOfPerson2.get(person1) , 0.001);
        Assert.assertEquals(80, creditorsOfPerson2.get(person3), 0.001);
        Assert.assertEquals(-40, creditorsOfPerson2.get(person5), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson2.getOrDefault(person2, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson2.getOrDefault(person4, 0.0), 0.001);


        HashMap<Person, Double> creditorsOfPerson3 = controller.getPeopleCreditorsOf(person3);
        Assert.assertEquals(-33.3333, creditorsOfPerson3.get(person1) , 0.001);
        Assert.assertEquals(-80, creditorsOfPerson3.get(person2), 0.001);
        Assert.assertEquals(20, creditorsOfPerson3.get(person4), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson3.getOrDefault(person3, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson3.getOrDefault(person5, 0.0), 0.001);

        HashMap<Person, Double> creditorsOfPerson4 = controller.getPeopleCreditorsOf(person4);
        Assert.assertEquals(-20, creditorsOfPerson4.get(person3) , 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson4.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson4.getOrDefault(person2, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson4.getOrDefault(person5, 0.0), 0.001);

        HashMap<Person, Double> creditorsOfPerson5 = controller.getPeopleCreditorsOf(person5);
        Assert.assertEquals(40, creditorsOfPerson5.get(person2) , 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson5.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson5.getOrDefault(person3, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson5.getOrDefault(person4, 0.0), 0.001);
        Assert.assertEquals(0.0, creditorsOfPerson5.getOrDefault(person5, 0.0), 0.001);

        HashMap<Person, HashMap<Person, Double>> globalBill = controller.getGlobalBill();
        Assert.assertEquals(creditorsOfPerson1, globalBill.get(person1));
        Assert.assertEquals(creditorsOfPerson2, globalBill.get(person2));
        Assert.assertEquals(creditorsOfPerson3, globalBill.get(person3));
        Assert.assertEquals(creditorsOfPerson4, globalBill.get(person4));
        Assert.assertEquals(creditorsOfPerson5, globalBill.get(person5));
    }

    @Test
    public void test_json() throws Exception {
        controller.saveData();
        controller.resetDB();
        controller.loadData();

        person1 = controller.getPerson(person1.getName());
        person2 = controller.getPerson(person2.getName());
        person3 = controller.getPerson(person3.getName());
        person4 = controller.getPerson(person4.getName());
        person5 = controller.getPerson(person5.getName());

        test_getExpensesPaid();
        test_getGlobalBill();
    }

    public static void resetSingleton(Class<?> clazz, String fieldName) {
        Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
            instance.setAccessible(false);
        } catch (Exception e) {
            throw new RuntimeException("Could not reset singleton: " + e.getMessage());
        }
    }

}

