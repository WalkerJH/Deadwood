/**
 * Payout for a scene
 **/
public class Payout {
    private int tokens;
    private int cash;

    public Payout(int tokens, int cash) {
        this.tokens = tokens;
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public int getTokens() {
        return tokens;
    }
}
