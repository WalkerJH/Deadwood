/*******************
 *
 *******************/
public class GameSystem {
    private Player[] players;
    private Board board;
    private Card[] deck;
    private int day;
    private int turn;

    public GameSystem(int numPlayers){}

    public void setUpGame(){}

    public int getCurrentPlayer(){
        return -1;
    }

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


