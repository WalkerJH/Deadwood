import javax.swing.*;

public class ShotCounterJLabel extends JLabel {
    public ShotCounterJLabel(Coordinates coord) {
        super();
        ImageIcon image = new ImageIcon(DeadwoodGUI.getImage("shot.png"));
        setIcon(image);
        setBounds(coord.getX(), coord.getY(), 47, 47);
    }
}
