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

    public String toString() {
        return String.format("%s - \"%s\" (Rank %d)", name, desc, rankRequirement);
    }

    public String getName() {
        return name;
    }

    public int getRankRequirement() {
        return rankRequirement;
    }

    public abstract Payout payout(boolean success);
}
