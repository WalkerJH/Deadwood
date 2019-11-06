/**
 * Performs functions of an extra (non-starring) role
**/
public class ExtraRole extends Role{

    public ExtraRole(String name, String desc, int rehearsalBonus, int rankRequirement) {
        super(name, desc, rehearsalBonus, rankRequirement);
    }

    public Payout payout(boolean success) {
        return new Payout(0,1);
    }
}
