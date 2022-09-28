/**
 * An exception that is thrown when invalid field values are used for character's attributes
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class InvalidCharacteristicException extends Exception {

    /**
     * Constructor with default message
     */
    public InvalidCharacteristicException(){
        super("WARNING: invalid characteristic in config file in line ");
    }

    /**
     * Constructor with specified message
     * @param message message to be displayed
     */
    public InvalidCharacteristicException(String message){
        super(message);
    }

}
