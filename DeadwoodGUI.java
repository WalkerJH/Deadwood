import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * Displays GUI for a Deadwood game run through Deadwood.java
 * Main View-level class
 */
public class DeadwoodGUI {

    public static final Color GRAYBROWN = new Color(242, 235, 228);
    public static final Color LIGHTBROWN = new Color(246, 216, 158);
    public static final Color REDBROWN = new Color(209, 112, 48);
    public static final Color[] PLAYERCOLORS = {
            new Color(207, 20, 43),
            new Color(79, 156, 137),
            new Color(59, 73, 110)};
    public static final int PLAYER_TOKEN_SIZE = 50;

    private JFrame frame;
    private JLayeredPane pane;
    private DeadwoodJButton moveButton;
    private DeadwoodJButton actButton;
    private DeadwoodJButton rehearseButton;
    private DeadwoodJButton takeRoleButton;
    private DeadwoodJButton upRankButton;
    private DeadwoodJButton endTurnButton;
    private JLabel activePlayerInfo;
    private JLabel[] playerIcons;
    private ArrayList<CardJLabel> cards;
    private int boardWidth;
    private int boardHeight;

    public DeadwoodGUI() {
        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane = frame.getLayeredPane();

        JLabel bkgLabel = new JLabel();
        bkgLabel.setIcon(new ImageIcon(getImage("woodBackground.png")));
        bkgLabel.setBounds(0, 0, bkgLabel.getIcon().getIconWidth(), bkgLabel.getIcon().getIconHeight());

        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(new ImageIcon(getImage("board.jpg")));
        boardWidth = boardLabel.getIcon().getIconWidth();
        boardHeight = boardLabel.getIcon().getIconHeight();
        boardLabel.setBounds(0, 0, boardWidth, boardHeight);
        frame.setSize(boardWidth + 300, boardHeight + PLAYER_TOKEN_SIZE);

        pane.add(bkgLabel, -1);
        pane.add(boardLabel, 0);

        frame.setVisible(true);
    }

    public void setUpGUI() {
        setUpPlayers();
        setUpButtons();
        setUpPlayerInfo();
        setUpCards();
    }

    public void update() {
        updatePlayerInfo();
        updatePlayerIcons();
        updateButtons();
    }

    private void updatePlayerIcons() {
        for(int i = 0; i < Deadwood.numPlayers; i ++) {
            Coordinates c;
            if(i == Deadwood.getTurn()) {
                if(Deadwood.currentPlayerHasRole()) {
                    c = Deadwood.getCurrentRoleCoordinates();
                }
                else {
                    c = Deadwood.getCurrentOffRoleCoordinates()[i];
                }
                playerIcons[i].setBounds(c.getX(), c.getY(), PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE);
            }
        }
    }

