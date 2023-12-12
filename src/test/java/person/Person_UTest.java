package person;

import exceptions.NegativeNumberException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Person_UTest {
    public Person_UTest() {

    }
    @Before
    public void setUp() {

    }

    @Test
    public void testGetFirstName() {
        Person person = new Person("John", "Doe");
        Assert.assertEquals("John", person.getName());
    }

    @Test
    public void testSetFirstName() {
        Person person = new Person("John", "Doe");
        person.setName("Alice");
        Assert.assertEquals("Alice", person.getName());
    }

    @Test
    public void testGetLastName() {
        Person person = new Person("John", "Doe");
        Assert.assertEquals("Doe", person.getLastName());
    }

    @Test
    public void testSetLastName() {
        Person person = new Person("John", "Doe");
        person.setLastName("Smith");
        Assert.assertEquals("Smith", person.getLastName());
    }

    @Test
    public void testAddExpense() throws NegativeNumberException {
        Person person = new Person("John", "Doe");
        person.addExpense(50.0);
        Assert.assertEquals(50.0, person.getExpensesPaid(), 0.001);
    }

    @Test
    public void testResetExpensesAndAmountOwed() throws NegativeNumberException {
        Person person = new Person("John", "Doe");
        person.addExpense(50.0);
        person.setAmountOwed(20.0);
        person.resetExpensesAndAmountOwed();
        Assert.assertEquals(0.0, person.getExpensesPaid(), 0.001);
        Assert.assertEquals(0.0, person.getAmountOwed(), 0.001);
    }

    @Test
    public void testCalculateAmountOwed() throws NegativeNumberException {
        Person person = new Person("John", "Doe");
        double totalExpenses = 200.0;
        int totalParticipants = 4;

        person.addExpense(50.0); // John paid 50.0
        double expectedOwed = (totalExpenses / totalParticipants) - person.getExpensesPaid();
        double calculatedOwed = person.calculateAmountOwed(totalExpenses, totalParticipants);

        Assert.assertEquals(expectedOwed, calculatedOwed, 0.001);
    }
}
