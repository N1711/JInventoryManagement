import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddOrder {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame("New Order");
    JComboBox<String> categories = new JComboBox<String>();

    public AddOrder() {
        getProducts();
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
        options.setBounds(20, 200, 360, 30);
        categories.setVisible(true);
        categories.setBounds(20, 20, 360, 30);
        JTextField txtVAT = new JTextField("VAT");
        JTextArea txtSold = new JTextArea("Sold To Details");
        JTextArea txtComments = new JTextArea("Additional Info");
        JButton btnAdd = new JButton(plusIcon);
        btnAdd.setText("Add");
        btnAdd.setBackground(Color.decode("#4cb44c"));
        btnAdd.setIconTextGap(32);
        btnAdd.setForeground(Color.white);
        btnAdd.setBounds(125, 370, 150, 50);
        txtSold.setBounds(20, 120, 360, 60);
        txtVAT.setBounds(20, 70, 360, 30);
        txtComments.setBounds(20, 250, 360, 100);
        panel.setLayout(null);
        panel.add(txtSold);
        panel.add(txtVAT);
        //panel.add(txtStatus);
        panel.add(btnAdd);
        panel.add(options);
        panel.add(categories);
        panel.add(txtComments);
        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 225, 410, 470);
        frame.setVisible(true);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = 0;
                if (String.valueOf(categories.getSelectedItem()) != "" && String.valueOf(options.getSelectedItem()) != "" && txtSold.getText() != "") {
                    try {
                        String[] id = String.valueOf(categories.getSelectedItem()).split("\\|");
                        if (id.length == 0) {
                            return;
                        }
                        int itemId = Integer.parseInt(id[0].trim().replace("(","").replace(")",""));
                        String item = id[1].trim();
                        String serialN = id[2].trim();
                        rows = SQLConnect.insertOrder(itemId, txtSold.getText(), txtVAT.getText(), String.valueOf(options.getSelectedItem()), user);
                        if (rows > 0) {
                            Orders.updateTable();
                            Inventory.updateTable();
                            JOptionPane.showMessageDialog(new JFrame(), "Success", "Dialog", JOptionPane.INFORMATION_MESSAGE);
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

    private ResultSet getProducts() {
        ResultSet rs = null;
        try {
            rs = SQLConnect.getAvailableProducts();
            while (rs.next()) {
                categories.addItem("(" + rs.getInt("id") + ")" + " | " + rs.getString("Item") + " | " + rs.getString("SerialNumber"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error obtaining product list", "Dialog", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return rs;
    }
}
