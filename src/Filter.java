import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Filter {
    ArrayList<String> columnNames = new ArrayList<String>();
    ArrayList<String> filterAlgortihm = new ArrayList<String>();

    public Filter() {

        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        JFrame frame = new JFrame("Filter");
        JPanel panel = new JPanel();
        String[] categoryOptions = {"All", "Network", "Laptops", "Smartphones", "Tablets", "Other"};
        String[] statusOptions = {"All", "Available", "Sold"};
        String[] priceOptions = {"All", "0-99", "99-200", "200-500", "500-1000", "1000+"};
        String[] qtyOptions = {"All","0-10", "10-50","50+"};
        String[] userOptions = {"All","admin"};

        JLabel lblId = new JLabel("Id");
        JLabel lblItem = new JLabel("Item");
        JLabel lblDescription = new JLabel("Description");
        JLabel lblStatus = new JLabel("Status");
        JLabel lblPrice = new JLabel("Price");
        JLabel lblCategory = new JLabel("Category");
        JLabel lblAddedBy = new JLabel("AddedBy");
        JComboBox<String> categories = new JComboBox<String>(categoryOptions);
        JComboBox<String> filter = new JComboBox<String>(statusOptions);
        JComboBox<String> price = new JComboBox<String>(priceOptions);
        JComboBox<String> user = new JComboBox<String>(userOptions);
        categories.setSelectedItem("All");
        filter.setSelectedItem("All");
        price.setSelectedItem("All");
        user.setSelectedItem("All");
        JButton btnOK = new JButton("OK");
        JLabel lblEquals = new JLabel("=");
        JLabel lblEquals2 = new JLabel("=");
        JLabel lblLike = new JLabel("Like");
        JLabel lblEquals3 = new JLabel("=");
        JLabel lblEquals4 = new JLabel("=");
        JLabel lblEquals5 = new JLabel("=");
        JLabel lblEquals6 = new JLabel("=");
        JLabel lblLike2 = new JLabel("Like");
        JTextField txtId = new JTextField();
        JTextField txtItem = new JTextField();
        JTextField txtDesc = new JTextField();

        //row 1
        lblId.setHorizontalTextPosition(SwingConstants.CENTER);
        lblId.setBounds(10,10,100,20);

        lblEquals.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEquals.setFont(categories.getFont().deriveFont(Font.ITALIC));
        lblEquals.setBounds(120, 10, 20, 20);

        txtId.setBounds(170,10,120,20);
        //row 2
        lblItem.setHorizontalTextPosition(SwingConstants.CENTER);
        lblItem.setBounds(10,50,100,20);

        lblLike.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLike.setBounds(120,50,40,20);

        txtItem.setBounds(170,50,120,20);
        //row 3
        lblDescription.setHorizontalTextPosition(SwingConstants.CENTER);
        lblDescription.setBounds(10,90,100,20);

        lblLike2.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLike2.setBounds(120,90,40,20);

        txtDesc.setBounds(170,90,120,20);

        //row 4
        lblStatus.setHorizontalTextPosition(SwingConstants.CENTER);
        lblStatus.setBounds(10,130,100,20);

        lblEquals2.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEquals2.setBounds(120,130,40,20);

        filter.setBounds(170,130,120,20);

        //row 5
        lblPrice.setHorizontalTextPosition(SwingConstants.CENTER);
        lblPrice.setBounds(10,170,100,20);

        lblEquals3.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEquals3.setBounds(120,170,40,20);

        price.setBounds(170,170,120,20);

        //row 6



        //row 7
        lblCategory.setHorizontalTextPosition(SwingConstants.CENTER);
        lblCategory.setBounds(10,250,100,20);

        lblEquals5.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEquals5.setBounds(120,250,40,20);

        categories.setBounds(170,250,120,20);

        //row 8
        lblAddedBy.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAddedBy.setBounds(10,290,100,20);

        lblEquals6.setHorizontalTextPosition(SwingConstants.CENTER);
        lblEquals6.setBounds(120,290,40,20);

        user.setBounds(170,290,120,20);

        btnOK.setBounds(110, 330, 100, 30);



        //panel.add(categories);
        //panel.add(filter);
        panel.add(btnOK);
        panel.add(lblId);
        panel.add(lblEquals);
        panel.add(txtId);
        panel.add(lblItem);
        panel.add(lblLike);
        panel.add(txtItem);
        panel.add(lblDescription);
        panel.add(lblLike2);
        panel.add(txtDesc);
        panel.add(lblStatus);
        panel.add(lblEquals2);
        panel.add(filter);
        panel.add(lblPrice);
        panel.add(lblEquals3);
        panel.add(price);
        panel.add(lblEquals4);
        panel.add(lblCategory);
        panel.add(lblEquals5);
        panel.add(categories);
        panel.add(lblAddedBy);
        panel.add(lblEquals6);
        panel.add(user);
        panel.setLayout(null);

        frame.add(panel);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 50, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 185, 320,410);

        frame.setVisible(true);

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory.filterTable(txtId.getText(), txtItem.getText(), txtDesc.getText(), String.valueOf(filter.getSelectedItem()).trim(), String.valueOf(price.getSelectedItem()).trim(), String.valueOf(categories.getSelectedItem()).trim(), String.valueOf(user.getSelectedItem()).trim());
                frame.dispose();
            }
        });
    }

}
