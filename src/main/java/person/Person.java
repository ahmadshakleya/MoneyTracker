package person;

import exceptions.NegativeNumberException;

public class Person {
    private static int ClassID = 0;
    private int ID;
    private String firstName;
    private String lastName;

    private double expensesPaid; // Hoeveel ik zelf heb betaald.
    private double amountOwed; // Hoeveel moet ik aan een ander betalen.

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        ClassID++;
        ID = ClassID;

        expensesPaid = 0.0;
        amountOwed = 0.0;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getExpensesPaid() {
        return expensesPaid;
    }

    public double getAmountOwed() {
        return amountOwed;
    }

    public void setAmountOwed(double amountOwed) throws NegativeNumberException {
        if (amountOwed < 0) {
            throw new NegativeNumberException("Invalid amount " + amountOwed);
        }
        this.amountOwed = amountOwed;
    }

    public void addExpense(double expense) throws NegativeNumberException {
        if (expense < 0) {
            throw new NegativeNumberException("Invalid expense " + expense);
        }
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
        if(totalParticipants <= 0 || totalExpenses < 0) {
            throw new NegativeNumberException("Invalid totalExpenses or totalParticipants");
        }

        double eachShare = totalExpenses / totalParticipants;
        return eachShare - this.expensesPaid;
    }
}
