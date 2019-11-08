/**
 * Represents a location on the game board
 */
import java.util.ArrayList;

public class Location {
    private String name;
    private ArrayList<Location> neighbors;
    private Set set;

    public Location(String name) {
        this.name = name;
    }

    public Location(String name, ArrayList<Location> neighbors) {
        this.name = name;
        this.neighbors = neighbors;
    }

    public String toString() { return name; }

    public void addNeighbor(Location neighbor) {
        neighbors.add(neighbor);
    }

    public Set getSet() { return set; }

    public void setSet(Set set) { this.set = set; }
}
