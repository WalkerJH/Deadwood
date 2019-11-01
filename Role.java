/*******************
 *
 *******************/
public abstract class Role {
    protected String name;
    protected String desc;
    protected int rehearsalBonus;
    protected int rankRequirement;

    public void rehearse(){}

    public abstract void payout();

    public abstract boolean actAttempt(int roll);


}
