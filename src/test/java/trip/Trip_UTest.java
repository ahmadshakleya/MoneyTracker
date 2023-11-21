package trip;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import person.Person;

import java.util.ArrayList;
import java.util.List;

public class Trip_UTest {
    public Trip_UTest() {

    }

    @Before
    public void setUp() {

    }

    @Test
    public void testAddParticipant() {
        Trip trip = new Trip("Vakantie");
        trip.addParticipant("John", "Doe");
        Assert.assertEquals(1, trip.getParticipants().size());
    }

    @Test
    public void testRemoveParticipant() {
        Trip trip = new Trip("Vakantie");
        trip.addParticipant("John", "Doe");

        // Get the Person object added to the trip
        Person person = trip.getParticipants().get(0);

        // Remove the participant
        trip.removeParticipant(person);

        Assert.assertEquals(0, trip.getParticipants().size());
    }

    @Test
    public void testAddExpense() {
        Trip trip = new Trip("Vakantie");
        Person person = Mockito.mock(Person.class);
        trip.addParticipant("John", "Doe");
        trip.addExpense(person, 50.0);
        Mockito.verify(person).addExpense(50.0);
        Assert.assertEquals(50.0, trip.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateTotalExpenses() {
        Trip trip = new Trip("Vakantie");
        trip.addParticipant("John", "Doe");
        trip.addParticipant("Alice", "Smith");
        trip.addExpense(trip.getParticipants().get(0), 50.0);
        trip.addExpense(trip.getParticipants().get(1), 75.0);

        Assert.assertEquals(125.0, trip.calculateTotalExpenses(), 0.001);
    }

    @Test
    public void testCalculateOwedAmounts() {
        Trip trip = new Trip("Vakantie");

        // Mock persons
        Person john = Mockito.mock(Person.class);
        Person alice = Mockito.mock(Person.class);

        List<Person> mockedParticipants = new ArrayList<>();
        mockedParticipants.add(john);
        mockedParticipants.add(alice);

        trip.setParticipants(mockedParticipants);

        // Set expenses paid for mocked persons
        Mockito.when(john.getExpensesPaid()).thenReturn(60.0);
        Mockito.when(alice.getExpensesPaid()).thenReturn(40.0);

        // Calculate owed amounts
        trip.calculateOwedAmounts();

        System.out.println(john.getAmountOwed());
        System.out.println(alice.getAmountOwed());
        // Verify setAmountOwed calls with expected owed amounts
        //Mockito.verify(john).setAmountOwed(10.0); // John owes 10 (60 - 50)
        //Mockito.verify(alice).setAmountOwed(-10.0); // Alice owes -10 (40 - 50)
    }
}
