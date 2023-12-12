package factory;

import database.PersonDB;
import person.Person;


public class PersonFactory {

    public static Person makePerson(String name) {
        PersonDB db = PersonDB.getInstance();
        Person newPerson = new Person(name);
        db.addEntry(newPerson);
        return newPerson;
    }
}
