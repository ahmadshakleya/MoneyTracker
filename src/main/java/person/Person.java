package person;

import exceptions.NegativeNumberException;

import java.util.Set;

public class Person {
    private static int ClassID = 0;
    private final int ID;
    private String name;
    private double expensesPaid; // Hoeveel ik zelf heb betaald.

    public Person(String name) {
        this.name = name;
        ClassID++;
        ID = ClassID;
        expensesPaid = 0.0;

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

    public void addExpense(double expense) {

        this.expensesPaid += expense;
    }

    public void resetExpenses() {
        this.expensesPaid = 0.0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", expensesPaid=" + expensesPaid +
                '}';
    }
}
