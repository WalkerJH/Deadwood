import java.util.ArrayList;

/**
 * Stores game locations
 **/
public class Board {

    private ArrayList<Location> locations;

    public Board() {
        locations = new ArrayList<>();
    }

    public Location findLocation(String locationName) {
        Location found = null;
        for (Location l : locations) {
            if (l.getName().equalsIgnoreCase(locationName)) {
                found = l;
            }
        }
        return found;
    }

    public void add (Location newLocation) {
        locations.add(newLocation);
    }

    public ArrayList<Location> getLocations() { return locations; }

    public boolean dayOver() {
        int numWrapped = 0;
        for(Location l: locations) {
            if(l.hasSet() && l.getSet().getShotCounters() == 0) {
                numWrapped++;
            }
        }
        return (numWrapped == 9);
    }
}