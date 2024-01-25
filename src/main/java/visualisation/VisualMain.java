package visualisation;

import database.PersonDB;
import person.Person;

public class VisualMain {
    public static void main(String[] args) throws InterruptedException {
        ShowPossibilities frame2 = new ShowPossibilities();
        while (!frame2.getShowPersons()) {
            frame2.showPersons();
            Thread.sleep(3000);
        }
        System.out.println(frame2.getShowPersons());

        PersonDB db = PersonDB.getInstance();

        Person person1 = new Person("Ahmad Shakleya");
        Person person2 = new Person("Berkay Yildirim");

        db.addEntry(person1);
        db.addEntry(person2);

        PersonsInDatabase personsInDatabaseFrame = new PersonsInDatabase(db);
        while (!personsInDatabaseFrame.addNewPersonButtonIsPressed) {
            personsInDatabaseFrame.GotoNext();
            Thread.sleep(3000);
        }

        AddPersonTodatabase frame3 = new AddPersonTodatabase(db);
        while(!frame3.AddPersonButtonIsPressed){
            frame3.GoToNext();
            Thread.sleep(3000);
        }

        /*
        for (Person person: db.showPeople().values()){ //gebruik MoneyTrackerController.getAllPeople
            System.out.println(person.getName());
        }

         */

    }
}
