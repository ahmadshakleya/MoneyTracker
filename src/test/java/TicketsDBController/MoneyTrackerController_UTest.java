package TicketsDBController;

import controller.MoneyTrackerController;
import org.junit.Before;
import org.junit.Test;

public class MoneyTrackerController_UTest {
    public MoneyTrackerController_UTest() {

    }

    @Before
    public void setUp() {

    }
    /*
    @Test
    public void testAddParticipant() {
        MoneyTrackerController TicketsDBController = new MoneyTrackerController("Vakantie");
        Person person = Mockito.mock(Person.class);
        TicketsDBController.addParticipant(person);
        Assert.assertEquals(1, TicketsDBController.getParticipants().size());
    }

    @Test
    public void testRemoveParticipant() {
        TicketsDBController TicketsDBController = new TicketsDBController("Vakantie");
        Person person_mock = Mockito.mock(Person.class);
        TicketsDBController.addParticipant(person_mock);

        // Get the Person object added to the TicketsDBController
        Person person = TicketsDBController.getParticipants().get(0);

        // Remove the participant
        TicketsDBController.removeParticipant(person);

        Assert.assertEquals(0, TicketsDBController.getParticipants().size());
    }

    @Test
    public void testAddExpense() {
        TicketsDBController TicketsDBController = new TicketsDBController("Vakantie");
        Person person = Mockito.mock(Person.class);
        TicketsDBController.addParticipant(person);
        TicketsDBController.addExpense(person, 50.0);
        Mockito.verify(person).addExpense(50.0);
        Assert.assertEquals(50.0, TicketsDBController.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateTotalExpenses() {
        TicketsDBController TicketsDBController = new TicketsDBController("Vakantie");
        Person person1 = Mockito.mock(Person.class);
        TicketsDBController.addParticipant(person1);
        Person person2 = Mockito.mock(Person.class);
        TicketsDBController.addParticipant(person2);
        TicketsDBController.addExpense(TicketsDBController.getParticipants().get(0), 50.0);
        TicketsDBController.addExpense(TicketsDBController.getParticipants().get(1), 75.0);

        Assert.assertEquals(125.0, TicketsDBController.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateOwedAmounts() {
        TicketsDBController TicketsDBController = new TicketsDBController("Test TicketsDBController");

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
        // Create a TicketsDBController instance and call calculateOwedAmounts with the mocked ticket
        TicketsDBController.addParticipant(person1);
        TicketsDBController.addParticipant(person2);
        TicketsDBController.calculateOwedAmounts(ticket);

        System.out.println(person1.getAmountOwed());
        /*
        // Assert the expected amount owed for participants
        Assert.assertEquals(-100.0, TicketsDBController.getParticipants().get(0).getAmountOwed(), 0.001);
        Assert.assertEquals(0.0, TicketsDBController.getParticipants().get(1).getAmountOwed(), 0.001);
    }
*/


}
