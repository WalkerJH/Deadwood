import java.util.Stack;

/**
 * Maintains a Deck of scene cards (Card.java) to draw from
 */

public class CardDeck {
    private static Stack<Card> deck;

    public CardDeck() {

    }

    public Card drawCard(){
        return deck.pop();
    }
}
