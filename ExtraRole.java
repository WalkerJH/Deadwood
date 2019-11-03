/**
 * Performs functions of an extra (non-starring) role
**/
public class ExtraRole extends Role{

    public ExtraRole(String name, String desc, int rehearsalBonus, int rankRequirement) {
        super(name, desc, rehearsalBonus, rankRequirement);
    }

    public int payout() { return 0; }
}
