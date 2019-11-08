/**
 * Performs functions of an extra (non-starring) role
**/
public class ExtraRole extends Role{

    public ExtraRole(String name, String desc, int rankRequirement) {
        super(name, desc, rankRequirement);
    }

    public Payout payout(boolean success) {
        if (success)
            return new Payout(1, 1);
        else
            return new Payout(0,1);
    }
}
