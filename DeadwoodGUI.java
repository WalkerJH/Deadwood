import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.*;

public class DeadwoodGUI {

/**
 * Plays the game Deadwood
 * Code Authors: Kai Broach and Walker Herring, Western Washington University
 * Deadwood is © and ™ 1999, 2011 James Ernest and Cheapass Games: www.cheapass.com.
 **/
    private static GameSystem game;
    private static Scanner input;
    private static boolean gameRunning;

    public static void  printHelp() {

    }

    public static void main(String[] args) throws Exception{
        input = new Scanner(System.in);
        try {
            //TODO: initialize gameSystem object
        }
        catch (InputMismatchException e) {
            throw new InputMismatchException("\nInvalid Input. Cancelling Game.");
        }
        game.setUpGame();
        //TODO: Run the game
    }

    public static void takeGameInput() {
        if (gameRunning) {
            //TODO: Run the game
        }
    }

    public static String getRankRequirements() {
        //String rankRequirements = "Cost per Rank:\n" +
        //        ("Rank\tCredits\t\tCash");
        //for(int i = 0; i < 5; i++) {
            //TODO: append ("%d\t\t%d\t\t\t%d\n", i + 2, game.RANK_UP_REQUIREMENTS_CREDITS[i], game.RANK_UP_REQUIREMENTS_CASH[i]);
        //}
    }

    public static void printRoles(Set s) {
        for (Role r : s.getLocalRoles()) {
            System.out.printf(" %s\n", r);
        }
        System.out.print(s.getCard());
        for (Role r : s.getCard().getRoles()) {
            System.out.printf(" %s\n", r);
        }
        System.out.println();
    }
}