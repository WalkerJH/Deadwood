/*******************
 *
 *******************/
public class GameSystem {
    private Player[] players;
    private Board board;
    private Card[] deck;
    private int day;
    private int turn;
    public final int NUM_DAYS = 3;
    public final int[] RANK_UP_REQUIREMENTS_CASH = {4, 10, 18, 28, 40};
    public final int[] RANK_UP_REQUIREMENTS_CREDITS = {5, 10, 15, 20, 25};

    public GameSystem(int numPlayers){}

    public void setUpGame(){}

    public int getCurrentPlayer(){
        return -1;
    }

    public void printPlayersStatus(){}

    public void nextTurn(){}

    public int getDay() {
        return day;
    }

    public void nextDay(){}

    public Card drawCard(){
        return null;
    }

    public void endGame(){}
}


