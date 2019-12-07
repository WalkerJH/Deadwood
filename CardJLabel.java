/**
 * JLabel object specifically for a Deadwood shot counter token
 */

import javax.swing.*;

public class CardJLabel extends JLabel{
    public CardJLabel(int id, Coordinates coord) {
        super();
        String filename;
        if(id < 10)
            filename = String.format("0%d.png", id);
        else
            filename = String.format("%d.png", id);
        ImageIcon image = new ImageIcon(DeadwoodGUI.getImage(filename));
        setIcon(image);
        setBounds(coord.getX(), coord.getY(), 205, 115);
    }
}
