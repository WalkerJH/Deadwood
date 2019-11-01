/**
 * Represents a location on the game board
 */

public class Location {
    protected String name;
    protected ArrayList neighbors<Location>;

    public Location(String name, ArrayList<Location> neighbors) {
        this.neighbors = neighbors;
        this.name = name;
    }
}
