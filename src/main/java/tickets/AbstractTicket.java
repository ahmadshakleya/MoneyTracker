package tickets;

import java.util.Date;

public abstract class AbstractTicket implements ITicket {

    protected Date date;
    protected double total;
    protected String description;
    protected boolean isPaid = false;


    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getTotal() {
        return total;
    }

    @Override
    public boolean getIsPaid() {
        return isPaid;
    }

    @Override
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
