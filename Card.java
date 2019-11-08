/**
 * Represents a card in the Deadwood deck
**/
public class Card {
    private String name;
    private String desc;
    private StarringRole[] roles;
    private int budget;

    public Card(String name, String desc, StarringRole[] roles, int budget) {
        this.name = name;
        this.desc = desc;
        this.roles = roles;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public StarringRole[] getRoles() {
        return roles;
    }

    public int getBudget() {
        return budget;
    }
}
