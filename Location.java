/**
 * Represents a location on the game board
 */
import java.util.ArrayList;

public class Location {
    protected String name;
    protected ArrayList<Location> neighbors;

    public Card getCard() { return card; }

    protected Card card;

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, ArrayList<Location> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public String toString() { return name; };

    public void addNeighbor(Location neighbor) {
        neighbors.add(neighbor);
    }
}
