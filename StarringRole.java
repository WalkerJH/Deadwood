/**
 * Performs functions of an starring (non-extra) role
**/
public class StarringRole extends Role {

    public StarringRole(String name, String desc, int rehearsalBonus, int rankRequirement) {
        super(name, desc, rehearsalBonus, rankRequirement);
    }

    public int payout() { return 0; }
}
