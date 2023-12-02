package factory;

public class TicketFactoryMaker {

    public static TicketFactoryEvenSplit makeEvenTicketFactory() {
        return new TicketFactoryEvenSplit();
    }

    public static TicketFactoryUnevenSplit makeUnevenTicketFactory() {
        return new TicketFactoryUnevenSplit();
    }
}
