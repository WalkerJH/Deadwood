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
    ArrayList<Coordinates> shotCounterSlots;


    public Set() {
        this.card = null;
        this.localRoles = new ArrayList<>();
        this.localActors = new ArrayList<>();
        this.shotCounterSlots = new ArrayList<>();
    }

    public Set(int shotCounters, ArrayList<Role> localRoles) {
        this.card = null;
        this.shotCounters = shotCounters;
        this.localRoles = localRoles;
        this.shotCounterSlots = new ArrayList<>(shotCounters);
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
        System.out.println("Wrapping Scene:");
        if(card.hasActor()) {
            ArrayList<Role> rolesOnCard = card.getRoles();
            ArrayList<Integer> rolls = new ArrayList<>();
            for(int i = 0; i < card.getBudget(); i++) {
                rolls.add(Dice.rollDice());
            }
            Collections.sort(rolls);
            Collections.reverse(rolls);
            int rollsUsed = 0; //rolls index
            int i = rolesOnCard.size() - 1; //roles index
            while (rollsUsed < rolls.size()) {
                payActor(rolls.get(rollsUsed), rolesOnCard.get(i));
                rollsUsed++;
                if(i > 0) {
                    i--;
                }
                else {
                    i = rolesOnCard.size() - 1;
                }
            }
            for(Player p : localActors) {
                for(Role r: localRoles){
                    if(p.getCurrentRole().equals(r))
                        p.pay(r.getRankRequirement());
                }
            }
        }
        for(Player p : localActors) {
            p.removeRole();
        }
        discardCard();
    }

    public void payActor(int cash, Role role) {
        if(role.getFilled()) {
            for(Player p : localActors) {
                if(p.getCurrentRole().equals(role)) {
                    p.pay(cash);
                }
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

    public void addShotCounterSlot(Coordinates coord) {
        shotCounterSlots.add(coord);
    }

    public ArrayList<Coordinates> getShotCounterSlots() {
        return shotCounterSlots;
    }

    public Card getCard(){ return card; }

    public void discardCard(){ card = null; }
}
