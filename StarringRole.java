/**
 * Performs functions of an starring (non-extra) role
**/
public class StarringRole extends Role {

    public StarringRole(String name, String desc, int rehearsalBonus, int rankRequirement) {
        super(name, desc, rehearsalBonus, rankRequirement);
    }

    public Payout payout(boolean success) {
        if (success)
            return new Payout(2, 0);
        else
            return new Payout(0,0);
    }
}
