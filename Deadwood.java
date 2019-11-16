import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Deadwood {

/**
 * Plays the game Deadwood
 * Code Authors: Kai Broach and Walker Herring, Western Washington University
 * Deadwood is © and ™ 1999, 2011 James Ernest and Cheapass Games: www.cheapass.com.
 **/
    private static GameSystem game;
    private static Scanner input;
    private static boolean gameRunning;

    public static void  printHelp() {
        System.out.print("player -> print player info\n" +
                "players -> print player info for all players\n" +
                "where -> print locations of all players\n" +
                "move -> move to a new location\n" +
                "role -> take a role\n" +
                "act -> act in current role\n" +
                "rehearse -> rehearse your role" +
                "rank -> pay cash or credits to increase your rank\n" +
                "end -> end active player's turn\n" +
                "help -> print this list of options\n");
    }

    public static void main(String[] args) throws Exception{
        input = new Scanner(System.in);
        System.out.println("Welcome to Deadwood. 2 or 3 Players?");
        try {
            game = new GameSystem(input.nextInt());
        }
        catch (InputMismatchException e) {
            throw new InputMismatchException("\nInvalid Input. Cancelling Game.");
        }
        game.setUpGame();
        System.out.printf("Initialized %d player game.\n--------------------\n",
                game.getNumPlayers());
        gameRunning = true;
        printHelp();
        while (gameRunning) {
            takeGameInput();
        }
    }

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

    public static void printRankRequirements() {
        System.out.println("Cost per Rank:");
        System.out.println("Rank\tCredits\t\tCash");
        for(int i = 0; i < 5; i++) {
            System.out.printf("%d\t\t%d\t\t\t%d\n", i + 2, game.RANK_UP_REQUIREMENTS_CREDITS[i], game.RANK_UP_REQUIREMENTS_CASH[i]);
        }
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