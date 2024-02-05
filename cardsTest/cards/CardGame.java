package cards;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class CardGame {
    /** The entry point for the executable
     * @param args
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        boolean validInput = false;
        Pack pack = null;
        String fileLocation = "";
        int numPlayers = 2;
        while (!validInput) { //Ask for inputs repeatedly until they are valid...

            try {
                System.out.println("Please enter the number of players:");

                numPlayers = scan.nextInt();
                if (numPlayers <= 2) {
                    throw new Exception("Invalid number of players (must be an integer greater than two).");
                }
                scan.nextLine();
                System.out.println("Please enter location of pack to load:");
                fileLocation = scan.nextLine();
                pack = new Pack(numPlayers);
                pack.loadPack(fileLocation);
                validInput = true;

            } catch (InvalidPackException e) {
                System.out.printf("%s is not a valid pack file.%n", fileLocation);
            } catch (FileNotFoundException e) {
                System.out.println("Pack file specified does not exist.");
            } catch (Exception e) {
                System.out.println("Invalid number of players (must be an integer greater than two).");
                scan.nextLine();
            }
        }
        scan.close();

    
    // Initialise the player and deck arrays with the length specified by the user input for the number of players.
    Player[] players = new Player[numPlayers];
    CardDeck[] decks = new CardDeck[numPlayers];

    //Initialise each deck object necessary for the game.
    for(int i=0;i<numPlayers;i++){
        decks[i] = new CardDeck(i+1);
    }
    // Initialise each player object necessary for the game - with each player having a left and right deck passed into the constructor.
    CountDownLatch latch = new CountDownLatch(1);
    for(int i=0;i<numPlayers;i++){ // D0  P0 D1  P1   D2   P2   D3   P3
        if(i == numPlayers - 1){
            players[i] = new Player(i+1, decks[numPlayers - 1], decks[0], players, latch);
        } else{
            players[i] = new Player(i+1, decks[i], decks[i+1], players, latch);
        }
    }

    try{
        // Deal the cards to the players in a round-robin fashion
        for(int i=0;i<4;i++){ //Give each player 4 cards in a round-robin fashion.
            for(Player p:players){
                p.addCard(pack.remove());
            }
        }
        // Deal the remaining cards to the decks in a round-robin fashion until the pack is empty.
        while(pack.getSize() > 0){
            for(int i=0;i<numPlayers;i++){
                decks[i].add(pack.remove());
            }
        }
        // Initialise and start each player thread.
        Thread[] threads = new Thread[numPlayers];
        for(int i=0;i<numPlayers;i++){
            threads[i] = new Thread(players[i]);
            threads[i].start();
        }
        
        latch.countDown();
        
     } catch(Exception e){
        e.printStackTrace();
     }


    }
}
