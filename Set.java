import java.util.ArrayList;
import java.util.Collections;

/**
 * Keeps track of the role info for for a Location
 **/

public class Set {
    private Card card;
    private ArrayList<Role> localRoles;
    private ArrayList<Player> localActors;
    private int shotCounters;

    public Set() {
        this.card = null;
        this.localRoles = new ArrayList<>();
        this.localActors = new ArrayList<>();
    }

    public Set(int shotCounters, ArrayList<Role> localRoles) {
        this.card = null;
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
    }

    public void setShotCounters(int shotCounters) {
        this.shotCounters = shotCounters;
    }

    public int getShotCounters() {
        return shotCounters;
    }

    public ArrayList<Role> getLocalRoles() {
        return localRoles;
    }

    public void addRole(Role r) {
        localRoles.add(r);
    }

    public void addActor(Player p) {
        localActors.add(p);
    }

    public void wrap() {
        ArrayList<StarringRole> rolesOnCard = card.getRoles();
        if(card.hasActor()) {
            ArrayList<Integer> rolls = new ArrayList<>(card.getBudget());
            for(int i = 0; i < rolls.size(); i++) {
                rolls.add(Dice.rollDice());
            }
            Collections.sort(rolls);
            Collections.reverse(rolls);
            int rollsUsed = 0;
            for(int i = rolesOnCard.size() - 1; i >= 0; i++) {
                while (rollsUsed <= rolls.size()) {
                    payLocalActor(rolls.get(rollsUsed), rolesOnCard.get(i));
                }
            }
        }
        discardCard();
    }

    public void payLocalActor(int cash, Role role) {
        if(role.getFilled()) {
            for(Player p : localActors) {
                if(p.getCurrentRole().equals(role))
                    p.pay(cash);
            }
        }
    }

    public void setCard(Card card) { this.card = card; }

    public Role findRole(String roleName) {
        for (Role r : localRoles) {
            if (r.getName().equalsIgnoreCase(roleName)) {
                return r;
            }
        }
        for (Role r : card.getRoles()) {
            if (r.getName().equalsIgnoreCase(roleName)) {
                return r;
            }
        }
        return null;
    }

    public void removeShot(){
        shotCounters --;
        if(shotCounters == 0) {
            wrap();
        }
    }

    public Card getCard(){ return card; }

    public void discardCard(){ card = null; }
}
