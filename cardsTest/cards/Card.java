package cards;
public class Card{
    /** Represents the value of the card.
     */
    private final int value;
    /**
     * Creates a card with the specified value.s
     * @param value The value of the card.
     */
    public Card(int value){
        this.value = value;
    }
    /** Gets the value of the card.
     * @return An integer representing the value of the card.
     */
    public int getValue(){
        return this.value;
    }



}