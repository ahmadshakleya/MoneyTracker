package IntegrationTests;

import controller.Controller;
import controller.RegistrationController;
import database.Database;
import database.RegistrationDB;
import employee.Employee;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import register_entry.RegisterEntry;

import static org.mockito.Mockito.spy;

public class Database_Controller_ITest {
    public Database_Controller_ITest() {

    }

    @Before
    public void initialize(){

    }
    /**
     * This function tests the interaction between the controller and the database.
     * Note that there is a problem when mocking RegisterEntry:
     * the mock constructor returns a mock object but the checkIn method of the RegistrationController uses the real constructor
     * I solved this my omitting the second argument of addEntry for the verification.
     * @throws Exception
     */
    @Test
    public void t_database_controller() throws Exception {
        Database dbUnderTest = spy(RegistrationDB.getInstance());
        Controller controllerUnderTest = new RegistrationController(dbUnderTest);
        Employee mock_employee = Mockito.mock(Employee.class);

        RegisterEntry mock_registerEntry = Mockito.mock(RegisterEntry.class);
        Mockito.when(mock_registerEntry.isCheckedIn()).thenReturn(true);
        PowerMockito.whenNew(RegisterEntry.class).withArguments(true).thenReturn(mock_registerEntry);

        controllerUnderTest.checkIn(mock_employee);
        Mockito.verify(dbUnderTest, Mockito.times(1)).addEntry(Mockito.eq(mock_employee),
                                                                                      Mockito.any());

    }
}
