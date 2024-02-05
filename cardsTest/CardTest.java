import cards.Card;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    @DisplayName("Check card takes correct value")
    public void testCardTakesCorrectValue() {
        int val = 5;
        Card c = new Card(5);
        assertEquals(5, c.getValue());
    }
}
