/**
 * Performs functions of an starring (non-extra) role
**/
public class StarringRole extends Role {

    private final static Payout SUCCESS = new Payout(true, 2, 0);
    private final static Payout FAIL = new Payout(false,0,0);

    public StarringRole(String name, String desc, int rankRequirement, Coordinates coordinates) {
        super(name, desc, rankRequirement, coordinates);
    }

    public String toString() {
        return "\u2605 " + super.toString();
    }

    public Payout payout(boolean success) {
        if (success)
            return SUCCESS;
        else
            return FAIL;
    }
}
