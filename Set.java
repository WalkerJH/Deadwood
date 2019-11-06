/**
 * A location that contains roles
 **/
public class Set extends Location {
    private Card card;
    private Role[] localRoles;
    private int shotCounters;

    public Set(String name, int shotCounters, Role[] localRoles) {
        super(name);
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
    }

    public Set(String name) {
        super(name);
    }

    public Role[] getLocalRoles() {
        return localRoles;
    }

    public void wrap(){}

    public void removeShot(){}

    public void getNewCard(){}

    public void discardCard(){}
}
