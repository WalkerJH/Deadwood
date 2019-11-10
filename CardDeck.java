/**
 * Maintains a Deck of scene cards (Card.java) to draw from
 */

import java.util.Stack;

public class CardDeck {
    private static Stack<Card> deck;

    public CardDeck() {

    }

    public Card drawCard(){
        return deck.pop();
    }
}
