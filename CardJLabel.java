/**
 * JLabel object specifically for a Deadwood card
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CardJLabel extends JLabel{
    public CardJLabel(int id, DeadwoodGUI dGUI, Coordinates coord) {
        super();
        String filename;
        if(id < 10)
            filename = String.format("0%d.png", id);
        else
            filename = String.format("%d.png", id);
        ImageIcon image = new ImageIcon(dGUI.getImage(filename));
        setIcon(image);
        setBounds(coord.getX(), coord.getY(), 205, 115);
        //setVisible(false);
    }

}
