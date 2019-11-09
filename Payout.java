/**
 * Payout for a scene
 **/
public class Payout {
    private boolean success;
    private int credits;
    private int cash;

    public Payout(boolean success, int tokens, int cash) {
        this.success = success;
        this.credits = tokens;
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public int getCredits() {
        return credits;
    }

    public boolean getSuccess() { return success; }
}
