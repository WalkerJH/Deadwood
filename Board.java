import java.util.ArrayList;

/**
 * Game locations
 **/
public class Board {

    private ArrayList<Location> locations;

    public Board(){
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

    public void resetBoard(){
        locations.clear();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}