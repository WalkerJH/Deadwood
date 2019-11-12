import java.util.ArrayList;

/**
 * Game locations; unused for now
 **/
public class Board {

    private static ArrayList<Location> locations;

    public Board(){
        locations = new ArrayList<>();
    }

    public static Location findLocation(String locationName) {
        Location found = null;
        for (Location l : locations) {
            if (l.getName().equalsIgnoreCase(locationName)) {
                found = l;
            }
        }
        return found;
    }

    public static void add (Location newLocation) {
        locations.add(newLocation);
    }

    public void resetBoard(){}
}