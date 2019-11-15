import java.util.ArrayList;
/**
 * Represents a card in the Deadwood deck
**/
public class Card {
    private String name;
    private String desc;
    private ArrayList<StarringRole> roles;
    private int budget;

    public Card(String name, String desc, ArrayList<StarringRole> roles, int budget) {
        this.name = name;
        this.desc = desc;
        this.roles = roles;
        this.budget = budget;
    }

    public String toString() {
        return String.format("%s\n%s\nBudget: %d");
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public ArrayList<StarringRole> getRoles() {
        return roles;
    }

    public int getBudget() {
        return budget;
    }
}
