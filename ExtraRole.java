/**
 * Performs functions of an extra (non-starring) role
**/
public class ExtraRole extends Role{

    private final static Payout SUCCESS = new Payout(true,1, 1);
    private final static Payout FAIL = new Payout(false,0,1);

    public ExtraRole(String name, String desc, int rankRequirement, Coordinates coordinates) {
        super(name, desc, rankRequirement, coordinates);
    }

    public Payout payout(boolean success) {
        if (success)
            return SUCCESS;
        else
            return FAIL;
    }
}
