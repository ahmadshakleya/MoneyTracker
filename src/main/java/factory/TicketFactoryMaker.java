package factory;

import controller.TicketsDBController;

public class TicketFactoryMaker {

    private TicketsDBController controller;

    public TicketFactoryMaker(TicketsDBController controller){
        this.controller = controller;
    }

    public TicketFactoryEvenSplit makeEvenTicketFactory() {return new TicketFactoryEvenSplit(controller);}

    public TicketFactoryUnevenSplit makeUnevenTicketFactory() {
        return new TicketFactoryUnevenSplit(controller);
    }
}
