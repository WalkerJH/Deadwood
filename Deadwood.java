/**
 * Main class for deadwood game
 **/

import java.util.Scanner;
public class Deadwood {

    private static GameSystem game;
    private static Scanner input;

    public static void  printHelp() {
        System.out.println("player -> print player info\n" +
                "where -> print current location\n" +
                "move <new location> -> move to a new location\n" +
                "take role <role> -> take a role\n" +
                "act -> act in current role\n" +
                "end -> end active player's turn\n");
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
         switch (input.nextLine()) {
             case "help":
                printHelp();
                break;
             case "player":
                 game.getCurrentPlayer().printStatus();
                 break;
             case "players":
                 game.printAllPlayersStatus();
                 break;
             case "where":
                 System.out.println(game.getCurrentPlayer().getCurrentLocation());
                 break;
             //TODO: Move. Might have to reconfigure how input is handled.
             //TODO: Take role. See above
             case "act":
                 Payout p = game.getCurrentPlayer().actAttempt();
                 if (p.getSuccess())
                     System.out.printf("Success! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                 else
                     System.out.printf("Failure! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                 break;
             case "end":
                 game.nextTurn();
                 System.out.printf("Ending turn. Now it is player %s's turn\n", game.getCurrentPlayer());
         }
    }
}