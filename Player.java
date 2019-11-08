/**
 * Implements player behaviors and stores player info
**/
public class Player {
    private String name;
    private int rank;
    private int cash;
    private int credits;
    private int rehearsalTokens;
    private Role currentRole;
    private Location currentLocation;

    public Player(String name, Location location) {
        this.name = name;
        this.rank = 1;
        this.cash = 0;
        this.credits = 0;
        this.rehearsalTokens = 0;
        this.currentLocation = location;
    }

    public void printStatus() {
        System.out.printf("Player %s, Rank %d\n" +
                "Set: %s, Role: %s \n -----\n" +
                "%d dollars, %d credits\n",
                name, rank, currentLocation, currentRole, cash, credits);
    }

    public void payCash(int payment){}

    public void payCredits(int payment){}

    public void move(Location destination) {}

    public void takeRole(Role newRole) {}

    public void rehearse(){
        rehearsalTokens ++;
    }

    public boolean actAttempt() {
        //TODO: Fix this
        boolean success = (Dice.rollDice() > currentLocation.getCard().getBudget());
        Payout p = currentRole.payout(success);
        credits += p.getCredits();
        cash += p.getCash();
        return success;
    }

    public void rankUpWithCash(int cash, int targetRank) {}

    public void rankUpWithCredits(int credits, int targetRank) {}

    public int getVictoryPoints() {
        return -1;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return name;
    }
}
