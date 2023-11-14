package trip;

import person.Person;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String tripName;
    private static int tripID = 0;
    private List<Person> participants; // TODO: Maak hiervan een set (no duplicates)
    private double totalExpenses;

    public Trip(String tripName) {
        this.tripName = tripName;
        tripID = generateTripID(); // Implements a method to generate unique IDs
        participants = new ArrayList<>();
        totalExpenses = 0.0;
    }

    public void addParticipant(String firstName, String lastName) {
        Person newPerson = new Person(firstName, lastName);
        participants.add(newPerson);
    }

    public void removeParticipant(Person person) {
        participants.remove(person);
    }

    public void addExpense(Person person, double expense) {
        person.addExpense(expense);
        totalExpenses += expense;
    }

    public double calculateTotalExpenses() {
        return totalExpenses;
    }

    /*
        The following function may be removed or handled by something else
     */
    public void calculateOwedAmounts() {
        int totalParticipants = participants.size();
        double eachShare = totalExpenses / totalParticipants;

        for (Person participant : participants) {
            participant.setAmountOwed(eachShare - participant.getExpensesPaid());
        }
    }

    public void displaySummary() {
        System.out.println("Trip Name: " + tripName);
        System.out.println("Total Expenses: " + totalExpenses);

        for (Person participant : participants) {
            System.out.println(participant.getFirstName() + " " + participant.getLastName() +
                    " - Owes: " + participant.getAmountOwed());
        }
    }

    private static int generateTripID() {
        return ++tripID;
    }
}
