package TestsDieWeVoorDeOpdrachtMoestenMaken;

import exceptions.NegativeNumberException;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import person.Person;

public class Person_UTest {
    public Person_UTest() {

    }
    @Before
    public void setUp() {

    }

    @Test
    public void testGetName() {
        Person person = new Person("John Doe");
        Assert.assertEquals("John Doe", person.getName());
    }

    @Test
    public void testAddExpense() throws NegativeNumberException {
        Person person = new Person("John Doe");
        person.addExpense(50.0);
        person.addExpense(20.0);
        person.addExpense(30.0);
        Assert.assertEquals(100.0, person.getExpensesPaid(), 0.001);
    }

    @Test
    public void testSetExpense() throws NegativeNumberException {
        Person person = new Person("John Doe");
        person.setExpensesPaid(50.0);
        person.setExpensesPaid(20.0);
        person.setExpensesPaid(30.0);
        Assert.assertEquals(30.0, person.getExpensesPaid(), 0.001);
    }


    @Test
    public void testGetExpense() throws NegativeNumberException {
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

    @Test
    public void testToJson() throws NegativeNumberException {
        final String name = "VOORNAAM ACHTERNAAM";
        final double expense = 1000.0;

        JSONObject expectedJson = new JSONObject();
        expectedJson.put("name", name);
        expectedJson.put("expensesPaid", expense);

        Person person = new Person(name);
        person.addExpense(expense);

        Assert.assertEquals(String.valueOf(expectedJson), String.valueOf(person.toJson()));
    }
}
