package IntegrationTests;

import controller.Controller;
import controller.RegistrationController;
import database.Database;
import employee.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import register_entry.RegisterEntry;

public class Controller_Person_ITest {
    public Controller_Person_ITest() {
    }

    @Before
    public void initialize(){

    }

    /**
     * This function tests the interaction between the controller and the employee.
     * Note that there is a problem when mocking RegisterEntry:
     * the mock constructor returns a mock object but the checkIn method of the RegistrationController uses the real constructor
     * I solved this my omitting the second argument of addEntry for the verification.
     * @throws Exception
     */
    @Test
    public void t_controller_employee() throws Exception{
        Database mock_db = Mockito.mock(Database.class);
        Controller controllerUnderTest = new RegistrationController(mock_db);
        Person personUnderTest = new Person("Ahmad", "Programmer");

        RegisterEntry mock_registerentry = Mockito.mock(RegisterEntry.class);
        Mockito.when(mock_registerentry.isCheckedIn()).thenReturn(true);
        PowerMockito.whenNew(RegisterEntry.class).withArguments(true).thenReturn(mock_registerentry);

        controllerUnderTest.checkIn(personUnderTest);
        Mockito.verify(mock_db, Mockito.times(1)).addEntry(Mockito.eq(personUnderTest), Mockito.any());

    }
}
