package factory;

import person.Person;

public class PersonFactory {
    public Person getPerson(int id, String firstName, String lastName) {
        return new Person(id, firstName, lastName);
    }
}
