/**
 * An exception that is thrown when there is an invalid number of data fields per row in config file
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class InvalidDataFormatException extends Exception {

    /**
     * Constructor with default message
     */
    public InvalidDataFormatException(){
        super("WARNING: invalid data format in config file in line ");
    }

    /**
     * Constructor with specified message
     * @param message message to be displayed
     */
    public InvalidDataFormatException(String message){
        super(message);
    }

}
