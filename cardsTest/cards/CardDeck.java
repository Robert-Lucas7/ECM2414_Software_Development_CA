package cards;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
// Use LinkedBlockingQueue instead of Concurrent

/*
 * Look at https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html
 * Can remove synchronized methods.
 */
public class CardDeck {
    /** The cards contained within the deck of cards, acting as a queue.
     */
    protected ConcurrentLinkedQueue<Card> cards;
    /** The number associated with the deck. 
     */
    private final int deckNumber;
    /** Creates a deck of cards with the specified deck number.
     * @param deckNumber The number associated with the deck.
     */
    public CardDeck(int deckNumber){
        cards = new ConcurrentLinkedQueue<>();
        this.deckNumber = deckNumber;
    }
    /** A thread-safe method to add a card to the card deck.
     * @param cardToAdd The card to add to the deck.
     */
    public void add(Card cardToAdd){
        cards.offer(cardToAdd);
    }
    /** A thread-safe method to remove a card from deck of cards.
     * @return The card removed from the top of the deck.
     * @throws Exception When the deck is empty.
     */
    public Card remove(){
        return cards.poll();
    }
    /** Gets the number of cards in the deck.
     * @return The number of cards in the deck.
     */
    public int getSize(){
        //Size is not a constant-time action so may use synchronised method here.
        return cards.size();
    }
    /** Gets a string representation of the CardDeck instance.
     * @return a string representation of the card deck.
     */
    @Override
    public String toString(){
        String s = String.format("deck%d contents: %s", this.deckNumber, this.showDeck());
        return s;
    }
    /** Get the number associated with the deck of cards.
     * @return the number associated with the deck of cards.
     */
    public int getDeckNumber(){
        return this.deckNumber;
    }
    public String showDeck(){ //Does the order of contents displayed matter.
        Iterator<Card> iterator = cards.iterator();
        String s = "";
        while(iterator.hasNext()){
            s += iterator.next().getValue()+" ";
        }
        return s.stripTrailing();
    }

}
