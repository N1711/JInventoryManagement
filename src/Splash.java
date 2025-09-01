import javax.swing.*;
import java.awt.*;

public class Splash {
    public static void main(String[] args) {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        JFrame frame =  new JFrame();
        JLabel lblInitialize = new JLabel("Initializing...");
        lblInitialize.setHorizontalAlignment(SwingConstants.CENTER);
        lblInitialize.setBounds(0,480,500,20);
        lblInitialize.setForeground(Color.black);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 250,
                graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 250,
                500,500);
        JLabel lblIcon = new JLabel(new ImageIcon(".\\External\\OIG3.jfif"));
        lblIcon.setBounds(0,0,500,480);
        frame.add(lblIcon);
        frame.add(lblInitialize);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setVisible(true);

        SQLConnect con = new SQLConnect();

        if(con.getSQLStatus() == "Success") {
            lblInitialize.setText("Connected!");
            Timer timer = new Timer(2000, e -> {
                new Login();
                frame.dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Error connecting to the db", "Dialog", JOptionPane.ERROR_MESSAGE);
            frame.dispose();
        }

    }
}
