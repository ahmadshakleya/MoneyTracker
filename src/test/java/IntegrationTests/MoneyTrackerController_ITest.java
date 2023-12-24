package IntegrationTests;

import controller.MoneyTrackerController;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import observers.PersonUpdaters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import person.Person;
import tag.Tag;

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
        controller.resetDB();
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

        HashMap<Person, Double> globelBillPerson1 = controller.getGlobelBill(person1);
        Assert.assertEquals(-6.6666, globelBillPerson1.get(person2) , 0.001);
        Assert.assertEquals(33.3333, globelBillPerson1.get(person3), 0.001);
        Assert.assertEquals(0.0, globelBillPerson1.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson1.getOrDefault(person4, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson1.getOrDefault(person5, 0.0), 0.001);


        HashMap<Person, Double> globelBillPerson2 = controller.getGlobelBill(person2);
        Assert.assertEquals(6.6666, globelBillPerson2.get(person1) , 0.001);
        Assert.assertEquals(80, globelBillPerson2.get(person3), 0.001);
        Assert.assertEquals(-40, globelBillPerson2.get(person5), 0.001);
        Assert.assertEquals(0.0, globelBillPerson2.getOrDefault(person2, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson2.getOrDefault(person4, 0.0), 0.001);


        HashMap<Person, Double> globelBillPerson3 = controller.getGlobelBill(person3);
        Assert.assertEquals(-33.3333, globelBillPerson3.get(person1) , 0.001);
        Assert.assertEquals(-80, globelBillPerson3.get(person2), 0.001);
        Assert.assertEquals(20, globelBillPerson3.get(person4), 0.001);
        Assert.assertEquals(0.0, globelBillPerson3.getOrDefault(person3, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson3.getOrDefault(person5, 0.0), 0.001);

        HashMap<Person, Double> globelBillPerson4 = controller.getGlobelBill(person4);
        Assert.assertEquals(-20, globelBillPerson4.get(person3) , 0.001);
        Assert.assertEquals(0.0, globelBillPerson4.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson4.getOrDefault(person2, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson4.getOrDefault(person5, 0.0), 0.001);

        HashMap<Person, Double> globelBillPerson5 = controller.getGlobelBill(person5);
        Assert.assertEquals(40, globelBillPerson5.get(person2) , 0.001);
        Assert.assertEquals(0.0, globelBillPerson5.getOrDefault(person1, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson5.getOrDefault(person3, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson5.getOrDefault(person4, 0.0), 0.001);
        Assert.assertEquals(0.0, globelBillPerson5.getOrDefault(person5, 0.0), 0.001);
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
}
