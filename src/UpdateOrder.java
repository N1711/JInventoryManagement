import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UpdateOrder {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame("New Order");

    public UpdateOrder(int id, String soldTo, String vat, String status) {
        String user = User.getUsername();
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ImageIcon plusIcon = null;
        String[] statusOptions = {"New", "Processed", "Dispatched", "Completed", "Canceled"};
        //ArrayList<String> productOptions = new ArrayList<>();
        OrdersTableModel data = new OrdersTableModel();

        URL imgURL = Inventory.class.getResource("/img/plus_small.png");
        if (imgURL != null) {
            plusIcon = new ImageIcon(imgURL);
        }

        JComboBox<String> options = new JComboBox<String>(statusOptions);
        options.setVisible(true);
        options.setBounds(20, 150, 360, 30);
        options.setSelectedIndex(Arrays.asList(statusOptions).indexOf(status));
        JTextField txtVAT = new JTextField(String.valueOf(vat));
        JTextArea txtSold = new JTextArea(String.valueOf(soldTo));
        JTextArea txtComments = new JTextArea("Additional Info");
        JButton btnUpdate = new JButton(plusIcon);
        btnUpdate.setText("Update");
        btnUpdate.setBackground(Color.decode("#4cb44c"));
        btnUpdate.setIconTextGap(32);
        btnUpdate.setForeground(Color.white);
        btnUpdate.setBounds(125, 330, 150, 50);
        txtSold.setBounds(20, 70, 360, 60);
        txtVAT.setBounds(20, 20, 360, 30);
        txtComments.setBounds(20, 200, 360, 100);
        panel.setLayout(null);
        panel.add(txtSold);
        panel.add(txtVAT);
        panel.add(btnUpdate);
        panel.add(options);
        panel.add(txtComments);
        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 225, 410, 430);
        frame.setVisible(true);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = 0;
                if (String.valueOf(options.getSelectedItem()) != "" && txtSold.getText() != "") {
                    try {
                        rows = SQLConnect.updateOrder(id, txtSold.getText(), txtVAT.getText(), String.valueOf(options.getSelectedItem()), user);
                        if(rows > 0) {
                            Orders.updateTable();
                            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

}

