import java.util.Stack;
import java.util.Collections;

/**
 * Maintains a Deck of scene cards (Card.java) to draw from
 */

public class CardDeck {
    private static Stack<Card> deck;

    public CardDeck(Card ... args) {
        deck = new Stack<Card>();
        for (int i = 0; i < args.length; i++) {
            deck.push(args[i]);
        }
        Collections.shuffle(deck);
    }

    public Card drawCard(){
        return deck.pop();
    }

    public void addCard(Card c) { deck.push(c); }
}
