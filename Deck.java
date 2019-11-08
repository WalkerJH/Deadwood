/**
 * Maintains a Deck of scene cards (Card.java) to draw from
 */

import java.util.Stack;

public class Deck {
    private static Stack<Card> deck;

    public Deck() {

    }

    public Card drawCard(){
        return deck.pop();
    }
}
