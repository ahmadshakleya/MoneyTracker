package exceptions;

public class PersonAlreadyExists extends Exception {
    public PersonAlreadyExists(String message) {
        super(message);
    }
}
