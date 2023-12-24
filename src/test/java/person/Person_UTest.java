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
    public void testGetName() {
        Person person = new Person("John Doe");
        Assert.assertEquals("John", person.getName());
    }

    @Test
    public void testAddExpense() throws NegativeNumberException {
        Person person = new Person("John Doe");
        person.addExpense(50.0);
        Assert.assertEquals(50.0, person.getExpensesPaid(), 0.001);
    }

    @Test
    public void testResetExpenses() throws NegativeNumberException {
        Person person = new Person("John Doe");
        person.addExpense(50.0);
        person.resetExpenses();
        Assert.assertEquals(0.0, person.getExpensesPaid(), 0.001);
    }
}
