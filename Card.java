import java.util.ArrayList;
/**
 * Represents a card in the Deadwood deck
**/
public class Card {
    private String name;
    private String desc;
    private ArrayList<Role> roles;
    private int budget;
    private int id;

    public Card(String name, String desc, ArrayList<Role> roles, int budget, int id) {
        this.name = name;
        this.desc = desc;
        this.roles = roles;
        this.budget = budget;
        this.id = id;
    }

    public String toString() {
        return String.format("%s - \"%s\"\n", name, desc);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public boolean hasActor() {
        for (Role r: roles) {
            if(r.getFilled())
                return true;
        }
        return false;
    }

    public int getBudget() {
        return budget;
    }

    public int getId() {
        return id;
    }
}
