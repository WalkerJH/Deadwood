/**
 * Represents a location on the game board
 */
import java.util.ArrayList;

public class Location {
    private String name;
    private ArrayList<Location> neighbors;
    private Set set;
    private Coordinates totalArea;
    private Coordinates[] offRoleCoordinates;

    public Location(String name) {
        this.name = name;
        neighbors = new ArrayList<Location>();
    }

    public String toString() {
        if(hasSet())
            return String.format("%s (Budget %d)", name, set.getCard().getBudget());
        else
            return name;
    }

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

    public ArrayList<Location> getNeighbors() {
        return neighbors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getSet() { return set; }

    public boolean hasSet() {
        return (set != null && set.getShotCounters() > 0);
    }

    public String getName() { return name; }

    public void setSet(Set set) { this.set = set; }

    public Coordinates getTotalArea() {
        return totalArea;
    }

    public void setCoordinates(Coordinates totalArea) {
        this.totalArea = totalArea;
        Coordinates slot1 = new Coordinates(totalArea.getX() + totalArea.getW() - 200,
                totalArea.getY() + totalArea.getH() - 55,
                DeadwoodGUI.PLAYER_TOKEN_SIZE, DeadwoodGUI.PLAYER_TOKEN_SIZE);
        Coordinates slot2 = new Coordinates(totalArea.getX() + totalArea.getW() - 145,
                totalArea.getY() + totalArea.getH() - 55,
                DeadwoodGUI.PLAYER_TOKEN_SIZE, DeadwoodGUI.PLAYER_TOKEN_SIZE);
        Coordinates slot3 = new Coordinates(totalArea.getX() + totalArea.getW() - 90,
                totalArea.getY() + totalArea.getH() - 55,
                DeadwoodGUI.PLAYER_TOKEN_SIZE, DeadwoodGUI.PLAYER_TOKEN_SIZE);
        offRoleCoordinates = new Coordinates[] {slot1, slot2, slot3};
    }

    public Coordinates[] getOffRoleCoordinates() {
        return offRoleCoordinates;
    }

    public void setOffRoleCoordinates(Coordinates[] coordinates) {
        this.offRoleCoordinates = coordinates;
    }
}