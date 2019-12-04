import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.InputMismatchException;

/**
 * Displays GUI for a Deadwood game run through Deadwood.java
 * Main View-level class
 */
public class DeadwoodGUI {

    private JFrame frame;
    private JLayeredPane pane;
    private MouseListener listener;
    private JButton moveButton;
    private JButton actButton;
    private JButton rehearseButton;
    private JButton takeRoleButton;
    private JButton upRankButton;
    private JLabel activePlayerInfo;
    private JLabel player1Icon;
    private JLabel player2Icon;
    private JLabel player3Icon;
    private int boardWidth;
    private int boardHeight;

    public DeadwoodGUI() {
        frame = new JFrame("Deadwood");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane = frame.getLayeredPane();
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(new ImageIcon(getImage("board.jpg")));
        boardWidth = boardLabel.getIcon().getIconWidth();
        boardHeight = boardLabel.getIcon().getIconHeight();
        boardLabel.setBounds(0, 0, boardWidth, boardHeight);
        frame.setSize(boardWidth + 300, boardHeight + Deadwood.PLAYER_TOKEN_SIZE);
        pane.add(boardLabel, new Integer(0));

        frame.setVisible(true);
    }

    public void setUpGUI() {
        setUpButtons();
        setUpPlayerInfo();
    }

    public void update() {
        updatePlayerInfo();
        updateButtons();
    }

    public BufferedImage getImage(String fileName) {
        BufferedImage image = null;
        File file = new File(fileName);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter number of Players",
                    "New Game", JOptionPane.QUESTION_MESSAGE));
        }
        catch (Exception ex) {
            displayException(ex);
            return -1;
        }
    }

    private void setUpButtons() {
        moveButton = new JButton("Move");
        moveButton.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 10, 200, Deadwood.PLAYER_TOKEN_SIZE);
        pane.add(moveButton);

        takeRoleButton = new JButton("Take Role");
        takeRoleButton.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 120, 200, Deadwood.PLAYER_TOKEN_SIZE);
        takeRoleButton.setVisible(false);
        pane.add(takeRoleButton);

        actButton = new JButton("Act");
        actButton.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 120, 200, Deadwood.PLAYER_TOKEN_SIZE);
        actButton.setVisible(false);
        pane.add(actButton);

        rehearseButton = new JButton("Rehearse");
        rehearseButton.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 230, 200, Deadwood.PLAYER_TOKEN_SIZE);
        rehearseButton.setVisible(false);
        pane.add(rehearseButton);

        upRankButton = new JButton("Upgrade Rank");
        rehearseButton.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 340, 200, Deadwood.PLAYER_TOKEN_SIZE);
        rehearseButton.setVisible(false);
        pane.add(rehearseButton);
    }

    private void updateButtons() {
        moveButton.setVisible(Deadwood.getCurrentPlayer().canMove());
        takeRoleButton.setVisible(Deadwood.getCurrentPlayer().canTakeRole());
        actButton.setVisible(Deadwood.getCurrentPlayer().canAct());
        rehearseButton.setVisible(Deadwood.getCurrentPlayer().canRehearse());
        upRankButton.setVisible(Deadwood.getCurrentPlayer().canRankUp());
    }

    private void setUpPlayerInfo() {
        activePlayerInfo = new JLabel();
        activePlayerInfo.setText(Deadwood.getCurrentPlayer().getStatus());
        activePlayerInfo.setBounds(boardWidth + Deadwood.PLAYER_TOKEN_SIZE, 400, 200, 60);
        activePlayerInfo.setVisible(true);
        pane.add(activePlayerInfo);
    }

    private void updatePlayerInfo() {
        activePlayerInfo.setText(Deadwood.getCurrentPlayer().getStatus());
        activePlayerInfo.setVisible(true);
    }

    public void setUpPlayers() {

        Coordinates[] start = Deadwood.getOffRoleCoordinates("Trailer");

        player1Icon = new JLabel();
        Image image1 = getImage("player1.png");
        image1 = image1.getScaledInstance(Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
        player1Icon.setIcon(new ImageIcon(image1));
        player1Icon.setBounds(start[0].getX(), start[0].getY(), Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE);
        start[0].setOccupied(true);
        pane.add(player1Icon, new Integer (1));

        if(Deadwood.numPlayers == 2 || Deadwood.numPlayers == 3) {
            player2Icon = new JLabel();
            Image image2 = getImage("player2.png");
            image2 = image2.getScaledInstance(Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
            player2Icon.setIcon(new ImageIcon(image2));
            player2Icon.setBounds(start[1].getX(), start[1].getY(), Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE);
            start[1].setOccupied(true);
            pane.add(player2Icon, new Integer(1));
        }
        else {
            displayException(new InputMismatchException("Invalid Number of Players"));
        }

        if(Deadwood.numPlayers == 3) {
            player3Icon = new JLabel();
            Image image3 = getImage("player3.png");
            image3 = image3.getScaledInstance(Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE, Image.SCALE_DEFAULT);
            player3Icon.setIcon(new ImageIcon(image3));
            player3Icon.setBounds(start[2].getX(), start[2].getY(), Deadwood.PLAYER_TOKEN_SIZE, Deadwood.PLAYER_TOKEN_SIZE);
            start[2].setOccupied(true);
            pane.add(player3Icon, new Integer(1));
        }
    }
}