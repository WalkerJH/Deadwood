/*******************
 * Defines base fields and behaviors for a role (starring or extra)
 *******************/
public abstract class Role {
    protected String name;
    protected String desc;
    protected int rehearsalBonus;
    protected int rankRequirement;
    protected Player currentActor;

    public void setCurrentActor(Player currentActor) {
        this.currentActor = currentActor;
    }

    public void rehearse(){}

    public abstract void payout();

    public abstract boolean actAttempt();
}
