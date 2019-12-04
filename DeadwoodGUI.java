import javax.swing.*;
import javax.imageio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Displays GUI for a Deadwood game run through Deadwood.java
 * Main View-level class
 */
public class DeadwoodGUI {
    private JFrame frame;

    public DeadwoodGUI() {
        JFrame frame = new JFrame("Deadwood");
        frame.setSize(1920, 1080);
        JLabel boardLabel = new JLabel();
        boardLabel.setIcon(new ImageIcon(getImage("board.jpg")));
        frame.add(boardLabel);

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

    public int promptNumPlayers() {
        return Integer.parseInt(JOptionPane.showInputDialog(frame, "Please enter number of Players",
                "New Game", JOptionPane.QUESTION_MESSAGE));
    }
}