package controller;

import database.Database;
import person.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import register_entry.RegisterEntry;

// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(RegistrationController.class)
public class RegistrationController_UTest
{
    public RegistrationController_UTest()
    {

    }

    @Before
    public void initialize()
    {

    }

    @Test
    public void t_checkIn() throws Exception
    {
        // Create mock objects for database and person
        Database mock_db = Mockito.mock(Database.class);
        Person mock_person = Mockito.mock(Person.class);

        // Make sure the constructor of RegisterEntry is mocked too
        RegisterEntry mock_registerEntry = Mockito.mock(RegisterEntry.class);
        Mockito.when(mock_registerEntry.isCheckedIn()).thenReturn(true);
        PowerMockito.whenNew(RegisterEntry.class).withArguments(true).thenReturn(mock_registerEntry);

        /*
            1. "new RegisterEntry" will return a mock object, carried out by 3 lines above
            2. The mocked database will be called, with method "addEntry"
            3. Verify that the method "addEntry" got called, with the right arguments: (mock_person, mock_registerEntry)
         */
        Controller controllerUnderTest = new RegistrationController(mock_db);
        controllerUnderTest.checkIn(mock_person);
        Mockito.verify(mock_db, Mockito.times(1)).addEntry(mock_person, mock_registerEntry);
    }

    @Test
    public void t_checkOut() throws Exception
    {
        // Create mock objects for database and person
        Database mock_db = Mockito.mock(Database.class);
        Person mock_person = Mockito.mock(Person.class);

        // Make sure the constructor of RegisterEntry is mocked too
        RegisterEntry mock_registerEntry = Mockito.mock(RegisterEntry.class);
        Mockito.when(mock_registerEntry.isCheckedIn()).thenReturn(false);
        PowerMockito.whenNew(RegisterEntry.class).withArguments(false).thenReturn(mock_registerEntry);

        /*
            1. "new RegisterEntry" will return a mock object, carried out by 3 lines above
            2. The mocked database will be called, with method "addEntry"
            3. Verify that the method "addEntry" got called, with the right arguments: (mock_person, mock_registerEntry)
         */
        Controller controllerUnderTest = new RegistrationController(mock_db);
        controllerUnderTest.checkOut(mock_person);
        Mockito.verify(mock_db, Mockito.times(1)).addEntry(mock_person, mock_registerEntry);
    }
}
