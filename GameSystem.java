/**
 * Keeps track of game state and performs basic game operations
 */
import javax.xml.parsers.ParserConfigurationException;
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
        day = 1;
        turn = 0;
        cardDeck = new CardDeck();
        ParseXML parser = new ParseXML("board.xml");
        board = parser.readBoardData();
        parser.setDocument("cards.xml");
        cardDeck = parser.readCardData();
        cardDeck.shuffle();
        distributeCards();
        testingLocations();

        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player("Player " + Integer.toString(i + 1), board.findLocation("Trailer"));
        }
    }

    public Player getCurrentPlayer(){
        return players[turn];
    }

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

    public Location findLocation(String locationName) {
        return board.findLocation(locationName);
    }

    private void distributeCards() {
        for (int i = 0; i < board.getLocations().size(); i++) {
            Set s = board.getLocations().get(i).getSet();
            if (s != null)
                s.setCard(cardDeck.drawCard());
        }
    }

    public void printAllPlayersStatus() {
        for (Player p : players) {
            p.printStatus();
            System.out.println();
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
            }
            for (Location l : board.getLocations()) {
                Set s = l.getSet();
                if (s != null)
                    s.discardCard();
            }
            distributeCards();
            return true;
        }
        else {
            endGame();
            return false;
        }
    }

    public void endGame(){
        System.out.println("The game is over!\n");
        Arrays.sort(players);
        System.out.printf("Player %s is the winner!\n", players[0]);
        System.out.println("Leaderboard: ");
        for(int i = 0; i < players.length; i++)
            System.out.printf("%d. %s - %d points\n", i+1, players[i], players[i].getVictoryPoints());
    }

    private void testingLocations() {
        /*
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
        */
    }
}