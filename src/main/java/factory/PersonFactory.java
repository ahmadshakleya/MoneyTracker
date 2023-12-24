package factory;

import database.PersonDB;
import person.Person;


public class PersonFactory {

    public static Person makePerson(String name) {
        return new Person(name);
    }
}
