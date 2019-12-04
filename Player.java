/**
 * Implements player behaviors and stores player info
**/
public class Player implements Comparable<Player> {
    private String name;
    private int rank;
    private int cash;
    private int credits;
    private int rehearsalTokens;
    private Role currentRole;
    private Location currentLocation;

    private boolean hasAction;
    private boolean working;

    public Player(String name, Location location) {
        this.name = name;
        this.rank = 1;
        this.cash = 0;
        this.credits = 0;
        this.rehearsalTokens = 0;
        this.currentLocation = location;
        this.hasAction = true;
        this.working = false;
    }

    public String toString() {
        return name;
    }

    public String getStatus() {
        String s = String.format("<html>%s, Rank %d<br>" +
                "Set: %s, Role: %s<br>" +
                "%d dollars, %d credits<br><html>",
                name, rank, currentLocation, currentRole, cash, credits);
        return s;
    }

    public void beginTurn() {
        hasAction = true;
    }

    public void fly(Location destination) {
        currentLocation = destination;
    } //move without restrictions

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
        if(success && currentLocation.getSet().getShotCounters() > 0)
            currentLocation.getSet().removeShot();
        this.hasAction = false;
        return p;
    }

    public boolean rankUpWithCash(int targetRank) {
        int diff = targetRank - rank;
        if(diff > 0 && rank != 6) {
            int cost = 0;
            for(int i = 0; i < diff; i++) {
                cost += GameSystem.RANK_UP_REQUIREMENTS_CASH[rank - 1 + i];
            }
            if(cash >= cost) {
                cash -= cost;
                rank = targetRank;
                this.hasAction = false;
                return true;
            }
        }
        return false;
    }

    public boolean rankUpWithCredits(int targetRank) {
        int diff = targetRank - rank;
        if(diff > 0 && rank != 6) {
            int cost = 0;
            for(int i = 0; i < diff; i++) {
                cost += GameSystem.RANK_UP_REQUIREMENTS_CREDITS[rank - 1 + i];
            }
            if(credits >= cost) {
                credits -= cost;
                rank = targetRank;
                this.hasAction = false;
                return true;
            }
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

    public Role getCurrentRole() { return currentRole; }

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
        System.out.println(this + " paid $" + payment);
        cash += payment;
    }

    public int getCredits() { return credits; }

    public boolean canMove() {
        return (!working && hasAction);
    }

    public boolean canAct() {
        return (working && hasAction);
    }

    public boolean canTakeRole() {
        return (!working && currentRole == null && currentLocation.hasSet());
    }

    public boolean canRehearse() {
        return (working && hasAction);
    }

    public boolean canRankUp() {
        return (hasAction && currentLocation.getName().equals("Casting Office"));
    }

    //Cheat code methods
    public void riches(int cheatCash, int cheatCredits) {
        cash += cheatCash;
        credits += cheatCredits;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
