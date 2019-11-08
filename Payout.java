/**
 * Payout for a scene
 **/
public class Payout {
    private int credits;
    private int cash;

    public Payout(int tokens, int cash) {
        this.credits = tokens;
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public int getCredits() {
        return credits;
    }
}
