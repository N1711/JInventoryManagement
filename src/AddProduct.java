import org.sqlite.core.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class AddProduct {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame("Add Product");

    public AddProduct() {
        String user = User.getUsername();
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ImageIcon plusIcon = null;
        String[] statusOptions = {"Available", "Sold"};
        String[] categoryOptions = {"Network", "Laptops", "Smartphones", "Tablets", "Other"};
        String message = "";
        TableModelDB data = new TableModelDB();

        URL imgURL = Inventory.class.getResource("/img/plus_small.png");
        if(imgURL != null) {
            plusIcon = new ImageIcon(imgURL);
        }

        JButton btnFile = new JButton("Upload");
        JLabel lblImage = new JLabel("No image selected");

        //dialogFrame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2, 200,200 );
        JComboBox<String> options = new JComboBox<String>(statusOptions);
        JComboBox<String> categories = new JComboBox<String>(categoryOptions);
        options.setVisible(true);
        options.setBounds(20,300,360,30);
        categories.setVisible(true);
        categories.setBounds(20,350, 360,30);
        //JLabel lblItem = new JLabel("Item");
        JTextField txtItem = new JTextField("Item");
        //JLabel lblDescription = new JLabel("Description");
        JTextArea txtDescription = new JTextArea("Description");
        //JLabel lblStatus = new JLabel("Status");
        JTextField txtStatus = new JTextField("Status");
        //JLabel lblQty = new JLabel("Quantity");
        JTextField txtSKU = new JTextField("SKU");
        JTextField txtSerial = new JTextField("Serial Number");
        JTextField txtPrice = new JTextField("Base Price (EUR)");
        JButton btnAdd = new JButton(plusIcon);
        btnAdd.setText("Add");
        btnAdd.setBackground(Color.decode("#4cb44c"));
        btnAdd.setIconTextGap(32);
        btnAdd.setForeground(Color.white);
        btnAdd.setBounds(125,450,150,50);
        JButton btnCancel = new JButton();
        txtItem.setBounds(20,20,360,30);
        txtDescription.setBounds(20,70,360,60);
        //txtStatus.setBounds(20,150,360,30);
        txtSKU.setBounds(20,200,360,30);
        txtSerial.setBounds(20,250,360,30);
        txtPrice.setBounds(20,150,360,30);
        lblImage.setBounds(20,400,230,30);
        btnFile.setBounds(260, 400, 120, 30);
        panel.setLayout(null);
        panel.add(txtItem);
        panel.add(txtDescription);
        //panel.add(txtStatus);
        panel.add(txtSKU);
        panel.add(txtSerial);
        panel.add(btnAdd);
        panel.add(txtPrice);
        panel.add(options);
        panel.add(lblImage);
        panel.add(btnFile);
        panel.add(categories);
        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 200, 410,550);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows = 0;
                if(txtItem.getText() != "" && txtDescription.getText() != "" && txtPrice.getText() != "" && txtSKU.getText() != "" && txtStatus.getText() != "") {
                    try {
                        rows = SQLConnect.insertProduct(txtItem.getText(), txtDescription.getText(), String.valueOf(options.getSelectedItem()), String.valueOf(categories.getSelectedItem()), Integer.parseInt(txtSKU.getText()), txtSerial.getText(), Integer.parseInt(txtPrice.getText()) , user);
                        if(rows > 0) {
                            DBDataModel a = new DBDataModel();
                            a.setId(rows);
                            a.setItem(txtItem.getText());
                            a.setDescription(txtDescription.getText());
                            a.setStatus(String.valueOf(options.getSelectedItem()));
                            a.setPrice(Integer.parseInt(txtPrice.getText()));
                            a.setSKU(Integer.parseInt(txtSKU.getText()));
                            a.setSerialNumber(txtSerial.getText());
                            a.setCategory(String.valueOf(categories.getSelectedItem()));
                            a.setAddedBy(user);
                            //TableModelDB.data.add(a);
                            //TableModelDB d = (TableModelDB) Inventory.tblListItems.getModel();
                            //d.fireTableDataChanged();
                            //Inventory.tblListItems.repaint();
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

        btnFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser flChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                flChooser.setAcceptAllFileFilterUsed(false);
                flChooser.setDialogTitle("Upload an image");
                FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .png files", "png");
                flChooser.addChoosableFileFilter(restrict);
                flChooser.showOpenDialog(null);
            }
        });

    }
}
