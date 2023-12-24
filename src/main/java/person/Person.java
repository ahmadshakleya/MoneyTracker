package person;

import org.json.simple.JSONObject;

public class Person {

    // TODO: Ik denk niet dat deze gebruikt worden? verwijderen?
    // private static int ClassID = 0;
    // private final int ID;

    private final String name;
    private double expensesPaid; // Hoeveel ik zelf heb betaald.

    public Person(String name) {
        this.name = name;
        //ClassID++;
        //ID = ClassID;
        expensesPaid = 0.0;

    }

    //public int getID() {return ID;}

    public String getName() {
        return name;
    }

    //public void setName(String name) {this.name = name;}

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
                // "ID=" + ID +
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
