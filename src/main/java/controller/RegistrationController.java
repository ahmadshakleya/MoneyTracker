package controller;

import database.Database;
import person.Person;
import register_entry.RegisterEntry;

public class RegistrationController implements Controller
{
    private Database db;

    public RegistrationController(Database db)
    {
        this.db = db;
    }

    @Override
    public void checkIn(Person e)
    {
        RegisterEntry entry = new RegisterEntry(true);
        db.addEntry(e, entry);
    }

    @Override
    public void checkOut(Person e)
    {
        RegisterEntry entry = new RegisterEntry(false);
        db.addEntry(e, entry);
    }
}
