package factory;

import person.Person;

@Deprecated //delete classe
public class PersonFactory {
    public Person getPerson(int id, String firstName, String lastName) {
        return new Person(firstName, lastName); // %TODO was "new Person(id, firstName, lastName);"
    }
}
