import java.util.Stack;
import java.util.Collections;

/**
 * Maintains a Deck of scene cards (Card.java) to draw from
 */

public class CardDeck {
    private static Stack<Card> deck;

    int numCards;

    public CardDeck() {
        deck = new Stack<>();
    }

    public CardDeck(Card ... args) {
        deck = new Stack<Card>();
        numCards = 0;
        for (int i = 0; i < args.length; i++) {
            deck.push(args[i]);
            numCards ++;
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card drawCard(){
        if (!deck.empty())
            return deck.pop();
        else
            return null;
    }

    public int getNumCards() { return numCards; }

    public void addCard(Card c) { deck.push(c); }
}
