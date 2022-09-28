/**
 * An exception that is thrown when user does not provide valid response for consent to data collection
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class InvalidInputException extends Exception {

    /**
     * Constructor with default message
     */
    public InvalidInputException(){
        super("Invalid response. Do you consent to have your decisions saved to a file? (yes/no)");
    }

    /**
     * Constructor with specified message
     * @param message message to be displayed
     */
    public InvalidInputException(String message){
        super(message);
    }

}
