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

    public String toString() {
        return name;
    }

    public void printStatus() {
        System.out.printf("Player %s, Rank %d\n" +
                "Set: %s, Role: %s\n" +
                "%d dollars, %d credits\n",
                name, rank, currentLocation, currentRole, cash, credits);
    }

    public boolean move(Location destination) {
        if (currentLocation.hasNeighbor(destination)) {
            currentLocation = destination;
            return true;
        } else {
            return false;
        }
    }

    public boolean takeRole(Role newRole) {
        if(currentRole == null && rank >= newRole.getRankRequirement()) {
            this.currentRole = newRole;
            return true;
        } else {
            return false;
        }
    }

    public void rehearse(){
        rehearsalTokens ++;
    }

    public Payout actAttempt() {
        boolean success = (Dice.rollDice() + rehearsalTokens > currentLocation.getSet().getCard().getBudget());
        Payout p = currentRole.payout(success);
        credits += p.getCredits();
        cash += p.getCash();
        return p;
    }

    public void rankUpWithCash(int targetRank) {}

    public void rankUpWithCredits(int targetRank) {}

    public int getVictoryPoints() {
        return -1;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() { return currentLocation; }

    public Role getCurrentRole() { return currentRole; }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }
}
