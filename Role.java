/**
 * Defines base fields and behaviors for a role (starring or extra)
**/
public abstract class Role {
    protected String name;
    protected String desc;
    protected int rankRequirement;

    public Role(String name, String desc, int rankRequirement) {
        this.name = name;
        this.desc = desc;
        this.rankRequirement = rankRequirement;
    }

    public abstract Payout payout(boolean success);
}
