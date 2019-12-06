/**
 * JButton with brown colors and set size
 **/

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DeadwoodJButton extends JButton {

    Border loweredBorder;
    Border normalBorder;

    public DeadwoodJButton(String text, int xPosition, int yPosition) {
        super(text);
        setBackground(DeadwoodGUI.LIGHTBROWN);
        setFont(this.getFont().deriveFont(16.0f));
        setBounds(xPosition, yPosition, 200, Deadwood.PLAYER_TOKEN_SIZE);

        loweredBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        normalBorder = BorderFactory.createLineBorder(Color.black);
        setBorder(normalBorder);
    }

    public void setEnabled(boolean value) {
        if (value) {
            super.setEnabled(true);
            setBackground(DeadwoodGUI.LIGHTBROWN);
            setBorder(normalBorder);
        } else {
            super.setEnabled(false);
            setBorder(loweredBorder);
            setBackground(DeadwoodGUI.GRAYBROWN);
        }
    }
}
