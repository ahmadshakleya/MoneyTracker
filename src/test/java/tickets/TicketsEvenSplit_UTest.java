package tickets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import person.Person;


import java.util.HashSet;
import java.util.Set;

import static org.powermock.api.mockito.PowerMockito.spy;

// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(TicketEvenSplit.class)
public class TicketsEvenSplit_UTest {

    public TicketsEvenSplit_UTest(){

    }

    @Before
    public void initialize(){


    }

    @Test
    public void t_getTotal() {
        Set<Person> mock_People = new HashSet<>();
        Person mock_Person1 = Mockito.mock(Person.class);
        Person mock_Person2 = Mockito.mock(Person.class);
        Person mock_Person3 = Mockito.mock(Person.class);

        Mockito.when(mock_Person1.getName()).thenReturn("Jan");
        Mockito.when(mock_Person2.getName()).thenReturn("Per");
        Mockito.when(mock_Person3.getName()).thenReturn("Hu");

        mock_People.add(mock_Person1);
        mock_People.add(mock_Person2);
        mock_People.add(mock_Person3);

        TicketEvenSplit ticketUnderTest = new TicketEvenSplit(100.0, mock_People, "");

        double expectedTotal = 100;

        double ansTotal = ticketUnderTest.getTotal();

        assert ansTotal == expectedTotal;
    }


}


