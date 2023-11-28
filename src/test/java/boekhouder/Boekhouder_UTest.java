package boekhouder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import person.Person;
import tickets.ITicket;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class Boekhouder_UTest {
    public Boekhouder_UTest() {

    }

    @Before
    public void setUp() {

    }
    /*
    @Test
    public void testAddParticipant() {
        Boekhouder boekhouder = new Boekhouder("Vakantie");
        Person person = Mockito.mock(Person.class);
        boekhouder.addParticipant(person);
        Assert.assertEquals(1, boekhouder.getParticipants().size());
    }

    @Test
    public void testRemoveParticipant() {
        Boekhouder boekhouder = new Boekhouder("Vakantie");
        Person person_mock = Mockito.mock(Person.class);
        boekhouder.addParticipant(person_mock);

        // Get the Person object added to the boekhouder
        Person person = boekhouder.getParticipants().get(0);

        // Remove the participant
        boekhouder.removeParticipant(person);

        Assert.assertEquals(0, boekhouder.getParticipants().size());
    }

    @Test
    public void testAddExpense() {
        Boekhouder boekhouder = new Boekhouder("Vakantie");
        Person person = Mockito.mock(Person.class);
        boekhouder.addParticipant(person);
        boekhouder.addExpense(person, 50.0);
        Mockito.verify(person).addExpense(50.0);
        Assert.assertEquals(50.0, boekhouder.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateTotalExpenses() {
        Boekhouder boekhouder = new Boekhouder("Vakantie");
        Person person1 = Mockito.mock(Person.class);
        boekhouder.addParticipant(person1);
        Person person2 = Mockito.mock(Person.class);
        boekhouder.addParticipant(person2);
        boekhouder.addExpense(boekhouder.getParticipants().get(0), 50.0);
        boekhouder.addExpense(boekhouder.getParticipants().get(1), 75.0);

        Assert.assertEquals(125.0, boekhouder.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateOwedAmounts() {
        Boekhouder boekhouder = new Boekhouder("Test Boekhouder");

        // Mock the ITicket interface
        ITicket ticket = Mockito.mock(ITicket.class);

        // Create test data for the ticket
        List<AbstractMap.SimpleEntry<Person, Double>> expenses = new ArrayList<>();
        Person person1 = Mockito.mock(Person.class);
        Person person2 = Mockito.mock(Person.class);
        expenses.add(new AbstractMap.SimpleEntry<>(person1, 100.0));
        expenses.add(new AbstractMap.SimpleEntry<>(person2, 150.0));
        person1.addExpense(expenses.get(0).getValue());
        person2.addExpense(expenses.get(1).getValue());

        // Stub the methods needed for the test
        Mockito.when(ticket.getTotal()).thenReturn(250.0);
        Mockito.when(ticket.getTotalPerPerson()).thenReturn(expenses);
        Mockito.when(person1.getExpensesPaid()).thenReturn(100.0);
        Mockito.when(person2.getExpensesPaid()).thenReturn(150.0);
        // Create a Boekhouder instance and call calculateOwedAmounts with the mocked ticket
        boekhouder.addParticipant(person1);
        boekhouder.addParticipant(person2);
        boekhouder.calculateOwedAmounts(ticket);

        System.out.println(person1.getAmountOwed());
        /*
        // Assert the expected amount owed for participants
        Assert.assertEquals(-100.0, boekhouder.getParticipants().get(0).getAmountOwed(), 0.001);
        Assert.assertEquals(0.0, boekhouder.getParticipants().get(1).getAmountOwed(), 0.001);
    }

     */

}
