package person;

import org.json.simple.JSONObject;

public class Person {
    private final String name;
    private double expensesPaid; // Hoeveel ik zelf heb betaald.

    public Person(String name) {
        this.name = name;
        expensesPaid = 0.0;

    }

    public String getName() {
        return name;
    }

    public double getExpensesPaid() {
        return expensesPaid;
    }

    public void addExpense(double expense) {

        this.expensesPaid += expense;
    }

    public void setExpensesPaid(double money){
        this.expensesPaid = money;
    }

    public void resetExpenses() {
        this.expensesPaid = 0.0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", expensesPaid=" + expensesPaid +
                '}';
    }

    public JSONObject toJson() {
        JSONObject personJson = new JSONObject();
        personJson.put("name", name);
        personJson.put("expensesPaid", expensesPaid);
        return personJson;
    }
}
