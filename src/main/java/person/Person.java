package person;

import database.TicketsDB;
import exceptions.NegativeNumberException;

public class Person {
    private static int ClassID = 0;
    private final int ID;
    private String name;

    private double expensesPaid; // Hoeveel ik zelf heb betaald.
    private double amountOwed; // Hoeveel moet ik aan een ander betalen.

    public Person(String name) {
        this.name = name;
        ClassID++;
        ID = ClassID;

        expensesPaid = 0.0;
        amountOwed = 0.0;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getExpensesPaid() {
        return expensesPaid;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void addAmountOwed(double amountOwed) {
        this.amountOwed += amountOwed;
    }

    public void addExpense(double expense){

        this.expensesPaid += expense;
    }

    public void resetExpensesAndAmountOwed() {
        this.expensesPaid = 0.0;
        this.amountOwed = 0.0;
    }

    // The following function may be removed or placed somewhere else:
    /*
    Refactor calculateAmountOwed Method:

Instead of directly calculating the owed amount within the Person class,
it's better to perform such calculations in a separate manager class.
Remove the calculateAmountOwed method from the Person class, as it couples
the logic of debt calculation with the person entity.
This separation adheres more to the principle of single responsibility.
     */
    public double calculateAmountOwed(double totalExpenses, int totalParticipants) throws NegativeNumberException {
        if (totalParticipants <= 0 || totalExpenses < 0) {
            throw new NegativeNumberException("Invalid totalExpenses or totalParticipants");
        }

        double eachShare = totalExpenses / totalParticipants;
        return eachShare - this.expensesPaid;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", expensesPaid=" + expensesPaid +
                ", amountOwed=" + amountOwed +
                '}';
    }
}