    public BufferedImage getImage(String fileName) {
        BufferedImage image = null;
        File file = new File(fileName);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            displayException(e);
        }
        return image;
    }

   public void displayException(Exception ex) {
       JOptionPane.showMessageDialog(frame, ex,"Error", JOptionPane.WARNING_MESSAGE);
       System.exit(-1);
       frame.dispose();
       frame.setVisible(false);
   }

    public int promptNumPlayers() {
        int numPlayers;
        try {
            String[] options = {"Two Player Game", "Three Player Game"};
            JPanel panel = new JPanel();
            int opt = JOptionPane.showOptionDialog(null, "Welcome to Deadwood", "New Game",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, 0);
            numPlayers = opt + 2;
        }
        catch (Exception ex) {
            displayException(ex);
            return -1;
        }
        return numPlayers;
    }

    private void setUpButtons() {
        moveButton = new DeadwoodJButton("Move", boardWidth + PLAYER_TOKEN_SIZE, 10);
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Deadwood.move(promptMove());
            }
        });
        pane.add(moveButton, 1);


        takeRoleButton = new DeadwoodJButton("Take Role",boardWidth + PLAYER_TOKEN_SIZE, 120);
        takeRoleButton.setEnabled(false);
        takeRoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Role r = promptRole();
                if (r != null)
                    Deadwood.takeRole(r);
            }
        });
        pane.add(takeRoleButton, 1);

        actButton = new DeadwoodJButton("Act", boardWidth + PLAYER_TOKEN_SIZE, 230);
        actButton.setEnabled(false);
        actButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Deadwood.act();
            }
        });
        pane.add(actButton, 1);

        rehearseButton = new DeadwoodJButton("Rehearse", boardWidth + PLAYER_TOKEN_SIZE, 340);
        rehearseButton.setEnabled(false);
        rehearseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Deadwood.rehearse();
            }
        });
        pane.add(rehearseButton, 1);

        upRankButton = new DeadwoodJButton("Upgrade Rank", boardWidth + PLAYER_TOKEN_SIZE, 450);
        upRankButton.setEnabled(false);
        upRankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Deadwood.rankUp();
            }
        });
        pane.add(upRankButton, 1);

        endTurnButton = new DeadwoodJButton("End Turn", boardWidth + PLAYER_TOKEN_SIZE, 560);
        endTurnButton.setEnabled(true);
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Deadwood.endTurn();
            }
        });
        pane.add(endTurnButton, 1);
    }

    private void updateButtons() {
        moveButton.setEnabled(Deadwood.getCurrentPlayer().canMove());
        takeRoleButton.setEnabled(Deadwood.getCurrentPlayer().canTakeRole());
        actButton.setEnabled(Deadwood.getCurrentPlayer().canAct());
        rehearseButton.setEnabled(Deadwood.getCurrentPlayer().canRehearse());
        upRankButton.setEnabled(Deadwood.getCurrentPlayer().canRankUp());

    }

    private void setUpCards() {
        cards = new ArrayList<>();
        ArrayList<Coordinates> locations = Deadwood.getLocationAreas();
        for(int i = 0; i < locations.size(); i++) {
            Coordinates c = locations.get(i);
            CardJLabel cjl = new CardJLabel(i + 1, this, c);
            pane.add(cjl, 0);
            cards.add(cjl);
        }
    }

    private void setUpPlayerInfo() {
        activePlayerInfo = new JLabel();
        activePlayerInfo.setText(Deadwood.getCurrentPlayer().getStatus());
        activePlayerInfo.setHorizontalAlignment(SwingConstants.CENTER);
        activePlayerInfo.setBounds(boardWidth + 10, 700, 280, 160);
        activePlayerInfo.setForeground(Color.white);
        activePlayerInfo.setBackground(PLAYERCOLORS[0]);
        activePlayerInfo.setOpaque(true);
        pane.add(activePlayerInfo, 1);

    }

    private void updatePlayerInfo() {
        activePlayerInfo.setText(Deadwood.getCurrentPlayer().getStatus());
        activePlayerInfo.setHorizontalAlignment(SwingConstants.CENTER);
        activePlayerInfo.setBackground(PLAYERCOLORS[Deadwood.getCurrentPlayer().getNumber()]);
        activePlayerInfo.setOpaque(true);
    }

    public void setUpPlayers() {

        Coordinates[] start = Deadwood.getOffRoleCoordinates("Trailer");
        playerIcons = new JLabel[Deadwood.numPlayers];

        playerIcons[0] = new JLabel();
        Image image1 = getImage("player1.png");
        image1 = image1.getScaledInstance(PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
        playerIcons[0].setIcon(new ImageIcon(image1));
        playerIcons[0].setBounds(start[0].getX(), start[0].getY(), PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE);
        pane.add(playerIcons[0], new Integer (1));

        if(Deadwood.numPlayers == 2 || Deadwood.numPlayers == 3) {
            playerIcons[1] = new JLabel();
            Image image2 = getImage("player2.png");
            image2 = image2.getScaledInstance(PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
            playerIcons[1].setIcon(new ImageIcon(image2));
            playerIcons[1].setBounds(start[1].getX(), start[1].getY(), PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE);
            pane.add(playerIcons[1], new Integer(1));
        }
        else {
            displayException(new InputMismatchException("Invalid Number of Players"));
        }

        if(Deadwood.numPlayers == 3) {
            playerIcons[2] = new JLabel();
            Image image3 = getImage("player3.png");
            image3 = image3.getScaledInstance(PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
            playerIcons[2].setIcon(new ImageIcon(image3));
            playerIcons[2].setBounds(start[2].getX(), start[2].getY(), PLAYER_TOKEN_SIZE, PLAYER_TOKEN_SIZE);
            pane.add(playerIcons[2], new Integer(1));
        }
    }

    public String promptMove () {
        ArrayList<Location> targets = Deadwood.getMoveTargets();
        Object[] options = new Object[targets.size()];
        for(int i = 0; i < targets.size(); i++) {
            options[i] = targets.get(i).getName();
        }
        return (String)JOptionPane.showInputDialog(null, "Where would you like to move?",
                    "Move", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public Role promptRole () {
        ArrayList<Role> availableRoles = Deadwood.getAvailableRoles();
        Object[] options = new Object[availableRoles.size()];
        for (int i = 0; i < availableRoles.size(); i++) {
            options[i] = availableRoles.get(i);
        }
        return (Role)JOptionPane.showInputDialog(null, "Which role would you like to take?",
                "Move", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
}