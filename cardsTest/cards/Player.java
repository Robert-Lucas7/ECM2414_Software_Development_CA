package cards;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
public class Player implements Runnable, GameWonListener{
    private final ArrayList<Card> hand; //When declared as final, the arraylist's methods (add and remove) can still be called but the variable cannot be reassigned.
    private int pointer;
    private final CardDeck leftDeck;
    private final CardDeck rightDeck;
    private final int preferredCard;
    private boolean gameNotFinished;
    private final GameWonListener[] listeners;
    private CountDownLatch latch;

    /** Method that acts as a response for when a player wins a game, so each player threads terminated elegantly.
    * @param playerWhoWon The player who has won the game.
    */
    @Override
    public void gameHasBeenWon(Player playerWhoWon) { //What to do when someone has won.
        this.gameNotFinished = false;
        //Write "Player i has informed player j that player i has won" to player j's output file.

        try (FileWriter f = new FileWriter(String.format("player%d_output.txt", this.preferredCard), true)) {
            if (playerWhoWon.getPreferredCard() == this.preferredCard) {
                f.write(String.format("player %d wins\n", this.preferredCard));
            } else {
                f.write(String.format("player %d has informed player %d that player %d has won\n",
                        playerWhoWon.getPreferredCard(), this.preferredCard, playerWhoWon.getPreferredCard()));
            }
            f.write(String.format("player %d exits\n", this.preferredCard));
            f.write(String.format("player %d final hand: %s\n", this.preferredCard, this.showHand()));

        } catch (IOException ex) {

        }

    }
    
    /** Writes the player's initial hand to the player's output text file.
     */
    private void writeInitialHandsToFile(){
        try(FileWriter f = new FileWriter(String.format("player%d_output.txt", this.preferredCard), false)){
            f.write(String.format("player %d initial hand: %s\n", this.preferredCard, this.showHand()));
        } catch(IOException e){}
    }
    
    /** Writes the deck contents of the leftDeck variable to an output text file.
     */
    private void writeDeckContentsToFile(){
        try (FileWriter f = new FileWriter(String.format("deck%d_output.txt", this.leftDeck.getDeckNumber()), false)) {
            
            f.write(String.format("deck%d contents: %s", this.leftDeck.getDeckNumber(), this.leftDeck.showDeck()));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    /** Method required for the Runnable interface and implements the game-playing strategy for the player for the card game.
     */
     @Override
    public void run(){
        writeInitialHandsToFile();
        try{
            latch.await(); // All threads will wait until the CountDownLatch reaches 0.
        }
        catch(InterruptedException e){}
        
        while(this.gameNotFinished){  //Execute the game-playing strategy whilst the game has not finished...
            try {
                if(this.checkIfWon()){
                    System.out.println(String.format("player %d wins", this.preferredCard));
                    for (GameWonListener l : listeners) { //Notify the other players that someone has won the game.
                        l.gameHasBeenWon(this);
                    }
                } else {
                    Card cardDrawn = leftDeck.remove();
                    if (cardDrawn != null) {
                        Card cardRemoved = this.removeCard();
                        this.addCard(cardDrawn);
                        rightDeck.add(cardRemoved);
                        this.writeMoveToFile(cardDrawn.getValue(), cardRemoved.getValue());

                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        writeDeckContentsToFile();
    }
    
    
    /** Creates a new player with the following parameters:
     * @param preferredCard The card value that the player will collect and not get rid of.
     * @param leftDeck The CardDeck where the player draws their cards from.
     * @param rightDeck The CardDeck where the player puts the cards they removed from their hand.
     * @param listeners An array containing the players in the game.
     */
    public Player(int preferredCard, CardDeck leftDeck, CardDeck rightDeck, GameWonListener[] listeners, CountDownLatch latch ){
        this.hand = new ArrayList<>();
        this.preferredCard = preferredCard;
        this.rightDeck = rightDeck;
        this.leftDeck = leftDeck;
        this.pointer = 0;
        this.gameNotFinished = true;
        this.listeners = listeners;
        this.latch = latch;
    }

    /** Removes a card from the player's hand. The card removed is chosen at random from the cards in the hand that are not of the preferred card value.
     * @return The card that has been removed.
     * @throws Exception Thrown when the hand is not full.
     */
    public Card removeCard() throws Exception{
        Random r = new Random();
        
        
        if (this.hand.size() != 4){
            throw new Exception("Hand is not full");
        }else {
            if(!this.checkIfWon()) {
                int index = r.nextInt(this.hand.size() - this.pointer) + this.pointer; // Gets an index of a random card that doesn't have the value of the preferred card of the player.

                Card card1 = this.hand.remove(index);
                return card1;
            } else{
                throw new Exception("Cannot remove card from winning hand.");
            }
        }
    }


    /** Adds the specified card to the player's hand.
     * @param card1 The card to be added to the player's hand.
     * @throws Exception Thrown when there is no room to add a card.
     */
    public void addCard (Card card1) throws Exception{
        if (this.hand.size() >= 4 ){
            throw new Exception("Hand is full");
        }else {
            this.hand.add(card1);
            if (card1.getValue() == preferredCard) {
                Collections.swap(this.hand, this.hand.size() - 1, this.pointer++);
            }
        }
    }

    /** Gets a string representation of the player's hand.
     * @return A string representation of the player's hand.
     */
    public String showHand(){
        String s = ""; //A string builder may be more efficient but the difference will be negligable for small inputs such as n=4.
        for(Card c : this.hand){
            s += c.getValue() + " ";
        }
        return s.stripTrailing();
    }

    /** Checks if the player has won the game by checking two conditions: if the hand contains four cards and each card in the hand has the same value.
     * @return A boolean value for if the player has a winning hand.
     */
    private boolean checkIfWon() {
        if(this.hand.size() == 4){
            for (Card c : this.hand ){
                if(c.getValue() != this.preferredCard){
                    return false;
                }
            }
            return true;
        } else{
            return false;
        }
        
    }

    /** Writes the player's move for that round to their respective file.
     * @param cardDrawn The card drawn from the left deck.
     * @param cardRemoved The card that has been removed from the player's hand.
     * @param isFirstGo A boolean value for if it is the player's first go in the game.
     */
    public void writeMoveToFile(int cardDrawn, int cardRemoved) {
        String filename = "player" + this.preferredCard + "_output.txt";
        try (FileWriter f = new FileWriter(filename, true)){
            f.write(String.format("player %d draws a %d from deck %d\n", this.preferredCard, cardDrawn, this.leftDeck.getDeckNumber()));
            f.write(String.format("player %d discards a %d to deck %d\n", this.preferredCard, cardRemoved, this.rightDeck.getDeckNumber()));
            f.write(String.format("player %d current hand is %s\n", this.preferredCard, this.showHand()));

        }catch(IOException e){
            
        }
    }
    /** Gets the number of cards in the player's hand.
     * @return The number of cards in the player's hand.
     */
    public int getHandSize(){
        return this.hand.size();
    }
    public int getPreferredCard(){
        return this.preferredCard;
    }

}
