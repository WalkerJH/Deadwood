/**
 * Defines base fields and behaviors for a role (starring or extra)
**/
public abstract class Role {
    protected String name;
    protected String desc;
    protected int rehearsalBonus;
    protected int rankRequirement;

    public Role(String name, String desc, int rehearsalBonus, int rankRequirement) {
        this.name = name;
        this.desc = desc;
        this.rehearsalBonus = rehearsalBonus;
        this.rankRequirement = rankRequirement;
    }

    public abstract Payout payout(boolean success);
}
