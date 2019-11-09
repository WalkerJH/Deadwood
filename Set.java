/**
 * A location that contains roles
 **/
public class Set {
    private Card card;
    private Role[] localRoles;
    private int shotCounters;

    public Set(int shotCounters, Role[] localRoles) {
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
    }

    public Role[] getLocalRoles() {
        return localRoles;
    }

    public void wrap(){}

    public void removeShot(){}

    public Card getCard(){ return card; }

    public void discardCard(){}
}
