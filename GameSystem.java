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

    public void setUpGame(){}

    public int getCurrentPlayer(){
        return -1;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void printAllPlayersStatus() {
        for (Player p : players) {
            p.printStatus();
        }
    }

    public void nextTurn(){
        if (turn < numPlayers-1)
            turn ++;
        else
            turn = 0;
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