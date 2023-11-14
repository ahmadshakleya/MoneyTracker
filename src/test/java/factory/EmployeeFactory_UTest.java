package factory;

import employee.Employee;
import employee.Programmer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.spy;

public class EmployeeFactory_UTest {
    public EmployeeFactory_UTest() {
    }

    @Before
    public void initialize() {

    }

    @Test
    public void t_getEmployee() throws Exception{
        EmployeeFactory factoryUnderTest = spy(new EmployeeFactory());
        String name = "Ahmad";

        Programmer mock_programmer = Mockito.mock(Programmer.class);
        PowerMockito.whenNew(Programmer.class).withArguments(name).thenReturn(mock_programmer);

        Employee e1 = factoryUnderTest.getEmployee(name, "Programmer");
        Mockito.verify(factoryUnderTest, Mockito.times(1)).getEmployee(e1.getName(), e1.getFunction());
    }
}
