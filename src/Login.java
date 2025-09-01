import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login {
    GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public Login() {

        JFrame frame = new JFrame("Login");
        JPanel panel = new JPanel();
        JTextField txtField = new JTextField();
        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");

        JLabel lblUsername = new JLabel("Username");
        JLabel lblPassword = new JLabel("Password");
        JLabel lblLogin = new JLabel("Login");
        JLabel lblError = new JLabel("Failed to login");
        lblError.setForeground(Color.RED);
        lblError.setVisible(false);
        lblError.setBounds(175,230,150,30);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblLeftPane = new JLabel();
        JLabel lblLogo = new JLabel("Dexinis");
        JLabel lblInfo = new JLabel("<html>This software is property of Dexinis inc. 2024. Do not distribute.</html>");
        lblInfo.setFont(new Font("Serif", Font.PLAIN, 12));
        lblLeftPane.setFont(new Font("Serif", Font.BOLD, 30));
        lblLeftPane.setBounds(0,0,150,400);
        lblLogo.setBounds(25,160,100,40);
        lblLogo.setFont(new Font("Serif", Font.BOLD, 24));
        lblInfo.setBounds(10,280,120,80);
        lblLeftPane.add(lblLogo);
        lblLeftPane.add(lblInfo);
        lblLeftPane.setOpaque(true);
        lblLeftPane.setBackground(Color.getHSBColor(125.5f,52.88f,40.78f));
        JPasswordField pswd = new JPasswordField();
        lblLogin.setBounds(225,30,100,50);
        lblLogin.setFont(new Font("Serif", Font.BOLD, 24));
        lblUsername.setBounds(175,80,150,20);
        txtField.setBounds(175,105,150,20);
        lblPassword.setBounds(175,130,150,20);
        pswd.setBounds(175,155,150,20);
        btnLogin.setBounds(175,190,150,20);
        btnCancel.setBounds(175,300,150,20);
        panel.add(txtField);
        panel.add(lblLeftPane);
        panel.add(pswd);
        panel.add(lblError);
        panel.add(btnLogin);
        panel.add(btnCancel);
        panel.add(lblUsername);
        panel.add(lblPassword);
        panel.add(lblLogin);
        panel.setLayout(null);

        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200,
                graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 200,
                400,400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //frame.dispose();
                if(SQLConnect.authenticate(txtField.getText(), new String(pswd.getPassword()))) {
                    User user = new User(txtField.getText());
                        new Main();
                        frame.dispose();
                } else {
                    lblError.setVisible(true);
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
            }
        });
    }
}
