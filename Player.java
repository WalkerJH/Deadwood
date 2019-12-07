/**
 * Implements player behaviors and stores player info
**/
public class Player implements Comparable<Player> {
    private String name;
    private int number;

    private int rank;
    private int cash;
    private int credits;
    private int rehearsalTokens;
    private Role currentRole;
    private Location currentLocation;

    private boolean hasAction;
    private boolean working;

    public Player(int playerNumber, Location location) {
        this.number = playerNumber;
        this.name = "Player " + Integer.toString(playerNumber + 1);
        this.rank = 6;
        this.cash = 0;
        this.credits = 0;
        this.rehearsalTokens = 6;
        this.currentLocation = location;
        this.hasAction = true;
        this.working = false;
    }

    public String toString() { return name; }

    public int getNumber() { return number; }

    public String getStatus() {
        String s = String.format("<html>%s, Rank %d<br>" +
            "Set: %s<br>Role: %s<br>" +
            "%d dollars, %d credits<br>%d rehearsal tokens<html>",
            name, rank, currentLocation.getName(), getCurrentRole(), cash, credits, rehearsalTokens);
        return s;
    }

    public void beginTurn() {
        hasAction = true;
    }

    //move without restrictions
    public void fly(Location destination) {
        currentLocation = destination;
    }

    public boolean move(Location destination) {
        if (currentLocation.hasNeighbor(destination)) {
            currentLocation = destination;
            this.hasAction = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean takeRole(Role newRole) {
        if(!newRole.getFilled() && rank >= newRole.getRankRequirement()) {
            this.currentRole = newRole;
            this.working = true;
            currentRole.setFilled(true);
            this.hasAction = false;
            currentLocation.getSet().addActor(this);
            return true;
        } else {
            return false;
        }
    }

    public int rehearse(){
        rehearsalTokens ++;
        this.hasAction = false;
        return rehearsalTokens;
    }

    public Payout actAttempt() {
        boolean success = ((Dice.rollDice() + rehearsalTokens) > currentLocation.getSet().getCard().getBudget());
        Payout p = currentRole.payout(success);
        credits += p.getCredits();
        cash += p.getCash();
        if(success && currentLocation.getSet().getShotCounters() > 0) {
            Deadwood.removeShot(currentLocation.getSet());
            currentLocation.getSet().removeShot();
        }
        this.hasAction = false;
        return p;
    }

    public boolean rankUpWithCash(int targetRank) {
        int cost = GameSystem.RANK_UP_REQUIREMENTS_CASH[targetRank - 2];
        if(cash >= cost) {
            cash -= cost;
            rank = targetRank;
            this.hasAction = false;
            return true;
        }

        return false;
    }

    public boolean rankUpWithCredits(int targetRank) {
        int cost = GameSystem.RANK_UP_REQUIREMENTS_CREDITS[targetRank - 2];
        if(credits >= cost) {
            credits -= cost;
            rank = targetRank;
            this.hasAction = false;
            return true;
        }
        return false;
    }

    public int compareTo(Player o) {
        return o.getVictoryPoints() - this.getVictoryPoints();
    }

    public int getVictoryPoints() {
        int vp = cash + credits + rank*5;
        return vp;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location getCurrentLocation() { return currentLocation; }

    public Role getCurrentRole() {
        if(working) {
            return currentRole;
        }
        else {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public int getCash() {
        return cash;
    }

    public void pay(int payment) {
        cash += payment;
    }

    public void removeRole() {
        working = false;
        currentRole.setFilled(false);
    }

    public int getCredits() { return credits; }

    public boolean canMove() {
        return (!working && hasAction);
    }

    public boolean canAct() {
        return (working && hasAction);
    }

    public boolean canTakeRole() {
        return (!working && currentLocation.hasSet() && Deadwood.getAvailableRoles().size() > 0);
    }

    public boolean canRehearse() {
        return (working && hasAction);
    }

    public boolean canRankUp() {
        return (currentLocation.getName().equals("Casting Office") && rank < 6);
    }

    //Cheat code methods
    public void riches(int cheatCash, int cheatCredits) {
        cash += cheatCash;
        credits += cheatCredits;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public boolean isWorking() {
        return working;
    }
}
