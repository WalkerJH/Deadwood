import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

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

        setUpPlayers();
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

    private void setUpPlayers() {
        player1Icon = new JLabel();
        Image image1 = getImage("player1.png");
        image1 = image1.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        player1Icon.setIcon(new ImageIcon(image1));
        player1Icon.setBounds(0, 0, 50, 50);
        pane.add(player1Icon, new Integer(1));
    }
}