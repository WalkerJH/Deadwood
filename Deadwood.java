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
        System.out.printf("Beginning %d player game...\n", game.getNumPlayers());
        game.setUpGame();
        printHelp();
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
                System.out.print("Where?");
                currentPlayer.getCurrentLocation().printNeighbors();
                System.out.println("Enter location or 'cancel' to cancel move");
                String destination = input.nextLine();
                if (!destination.equalsIgnoreCase("cancel")) {
                    Location l = game.findLocation(destination);
                    if (l != null && currentPlayer.move(l))
                        System.out.printf("Moved to %s\n", destination);
                    else
                        System.out.printf("Can't move to %s\n", destination);
                }
                break;
            case "role":
                System.out.print("Which Role?");
                break;
            case "act":
                Payout p = currentPlayer.actAttempt();
                if (p.getSuccess())
                    System.out.printf("Success! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                else
                    System.out.printf("Failure! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                break;
            case "end":
                game.nextTurn();
                System.out.printf("Ending turn. Now it is player %s's turn\n", currentPlayer);
                break;
        }
    }
}