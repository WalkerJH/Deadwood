/**
 * Keeps track of game state and performs basic game operations
 */
import java.util.Stack;

public class GameSystem {
    private Player[] players;
    private int numPlayers;
    private Board board;
    private Deck deck;
    private int numWrapped;
    private int day;
    private int turn;
    public final int NUM_DAYS = 3;
    public final int[] RANK_UP_REQUIREMENTS_CASH = {4, 10, 18, 28, 40};
    public final int[] RANK_UP_REQUIREMENTS_CREDITS = {5, 10, 15, 20, 25};

    public GameSystem(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public void setUpGame(){
        day = 1;
        turn = 0;
        board = new Board();
        deck = new Deck();
        numWrapped = 0;
        Location trailer = new Location("Trailer");
        //TODO: initialize other locations
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(Integer.toString(i), trailer);
        }
    }

    public Player getCurrentPlayer(){
        return players[turn];
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void printAllPlayersStatus() {
        for (Player p : players) {
            p.printStatus();
        }
    }

    public int nextTurn(){
        if (turn < numPlayers-1)
            turn ++;
        else
            turn = 0;
        return turn;
    }

    public int getDay() {
        return day;
    }

    public void nextDay(){}

    public Deck getDeck() {
        return deck;
    }

    public void endGame(){}
}