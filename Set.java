import java.util.ArrayList;

/**
 * Keeps track of the role info for for a Location
 **/

public class Set {
    private Card card;
    private ArrayList<Role> localRoles;
    private int shotCounters;

    public Set() {
        this.localRoles = new ArrayList<>();
    }

    public Set(int shotCounters, ArrayList<Role> localRoles) {
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
    }

    public void setShotCounters(int shotCounters) {
        this.shotCounters = shotCounters;
    }

    public ArrayList<Role> getLocalRoles() {
        return localRoles;
    }

    public void addRole(Role r) {
        localRoles.add(r);
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

    public void wrap(){
        //TODO
    }

    public void removeShot(){
        //TODO
    }

    public Card getCard(){ return card; }

    public void setCard(Card card) {
        this.card = card;
    }

    public void discardCard(){
        //TODO
    }
}
