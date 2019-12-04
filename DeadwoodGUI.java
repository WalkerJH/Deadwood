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
        frame.setSize(boardWidth + 300, boardHeight + 50);
        pane.add(boardLabel, new Integer(0));

        setUpButtons();

        frame.setVisible(true);
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
        moveButton.setBounds(boardWidth + 50, 10, 200, 50);
        pane.add(moveButton);

        takeRoleButton = new JButton("Take Role");
        takeRoleButton.setBounds(boardWidth + 50, 120, 200, 50);
        takeRoleButton.setVisible(false);
        pane.add(takeRoleButton);

        actButton = new JButton("Act");
        actButton.setBounds(boardWidth + 50, 120, 200, 50);
        actButton.setVisible(false);
        pane.add(actButton);

        rehearseButton = new JButton("Rehearse");
        rehearseButton.setBounds(boardWidth + 50, 230, 200, 50);
        rehearseButton.setVisible(false);
        pane.add(rehearseButton);
    }

    public void setUpPlayers() {
        player1Icon = new JLabel();
        Image image1 = getImage("player1.png");
        image1 = image1.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        player1Icon.setIcon(new ImageIcon(image1));
        Coordinates start = Deadwood.getLocation("Trailer");
        player1Icon.setBounds(start.getX() + 5, start.getY() + 5, 50, 50);
        pane.add(player1Icon, new Integer (1));

        if(Deadwood.numPlayers == 2 || Deadwood.numPlayers == 3) {
            player2Icon = new JLabel();
            Image image2 = getImage("player2.png");
            image2 = image2.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            player2Icon.setIcon(new ImageIcon(image2));
            player2Icon.setBounds(start.getX() + 60, start.getY() + 5, 50, 50);
            pane.add(player2Icon, new Integer(1));
        }
        else {
            displayException(new InputMismatchException("Invalid Number of Players"));
        }

        if(Deadwood.numPlayers == 3) {
            player3Icon = new JLabel();
            Image image3 = getImage("player3.png");
            image3 = image3.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            player3Icon.setIcon(new ImageIcon(image3));
            player3Icon.setBounds(start.getX() + 115, start.getY() + 5, 50, 50);
            pane.add(player3Icon, new Integer(1));
        }
    }
}