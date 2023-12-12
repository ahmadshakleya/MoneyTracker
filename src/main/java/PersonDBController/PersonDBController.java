package PersonDBController;

import database.PersonDB;
import person.Person;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

public class PersonDBController {
    private final PersonDB personDB;

    public PersonDBController() {
        this.personDB = PersonDB.getInstance();
    }

    public void addPerson(Person person) {
        personDB.addEntry(person);
    }

    public Person getPersonByID(Integer ID) {
        return personDB.getEntry(String.valueOf(ID));
    }

    public void addObserver(PropertyChangeListener listener) {
        personDB.addObserver(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        personDB.removeObserver(listener);
    }
}

