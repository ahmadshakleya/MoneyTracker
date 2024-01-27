package factory;

import controller.MoneyTrackerController;

public class TicketFactoryMaker {

    private final MoneyTrackerController controller;

    public TicketFactoryMaker(MoneyTrackerController controller){
        this.controller = controller;
    }

    public TicketFactoryEvenSplit makeEvenTicketFactory() {return new TicketFactoryEvenSplit(controller);}

    public TicketFactoryUnevenSplit makeUnevenTicketFactory() {
        return new TicketFactoryUnevenSplit(controller);
    }

    public MoneyTrackerController getController() {
        return controller;
    }
}
