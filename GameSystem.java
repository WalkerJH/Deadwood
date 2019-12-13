/**
 * Keeps track of game state and performs basic game operations
 */
import java.util.*;

public class GameSystem {

    private Player[] players;
    private int numPlayers;
    private Board board;
    private CardDeck cardDeck;
    private int day;
    private int turn;

    public static final int[] RANK_UP_REQUIREMENTS_CASH = {4, 10, 18, 28, 40};
    public static int[] RANK_UP_REQUIREMENTS_CREDITS = {5, 10, 15, 20, 25};

    public GameSystem(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public void setUpGame() throws Exception {
        day = 0;
        turn = 0;
        cardDeck = new CardDeck();
        ParseXML parser = new ParseXML("board.xml");
        board = parser.readBoardData();
        parser.setDocument("cards.xml");
        cardDeck = parser.readCardData();
        cardDeck.shuffle();
        distributeCards();

        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i, board.findLocation("Trailer"));
        }
    }

    public Player getCurrentPlayer(){
        return players[turn];
    }

    public int getTurn() { return turn; }

    public Player[] getPlayers() {
        return players;
    }

    public int getDay() { return day; }

    public int getNumPlayers() {
        return numPlayers;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public Board getBoard() {
        return board;
    }

    public Location findLocation(String locationName) {
        return board.findLocation(locationName);
    }

    private void distributeCards() {
        for (int i = 0; i < board.getLocations().size(); i++) {
            Location l = board.getLocations().get(i);
            if (l.getName() != "Trailer" && l.getName()!= "Casting Office") {
                Set s = l.getSet();
                s.setCard(cardDeck.drawCard());
                s.setShotCounters(s.getMaxShots());
            }
        }
    }

    public boolean nextTurn(){
        if (turn < numPlayers-1)
            turn ++;
        else
            turn = 0;
        if(board.dayOver()) {
            return nextDay();
        }
        else {
            players[turn].beginTurn();
            return true;
        }
    }

    public boolean nextDay() {
        day++;
        if(day <= 3) {
            for (Player p : players) {
                p.fly(board.findLocation("Trailer"));
                p.setWorking(false);
            }
            for (Location l : board.getLocations()) {
                if (l.hasSet())
                    l.getSet().discardCard();
            }
            distributeCards();
            players[turn].beginTurn();
            return false;
        }
        else {
            endGame();
            return false;
        }
    }

    public void endGame(){
        Arrays.sort(players);
        Deadwood.endGame();
    }
}