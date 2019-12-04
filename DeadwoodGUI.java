import javax.swing.*;
import javax.imageio.*;
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
        moveButton.setBounds(boardWidth + 50, 10, 200, 100);
        pane.add(moveButton, new Integer(1));

        actButton = new JButton("Act");
        actButton.setBounds(boardWidth + 50, 120, 200, 100);
        actButton.setVisible(false);
        pane.add(actButton, new Integer(1));

        rehearseButton = new JButton("Rehearse");
        rehearseButton.setBounds(boardWidth + 50, 230, 200, 100);
        rehearseButton.setVisible(false);
        pane.add(rehearseButton, new Integer(1));


    }
}