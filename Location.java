/**
 * Represents a location on the game board
 */
import java.util.ArrayList;

public class Location {
    protected String name;
    protected ArrayList<Location> neighbors;

    public Location(String name, ArrayList<Location> neighbors) {
        this.neighbors = neighbors;
        this.name = name;
    }
}
