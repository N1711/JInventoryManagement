import java.awt.*;
import javax.swing.*;

public class DBChange {

    JFrame frame = new JFrame("Connect to DB");
    JPanel panel = new JPanel();

    public DBChange() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        JRadioButton rbRemote = new JRadioButton("Remote");
        JRadioButton rbLocal = new JRadioButton("Local");
        JTextField txtLocation = new JTextField("..Db location");
        txtLocation.setBounds(20,20,150,20);
        rbLocal.setBounds(20,60,75,20);
        rbRemote.setBounds(85,60, 75, 20);
        panel.setLayout(null);
        panel.add(rbRemote);
        panel.add(rbLocal);
        panel.add(txtLocation);
        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 100,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 200, 250,200);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
