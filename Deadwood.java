/**
 * Main class for deadwood game
 **/

import java.util.Scanner;
public class Deadwood {

    private static GameSystem game;
    private static Scanner input;

    public static void  printHelp() {
        System.out.print("player -> print player info\n" +
                "where -> print current and neighboring locations, roles\n" +
                "move -> move to a new location\n" +
                "role -> take a role\n" +
                "act -> act in current role\n" +
                "end -> end active player's turn\n");
    }

    public static void printOptions() {
        System.out.print("You can: ");
        //TODO: Print out what the player can still do
    }

    public static void main(String[] args) {
        input = new Scanner(System.in);
        System.out.println("Welcome to Deadwood. 2 or 3 Players?");
        game = new GameSystem(input.nextInt());
        game.setUpGame();
        System.out.printf("Initialized %d player game.\n" +
                "--------------------\n" +
                "\n", game.getNumPlayers());
        while (true) {
            takeGameInput();
        }
    }

    public static void takeGameInput() {
        String in = input.nextLine();
        Player currentPlayer = game.getCurrentPlayer();
        switch (in) {
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
                System.out.println(currentPlayer.getCurrentLocation());
                System.out.print("Neighboring Spaces:");
                currentPlayer.getCurrentLocation().printNeighbors();
                break;
            case "move":
                if(game.hasAction()) {
                    System.out.print("Where?");
                    currentPlayer.getCurrentLocation().printNeighbors();
                    System.out.println("Enter location name to move there or 'cancel' to cancel move");
                    String destination = input.nextLine();
                    if (!destination.equalsIgnoreCase("cancel")) {
                        Location l = game.findLocation(destination);
                        if (l != null && currentPlayer.move(l) && currentPlayer.getCurrentRole() == null) {
                            game.setAction(false);
                            System.out.printf("Moved to %s\n", destination);
                        } else {
                            System.out.printf("Can't move to %s\n", destination);
                        }
                    }
                }
                break;
            case "role":
                System.out.println("Which Role?");
                Set s =  currentPlayer.getCurrentLocation().getSet();
                s.printRoles();
                System.out.println("Enter role name to take role or 'cancel' to cancel taking a role");
                String roleName = input.nextLine();
                if(!roleName.equalsIgnoreCase("cancel")) {
                    Role r = s.findRole(roleName);
                    if (r != null && currentPlayer.takeRole(r))
                        System.out.printf("You are now working on %s\n", r.getName());
                    else
                        System.out.printf("Can't take role %s (Rank %d)\n", r.getName(), r.getRankRequirement());
                }
                break;
            case "act":
                if(game.hasAction()) {
                    Payout p = currentPlayer.actAttempt();
                    if (p.getSuccess())
                        System.out.printf("Success! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                    else
                        System.out.printf("Failure! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                    game.setAction(false);
                }
                break;
            case "end":
                game.nextTurn();
                System.out.printf("Ending turn. Now it is player %s's turn\n", currentPlayer);
                break;
            default:
                System.out.println("Type help for list of commands");
                break;
        }
    }
}