/**
 * Main class for deadwood game
 **/

import java.util.Scanner;
public class Deadwood {

    private static GameSystem game;
    private static Scanner input;
    private static boolean gameRunning;

    public static void  printHelp() {
        System.out.print("player -> print player info\n" +
                "where -> print current and neighboring locations, roles\n" +
                "move -> move to a new location\n" +
                "role -> take a role\n" +
                "act -> act in current role\n" +
                "end -> end active player's turn\n");
    }

    public static void main(String[] args) throws Exception{
        input = new Scanner(System.in);
        System.out.println("Welcome to Deadwood. 2 or 3 Players?");
        game = new GameSystem(input.nextInt());
        game.setUpGame();
        System.out.printf("Initialized %d player game.\n--------------------\n",
                game.getNumPlayers());
        gameRunning = true;
        while (gameRunning) {
            takeGameInput();
        }
    }

    public static void takeGameInput() {
        if (gameRunning) {
            String in = input.nextLine();
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
                    System.out.println(currentPlayer.getCurrentLocation());
                    System.out.print("Neighboring Spaces:");
                    currentPlayer.getCurrentLocation().printNeighbors();
                    break;
                case "move":
                    if (game.hasAction()) {
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
                    Set s = currentPlayer.getCurrentLocation().getSet();
                    if (s != null) {
                        System.out.println("Which Role?");

                        s.printRoles();
                        System.out.println("Enter role name to take role or 'cancel' to cancel taking a role");
                        String roleName = input.nextLine();
                        if (!roleName.equalsIgnoreCase("cancel")) {
                            Role r = s.findRole(roleName);
                            if (r != null && currentPlayer.takeRole(r))
                                System.out.printf("You are now working on %s\n", r.getName());
                            else
                                System.out.printf("Can't take role %s (Rank %d)\n", r.getName(), r.getRankRequirement());
                        }
                    } else {
                        System.out.println("There are no roles here");
                    }
                    break;
                case "act":
                    if (game.hasAction()) {
                        Payout p = currentPlayer.actAttempt();
                        if (p.getSuccess())
                            System.out.printf("Success! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                        else
                            System.out.printf("Failure! + %d credits, + %d cash\n", p.getCredits(), p.getCash());
                        game.setAction(false);
                    }
                    break;
                case "rehearse":
                    if (currentPlayer.getCurrentRole() != null && game.hasAction()) {
                        System.out.printf("You rehearsed, you have + %d to acting rolls\n", currentPlayer.rehearse());
                        game.setAction(false);
                    } else {
                        System.out.println("You can't rehearse");
                    }
                    break;
                case "rank":
                    if (currentPlayer.getCurrentLocation().getName().equals("Casting Office") && game.hasAction()) {
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
                                    game.setAction(false);
                                } else {
                                    System.out.println("Rank Up Failed");
                                }
                                break;
                            case "credit":
                            case "credits":
                                if (currentPlayer.rankUpWithCredits(rank)) {
                                    System.out.println("Rank Increased to " + currentPlayer.getRank() +
                                            ". You now have " + currentPlayer.getCredits() + "credits");
                                    game.setAction(false);
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
                    if (game.getDay() > 3) {
                        game.endGame();
                        gameRunning = false;
                    } else {
                        game.nextTurn();
                        System.out.printf("Ending turn. Now it is player %s's turn\n", game.getCurrentPlayer());
                    }
                    break;

                //Cheats for development purposes
                case "cheat-end-game":
                    game.endGame();
                    break;
                case "cheat-fly":
                    String destination = input.nextLine();
                    Location l = game.findLocation(destination);
                    currentPlayer.fly(l);
                    break;
                case "cheat-riches":
                    currentPlayer.riches(10,10);
                    break;
                case "cheat-action":
                    game.setAction(true);
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
}