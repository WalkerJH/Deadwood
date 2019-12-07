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
        gui.update();
    }

    public static Coordinates getLocationArea(String locationName) {
        Location l = game.findLocation(locationName);
        return l.getCardArea();
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

    public static int getTurn() {
        return game.getTurn();
    }

    public static void endTurn() {
        game.nextTurn();
        gui.update();
    }

    public static void rankUp() {

        gui.update();
    }

    public static void rehearse() {
        game.getCurrentPlayer().rehearse();
        gui.update();
    }

    public static void act() {
        game.getCurrentPlayer().actAttempt();
        gui.update();
    }

    public static void move(String locationName) {
        getCurrentPlayer().move(game.findLocation(locationName));
        gui.update();
    }

    public static void takeRole(Role chosenRole) {
        getCurrentPlayer().takeRole(chosenRole);
        gui.update();
    }

    public static boolean currentPlayerHasRole() {
        return game.getCurrentPlayer().getCurrentRole() != null;
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
        gui.removeShotCounter(set.getShotCounterSlots().pop());
    }

    /*
    public static void takeGameInput() {
        if (gameRunning) {
            String in = input.nextLine().toLowerCase();
            Player currentPlayer = game.getCurrentPlayer();
            switch (in) {
                case "":
                    break;
                case "help":
                    printHelp();
                    break;
                case "player":
                    currentPlayer.printStatus();
                    break;
                case "players":
                    game.printAllPlayersStatus();
                    break;
                case "where":
                    for(Player p: game.getPlayers()) {
                        System.out.print(p + ": ");
                        System.out.println(p.getCurrentLocation());
                        System.out.println("Active Player: " + currentPlayer);
                    }
                    break;
                case "move":
                    if (currentPlayer.canMove()) {
                        System.out.print("Where?");
                        currentPlayer.getCurrentLocation().printNeighbors();
                        System.out.println("Enter location name to move there or 'cancel' to cancel move");
                        String destination = input.nextLine();
                        if (!destination.equalsIgnoreCase("cancel")) {
                            Location l = game.findLocation(destination);
                            if (l != null && currentPlayer.move(l)) {
                                System.out.printf("Moved to %s\n", destination);
                            } else {
                                System.out.printf("Can't move to %s\n", destination);
                            }
                        }
                    }
                    break;
                case "role":
                    if(currentPlayer.canTakeRole()) {
                        System.out.println("Which Role?");
                        Set s = currentPlayer.getCurrentLocation().getSet();
                        printRoles(s);
                        System.out.println("Enter role name to take role or 'cancel' to cancel taking a role");
                        String roleName = input.nextLine();
                        if (!roleName.equalsIgnoreCase("cancel")) {
                            Role r = s.findRole(roleName);
                            if (r != null && currentPlayer.takeRole(r))
                                System.out.printf("You are now working on %s\n", r.getName());
                            else
                                System.out.println("Invalid Role");
                        }
                    }
                    else {
                        System.out.println("Cannot take role right now");
                    }
                    break;
                case "act":
                    if (currentPlayer.canAct()) {
                        Payout p = currentPlayer.actAttempt();
                        if (p.getSuccess()) {
                            System.out.printf("Success! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                            System.out.println(currentPlayer.getCurrentLocation().getSet().getShotCounters() + " shots left on set");
                        }
                        else {
                            System.out.printf("Failure! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                        }
                    }
                    break;
                case "rehearse":
                    if (currentPlayer.canRehearse()) {
                        System.out.printf("You rehearsed, you have + %d to acting rolls\n", currentPlayer.rehearse());
                    } else {
                        System.out.println("You can't rehearse");
                    }
                    break;
                case "rank":
                    if (currentPlayer.canRankUp()) {
                        printRankRequirements();
                        System.out.println("Current Rank: " + currentPlayer.getRank());
                        System.out.print("Desired Rank: ");
                        int rank = input.nextInt();
                        input.nextLine();
                        System.out.println("Paying with cash or credits?");
                        String paymentMethod = input.nextLine();
                        switch (paymentMethod) {
                            case "cash":
                                if (currentPlayer.rankUpWithCash(rank)) {
                                    System.out.println("Rank Increased to " + currentPlayer.getRank() +
                                            ". You now have $ " + currentPlayer.getCash());
                                } else {
                                    System.out.println("Rank Up Failed");
                                }
                                break;
                            case "credit":
                            case "credits":
                                if (currentPlayer.rankUpWithCredits(rank)) {
                                    System.out.println("Rank Increased to " + currentPlayer.getRank() +
                                            ". You now have " + currentPlayer.getCredits() + "credits");
                                }
                                break;
                            default:
                                System.out.println("Invalid Input: Please enter either 'cash' or 'credits' as your payment method");
                                break;
                        }
                    } else {
                        System.out.println("Cannot rank up right now");
                    }
                    break;
                case "end":
                    if(game.nextTurn())
                        System.out.printf("Ending turn. Now it is %s's turn\n", game.getCurrentPlayer());
                    else
                        gameRunning = false;
                    break;

                //Cheats for development purposes
                case "cheat-end-game":
                    game.endGame();
                    gameRunning = false;
                    break;
                case "cheat-fly":
                    String destination = input.nextLine();
                    Location l = game.findLocation(destination);
                    currentPlayer.fly(l);
                    break;
                case "cheat-riches":
                    currentPlayer.riches(10,10);
                    break;
                case "cheat-rank":
                    currentPlayer.setRank(6);
                    break;
                //End cheats

                default:
                    System.out.println("Invalid input. Type help for list of commands");
                    break;
            }
        }
    }
    */
}