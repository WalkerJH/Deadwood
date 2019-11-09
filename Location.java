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
        neighbors = new ArrayList<Location>();
    }

    public String toString() { return name; }

    public void addNeighbors(Location ... args) {
        for (int i = 0; i < args.length; i++) {
            neighbors.add(args[i]);
        }
    }

    public void printNeighbors() {
        for (Location l : neighbors) {
            System.out.printf(" %s,", l);
        }
        System.out.println();
    }

    public boolean hasNeighbor(Location destination) {
        return (neighbors.contains(destination));
    }

    public Set getSet() { return set; }

    public String getName() { return name; }

    public void setSet(Set set) { this.set = set; }

}