/*******************
 *
 *******************/
public class Player {
    private String name;
    private int cash;
    private int credits;
    private int rank;
    private Role currentRole;
    private Location currentLocation;

    public Player(String name) {
        this.name = name;
    }

    public String printStatus() {
        return null;
    }

    public void takeRole(Role newRole) {}

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return name;
    }
}
