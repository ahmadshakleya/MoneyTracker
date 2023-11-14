package database;

import employee.Person;
import register_entry.RegisterEntry;
import register_entry.RegisterEntryNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistrationDB extends Database
{
    private final HashMap<Person, RegisterEntry> db;

    // Static variable to hold the single instance
    private static RegistrationDB instance;

    // Private constructor to prevent instantiation
    private RegistrationDB()
    {
        this.db = new HashMap<>();
    }

    // Static method to get the instance
    public static RegistrationDB getInstance() {
        if (instance == null){
            instance = new RegistrationDB();
        }
        return instance;
    }

    @Override
    public void addEntry(Person e, RegisterEntry re)
    {
        this.db.put(e, re);
        ArrayList<String> newValue = new ArrayList<>();
        newValue.add(e.getName());
        newValue.add(re.toString());
        support.firePropertyChange("Entry: ", null, newValue); // newValue is a list: the first index
                                                                                    // contains the name of the employee
                                                                                    // and the second index contains the entry.
    }

    @Override
    public RegisterEntry getEntry(Person e)
    {
        return this.db.getOrDefault(e, new RegisterEntryNull());
    }
}
