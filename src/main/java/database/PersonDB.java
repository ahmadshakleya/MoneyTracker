package database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Person;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class PersonDB
{
    private HashMap<String, Person> db;

    // Static variable to hold the single instance
    private static PersonDB instance;
    protected PropertyChangeSupport support;

    // Private constructor to prevent instantiation
    private PersonDB()
    {
        support = new PropertyChangeSupport(this);
        this.db = new HashMap<>();
    }

    // Static method to get the instance
    public static PersonDB getInstance() {
        if (instance == null){
            instance = new PersonDB();
        }
        return instance;
    }

    public void addEntry(Person person)
    {
        db.put(person.getName(), person);
        ArrayList<String> newValue = new ArrayList<>();
        newValue.add(person.getName());
        support.firePropertyChange("people who are effected: ", null, newValue); // Persoon
    }

    public Person getEntry(String name)
    {
        return this.db.get(name);
    }

    public void addObserver(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void removeObserver(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public HashMap<String, Person> showPeople() {
        return db;
    }

    public void reset(){
        support = new PropertyChangeSupport(this);
        this.db = new HashMap<>();
    }

    public JSONArray toJson(){
        JSONArray jsonArray = new JSONArray();
        for (String key : db.keySet()){
            jsonArray.add(db.get(key).toJson());
        }
        return jsonArray;
    }

    public ArrayList<Person> getAllPeople(){
        return new ArrayList<>(db.values());
    }
}
