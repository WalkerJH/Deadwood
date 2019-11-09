/**
 * Keeps track of game state and performs basic game operations
 */
import java.util.*;

public class GameSystem {
    private Player[] players;
    private int numPlayers;
    private ArrayList<Location> board;
    private CardDeck cardDeck;
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
        cardDeck = new CardDeck();
        board = new ArrayList<Location>();
        numWrapped = 0;
        Location trailer = new Location("Trailer");
        board.add(trailer);

        //TODO: initialize other locations. Fake testing locations below:
        Location flavortown = new Location("Flavortown");
        board.add(flavortown);
        Location pit = new Location("Pit of Despair");
        board.add(pit);
        Location jonesTruckRental = new Location("Jones Truck Rental and Storage");
        board.add(jonesTruckRental);
        trailer.addNeighbors(pit, flavortown);
        pit.addNeighbors(trailer);
        flavortown.addNeighbors(trailer, jonesTruckRental);
        jonesTruckRental.addNeighbors(flavortown);

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

    public Location findLocation(String locationName) {
        Location found = null;
        for (Location l : board) {
            if (l.getName().equalsIgnoreCase(locationName)) {
                found = l;
            }
        }
        return found;
    }

    public int getDay() {
        return day;
    }

    public void nextDay(){}

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public void endGame(){}
}