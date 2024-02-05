package cards;
public class InvalidPackException extends Exception{
    /** Creates an InvalidPackException with a set message.
     */
    public InvalidPackException(){
        super("A pack must contain 8n non-negative integers (where n is the number of players)");
    }
}
