import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Deadwood {

/**
 * Plays the game Deadwood
 * Code Authors: Kai Broach and Walker Herring, Western Washington University
 * Deadwood is © and ™ 1999, 2011 James Ernest and Cheapass Games: www.cheapass.com.
 **/
    private static GameSystem game;
    private static DeadwoodGUI gui;
    private static boolean gameRunning;
    public static int numPlayers;

    public static void main(String[] args) throws Exception{
        gui = new DeadwoodGUI();
        numPlayers = gui.promptNumPlayers();
        game = new GameSystem(numPlayers);
        game.setUpGame();
        gui.setUpGUI();
        gameRunning = true;
        gui.update(true);
    }

    public static Coordinates[] getOffRoleCoordinates (String locationName) {
        Location l = game.findLocation(locationName);
        return l.getOffRoleCoordinates();
    }

    public static Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public static ArrayList<Location> getMoveTargets() {
        return game.getCurrentPlayer().getCurrentLocation().getNeighbors();
    }

    public static ArrayList<Role> getAvailableRoles() {
        int rank = game.getCurrentPlayer().getRank();

        ArrayList<Role> roles = new ArrayList<Role>();
        roles.addAll(game.getCurrentPlayer().getCurrentLocation().getSet().getLocalRoles());
        ArrayList<Role> cardRoles = game.getCurrentPlayer().getCurrentLocation().getSet().getCard().getRoles();
        roles.addAll(cardRoles);
        Iterator<Role> itr = roles.iterator();
        while (itr.hasNext()) {
            Role r = itr.next();
            if ((r.getRankRequirement() > game.getCurrentPlayer().getRank()) || r.getFilled()) {
                itr.remove();
            }
        }
        return roles;
    }

    public static ArrayList<Integer> getAvailableRanks() {
        ArrayList<Integer> result = new ArrayList<>();
        int rank = game.getCurrentPlayer().getRank();
        for(int i = 2; i <=6; i ++) {
            if(i > rank)
                result.add(i);
        }
        return result;
    }

    public static int getTurn() {
        return game.getTurn();
    }

    public static void endTurn() {
        gui.update(game.nextTurn());
    }

    public static void endGame() {
        String[] playerResults = new String[game.getNumPlayers()];
        Player[] players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if(i == 0) {
                playerResults[i] = (String.format("Winner: %s with %d Victory Points\n", players[i].getName(), players[i].getVictoryPoints()));
            }
            else {
                playerResults[i] = (String.format("%s with %d Victory Points\n", players[i].getName(), players[i].getVictoryPoints()));
            }
        }
        gui.endGamePopUp(playerResults);
    }

    public static void rankUp() {
        int desiredRank = gui.promptRankUp();
        if(gui.promptPaymentMethod() == 0) {
            game.getCurrentPlayer().rankUpWithCash(desiredRank);
        }
        else{
            game.getCurrentPlayer().rankUpWithCredits(desiredRank);
        }
        gui.update(true);
    }

    public static void rehearse() {
        game.getCurrentPlayer().rehearse();
        gui.update(true);
    }

    public static void act() {
        game.getCurrentPlayer().actAttempt();
        gui.update(true);
    }

    public static void move(String locationName) {
        getCurrentPlayer().move(game.findLocation(locationName));
        gui.update(true);
    }

    public static void takeRole(Role chosenRole) {
        getCurrentPlayer().takeRole(chosenRole);
        gui.update(true);
    }

    public static boolean currentPlayerHasRole() {
        return game.getCurrentPlayer().isWorking();
    }

    public static Coordinates getCurrentRoleCoordinates() {
        Role role = game.getCurrentPlayer().getCurrentRole();
        if (role.toString().startsWith("\u2605")) {
            return role.getCoordinates().addXY(game.getCurrentPlayer().getCurrentLocation().getCardArea());
        }
        else {
            return role.getCoordinates();
        }
    }

    public static Coordinates[] getCurrentOffRoleCoordinates() {
        return game.getCurrentPlayer().getCurrentLocation().getOffRoleCoordinates();
    }

    public static ArrayList<Coordinates> getLocationAreas() {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        for(Location l : game.getBoard().getLocations()) {
            coordinates.add(l.getCardArea());
        }
        return coordinates;
    }

    public static ArrayList<Coordinates> getAllShotAreas() {
        ArrayList<Coordinates> result = new ArrayList<>();
        for(Location l : game.getBoard().getLocations()) {
            if(l.hasSet()) {
                result.addAll(l.getSet().getShotCounterSlots());
            }
        }
        return result;
    }

    public static int getCardId(Coordinates location) {
        for(Location l: game.getBoard().getLocations()) {
            if(l.hasSet() && l.getCardArea().equals(location)) {
                return l.getSet().getCard().getId();
            }
        }
        return -1;
    }

    public static void removeShot(Set set) {
        gui.removeShotCounter(set.getShotCounterSlots().get(set.getShotCounters() - 1));
    }
}