import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;

public class UpdateItem {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame("Update Product");

    public UpdateItem(int id, String Item, String Description, String Status, String Category, int SKU, String SerialNumber, int Price) {
        String user = User.getUsername();
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ImageIcon plusIcon = null;
        String[] statusOptions = {"Available", "Sold"};
        String[] categoryOptions = {"Network", "Laptops", "Smartphones", "Tablets", "Other"};
        String message = "";

        URL imgURL = Inventory.class.getResource("/img/pencil_small.png");
        if(imgURL != null) {
            plusIcon = new ImageIcon(imgURL);
        }

        //dialogFrame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2, 200,200 );
        JButton btnFile = new JButton("Upload");
        JLabel lblImage = new JLabel("No image selected");
        JComboBox<String> options = new JComboBox<String>(statusOptions);
        JComboBox<String> categories = new JComboBox<String>(categoryOptions);
        options.setVisible(true);
        options.setBounds(20,300,360,30);
        categories.setVisible(true);
        categories.setBounds(20,350, 360,30);
        //JLabel lblItem = new JLabel("Item");
        JTextField txtItem = new JTextField(Item);
        //JLabel lblDescription = new JLabel("Description");
        JTextArea txtDescription = new JTextArea(Description);
        //JLabel lblStatus = new JLabel("Status");
        JTextField txtStatus = new JTextField(Status);
        categories.setSelectedIndex(Arrays.asList(categoryOptions).indexOf(Category));
        options.setSelectedIndex(Arrays.asList(statusOptions).indexOf(Status));
        //JLabel lblQty = new JLabel("Quantity");
        JTextField txtSKU = new JTextField(String.valueOf(SKU));
        JTextField txtSerial = new JTextField(String.valueOf(SerialNumber));
        JTextField txtPrice = new JTextField(String.valueOf(Price));
        JButton btnUpdate = new JButton(plusIcon);
        btnUpdate.setText("Update");
        btnUpdate.setBackground(Color.decode("#4cb44c"));
        btnUpdate.setIconTextGap(32);
        btnUpdate.setForeground(Color.white);
        btnUpdate.setBounds(125,450,150,50);
        txtItem.setBounds(20,20,360,30);
        JScrollPane paneTextArea = new JScrollPane(txtDescription);
        paneTextArea.setBounds(20,70,360,60);
        //txtStatus.setBounds(20,150,360,30);
        txtSKU.setBounds(20,200,360,30);
        txtSerial.setBounds(20,250,360,30);
        txtPrice.setBounds(20,150,360,30);
        lblImage.setBounds(20,400,230,30);
        btnFile.setBounds(260, 400, 120, 30);
        panel.setLayout(null);
        panel.add(txtItem);
        panel.add(paneTextArea);
        //panel.add(txtStatus);
        panel.add(txtSKU);
        panel.add(txtSerial);
        panel.add(btnUpdate);
        panel.add(txtPrice);
        panel.add(options);
        panel.add(categories);
        panel.add(lblImage);
        panel.add(btnFile);
        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 200, 410,550);
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtItem.getText() != "" && txtDescription.getText() != "" && txtPrice.getText() != "" && txtSKU.getText() != "" && txtStatus.getText() != "") {
                    try {
                        int rows = SQLConnect.updateProduct(id, txtItem.getText(), txtDescription.getText(), options.getSelectedItem().toString(), categories.getSelectedItem().toString(), Integer.parseInt(txtSKU.getText()), txtSerial.getText(), Integer.parseInt(txtPrice.getText()), user);
                        if(rows > 0) {
                            JOptionPane.showMessageDialog(new JFrame(), "Success", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                            Inventory.updateTable();
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
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
