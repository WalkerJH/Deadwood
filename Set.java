import java.util.ArrayList;

/**
 * A location that contains roles
 **/

public class Set {
    private Card card;
    private ArrayList<Role> localRoles;
    private int shotCounters;

    public Set(int shotCounters, ArrayList<Role> localRoles) {
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
    }

    public ArrayList<Role> getLocalRoles() {
        return localRoles;
    }

    public Role findRole(String roleName) {
        for (Role r : localRoles) {
            if (r.getName().equalsIgnoreCase(roleName)) {
                return r;
            }
        }
        return null;
    }

    public void printRoles() {
        for (Role r : localRoles) {
            System.out.printf(" %s\n", r);
        }
        System.out.println();
    }

    public void wrap(){}

    public void removeShot(){}

    public Card getCard(){ return card; }

    public void discardCard(){}
}
