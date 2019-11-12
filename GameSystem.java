/**
 * Keeps track of game state and performs basic game operations
 */
import java.util.*;

public class GameSystem {
    private Player[] players;
    private int numPlayers;
    private Board board;
    private CardDeck cardDeck;
    private int numWrapped;
    private int day;
    private int turn;
    private boolean action;
    private final int NUM_DAYS = 3;
    public static final int[] RANK_UP_REQUIREMENTS_CASH = {4, 10, 18, 28, 40};
    public static int[] RANK_UP_REQUIREMENTS_CREDITS = {5, 10, 15, 20, 25};

    public GameSystem(int numPlayers){
        this.numPlayers = numPlayers;
    }

    public void setUpGame() throws Exception{
        day = 1;
        turn = 0;
        cardDeck = new CardDeck();
        ParseXML parser = new ParseXML("board.xml");
        board = parser.readBoardData();
        numWrapped = 0;
        action = true;

        //Fake testing initialization below
        Location flavortown = new Location("Flavortown");
        Role fieri = new StarringRole("Guy Fieri", "We’re takin’ you on a road rockin’ trip down to Flavortown, " +
                "where the gravitational force of bacon warps the laws of space and time.", 6);
        Role chef = new ExtraRole("Chef", "Thanks Mr. Fieri, very cool.", 1);
        ArrayList<Role> flavortownRoles = new ArrayList<>();
        flavortownRoles.add(fieri);
        flavortownRoles.add(chef);
        flavortown.setSet(new Set(1, flavortownRoles));
        board.add(flavortown);
        Location pit = new Location("Pit of Despair");
        board.add(pit);
        Location jonesTruckRental = new Location("Jones Truck Rental and Storage");
        board.add(jonesTruckRental);
        //End fake stuff

        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(Integer.toString(i + 1), board.findLocation("Trailer"));
        }
    }

    public Player getCurrentPlayer(){
        return players[turn];
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public boolean hasAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public Location findLocation(String locationName) {
        return board.findLocation(locationName);
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
        action = true;
        return turn;
    }

    public void nextDay(){}

    public void endGame(){}
}