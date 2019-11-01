/*******************
 * Implements player behaviors and stores player info
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

    public void payCash(int payment){};

    public void payCredits(int payment){};

    public void move(Location destination) {}

    public void takeRole(Role newRole) {}

    public void rankUp(int cash, int credits) {}

    public int getVictoryPoints() {
        return -1;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getName() {
        return name;
    }
}
