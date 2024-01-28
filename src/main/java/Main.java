import controller.MoneyTrackerController;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import observers.PersonUpdaters;
import person.Person;
import tag.Tag;
import visualisation.MoneyTrackerApp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        MoneyTrackerApp app = new MoneyTrackerApp();
        //main.run();
    }
}
