

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import cards.CardDeck;
import cards.Card;
public class CardDeckTest {
    CardDeck deck;
    @BeforeEach
    public void setup(){
        deck = new CardDeck(1);
    }
    
    @Test
    @DisplayName("Ensure card is removed from deck.")
    public void testRemove() {
        deck.add(new Card(1));
        deck.remove();
        assertEquals(0,deck.getSize());

    }
    
    @Test
    @DisplayName("Ensure card is added to deck.")
    public void testAdd() {
        deck.add(new Card(3));
        assertEquals(1, deck.getSize());
    }
    
    @Test
    @DisplayName("Ensure deck has correct number assigned to it.")
    public void testGetDeckNumber() {
        assertEquals(1, deck.getDeckNumber());
    }
    
    @Test
    @DisplayName("Ensure show deck displays correct content.")
    public void testShowDeck() {
        for (int i = 1; i < 10; i++) {
            deck.add(new Card(i));
        }
        assertEquals("1 2 3 4 5 6 7 8 9", deck.showDeck());
    }

}
