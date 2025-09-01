import org.sqlite.core.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends JPanel {
    public static JTable tblListItems;
    public static TableModelDB data = new TableModelDB();

    public Inventory() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(30, 30, 30, 30));
        String loggedInUser = User.getUsername();
        ImageIcon plusIcon = null;
        ImageIcon updateIcon = null;
        ImageIcon deleteIcon = null;
        ImageIcon searchIcon = null;
        ImageIcon viewIcon = null;
        ImageIcon refreshIcon = null;
        updateTable();

        URL imgURL = Inventory.class.getResource("/img/plus_small.png");
        if(imgURL != null) {
            plusIcon = new ImageIcon(imgURL);
        }

        URL imgPencilURL = Inventory.class.getResource("/img/pencil_small.png");
        if(imgPencilURL != null) {
            updateIcon = new ImageIcon(imgPencilURL);
        }

        URL imgDeleteURL = Inventory.class.getResource("/img/delete_small.png");
        if(imgPencilURL != null) {
            deleteIcon = new ImageIcon(imgDeleteURL);
        }

        URL imgSearchURL = Inventory.class.getResource("/img/magnifying-glass.png");
        if(imgSearchURL != null) {
            searchIcon = new ImageIcon(imgSearchURL);
        }

        URL imgViewURL = Inventory.class.getResource("/img/file.png");
        if(imgViewURL != null) {
            viewIcon = new ImageIcon(imgViewURL);
        }

        URL imgRefreshURL = Inventory.class.getResource("/img/refresh.png");
        if(imgViewURL != null) {
            refreshIcon = new ImageIcon(imgRefreshURL);
        }

        setLayout(null);
        JLabel lblInventory = new JLabel("Inventory");
        lblInventory.setFont(new Font("Serif", Font.BOLD, 24));
        lblInventory.setHorizontalAlignment(SwingConstants.CENTER);
        lblInventory.setForeground(Color.white);
        //JLabel lblManage = new JLabel("Manage your inventory");
        lblInventory.setBounds(0,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 200,graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 6,30);
        //lblManage.setBounds(10,20,300,20);
        JLabel lblUsername = new JLabel("Logged in as : " + loggedInUser);
        lblUsername.setForeground(Color.white);
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setBounds(0, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 160, graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 6, 30);

        setBackground(Color.decode("#787878"));

        JButton btnAdd = new JButton(plusIcon);
        btnAdd.setText("Add");
        //btnAdd.setVerticalTextPosition(SwingConstants.VERTICAL);
        //btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setBackground(Color.decode("#4cb44c"));
        btnAdd.setIconTextGap(32);
        btnAdd.setForeground(Color.white);
        JButton btnUpdate = new JButton(updateIcon);
        btnUpdate.setText("Update");
        btnUpdate.setBackground(Color.decode("#4cb44c"));
        btnUpdate.setIconTextGap(32);
        btnUpdate.setForeground(Color.white);
        JButton btnRemove = new JButton(deleteIcon);
        btnRemove.setText("Remove");
        btnRemove.setBackground(Color.decode("#4cb44c"));
        btnRemove.setIconTextGap(32);
        btnRemove.setForeground(Color.white);
        JButton btnView = new JButton(viewIcon);
        btnView.setText("View");
        btnView.setBackground(Color.decode("#4cb44c"));
        btnView.setIconTextGap(32);
        btnView.setForeground(Color.white);
        JButton btnRefresh = new JButton(refreshIcon);
        btnRefresh.setText("Refresh");
        btnRefresh.setBackground(Color.decode("#4cb44c"));
        btnRefresh.setIconTextGap(32);
        btnRefresh.setForeground(Color.white);

        btnAdd.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,50,150,50);
        btnAdd.setMargin(new Insets(0,0,0,0));
        btnUpdate.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,120,150,50);
        btnUpdate.setMargin(new Insets(0,0,0,0));
        btnRemove.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,190,150,50);
        btnRemove.setMargin(new Insets(0,0,0,0));
        btnView.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,260,150,50);
        btnView.setMargin(new Insets(0,0,0,0));
        btnRefresh.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,330,150,50);
        btnRefresh.setMargin(new Insets(0,0,0,0));

        JTextField txtSearch = new JTextField("...find item");
        txtSearch.setBounds(10,10,(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2,30);

        JButton btnSearch = new JButton(searchIcon);
        btnSearch.setText("Search");
        //btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearch.setIconTextGap(18);
        btnSearch.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 30, 10, 150, 30);

        JButton btnFilter = new JButton();
        btnFilter.setText("Advanced Filter");
        btnFilter.setHorizontalTextPosition(SwingConstants.CENTER);
        //btnSort.setIconTextGap(24);
        btnFilter.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 200, 10, 150, 30);

        tblListItems = new JTable(data);
        tblListItems.setBounds(0,0,graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 100,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 150);
        tblListItems.setAutoCreateRowSorter(true);
        JScrollPane scrPane = new JScrollPane(tblListItems);
        scrPane.setBounds(10,50,graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 100,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 150);

        lblUsername.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 200,150,100);
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);

        add(scrPane);
        add(btnAdd);
        add(btnUpdate);
        add(btnRemove);
        add(txtSearch);
        add(btnSearch);
        add(btnFilter);
        add(btnView);
        add(btnRefresh);
        add(lblUsername);

        setVisible(true);
        setBounds(0,0,640,640);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProduct();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });


        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtSearch.getText() == "") {
                    return;
                }
                String value = txtSearch.getText();
                try {
                    searchTable(value);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tblListItems.getSelectionModel().isSelectionEmpty()) {
                    return;
                }
                String value = String.valueOf(tblListItems.getValueAt(tblListItems.getSelectedRow(), 0));
                try {
                    Integer numRows = SQLConnect.deleteProduct(Integer.parseInt(value));
                    if(numRows > 0) {
                        TableModelDB d = (TableModelDB) tblListItems.getModel();
                        d.removeRowAt((int) tblListItems.getValueAt(tblListItems.getSelectedRow(), 0));
                        JOptionPane.showMessageDialog(new JFrame(), "Success", "Dialog", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Error", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tblListItems.getSelectionModel().isSelectionEmpty()) {
                    return;
                }
                int row = tblListItems.getSelectedRow();
                new UpdateItem(Integer.parseInt(tblListItems.getModel().getValueAt(row, 0).toString()), tblListItems.getModel().getValueAt(row,1).toString(), tblListItems.getModel().getValueAt(row,2).toString(),
                tblListItems.getModel().getValueAt(row,3).toString(), tblListItems.getModel().getValueAt(row,4).toString(),
                Integer.parseInt(tblListItems.getModel().getValueAt(row,5).toString()), tblListItems.getModel().getValueAt(row,6).toString(),
                Integer.parseInt(tblListItems.getModel().getValueAt(row, 7).toString()));
            }
        });

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Filter();
            }
        });

        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tblListItems.getSelectionModel().isSelectionEmpty()) {
                    return;
                }
                int row = tblListItems.getSelectedRow();
                new ViewItem(tblListItems.getModel().getValueAt(row,1).toString(), tblListItems.getModel().getValueAt(row,2).toString());
            }
        });
    }

    public static void updateTable() {
        ResultSet rs = null;
        data.blankData();
        try {
            rs = SQLConnect.getAvailableProducts();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(new JFrame(), "Error getting products", "Dialog", JOptionPane.ERROR_MESSAGE);
        }

        try
        {
            while (rs.next())
            {
                DBDataModel a = new DBDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setDescription(rs.getString(3));
                a.setStatus(rs.getString(4));
                a.setCategory(rs.getString(5));
                a.setSKU(rs.getInt(6));
                a.setSerialNumber(rs.getString(7));
                a.setPrice(rs.getInt(8));
                a.setAddedBy(rs.getString(9));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(new JFrame(), "Error getting products", "Dialog", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void searchTable(String search) {

        ResultSet rs = null;
        data.blankData();

        try {
            rs = SQLConnect.searchProducts(search);
        } catch (Exception e) {
            System.out.println(e);
        }

        try
        {
            while (rs.next())
            {
                DBDataModel a = new DBDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setDescription(rs.getString(3));
                a.setStatus(rs.getString(4));
                a.setCategory(rs.getString(5));
                a.setSKU(rs.getInt(6));
                a.setSerialNumber(rs.getString(7));
                a.setPrice(rs.getInt(8));
                a.setAddedBy(rs.getString(9));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static void filterTable(String id, String item, String desc, String status, String price, String category, String addedBy) {
        ResultSet rs = null;
        data.blankData();
        Integer _id;
        String _item;
        String _desc;
        String _status;
        Integer _priceLow;
        Integer _priceHigh;
        String _category;
        String _addedBy;

        if(item.isEmpty() || item.length() == 0) {
            _item = "";
        } else {
            _item = item;
        }

        if(desc.isEmpty() || desc.length() == 0) {
            _desc = "";
        } else {
            _desc = item;
        }

        if(id.isEmpty() || id.length() == 0) {
            _id = null;
        } else {
            _id = Integer.parseInt(id);
        }
        if(status == "All") {
            _status = "";
        } else {
            _status = status;
        }
        if(category == "All") {
            _category = "";
        } else {
            _category = category;
        }
        if(addedBy == "All") {
            _addedBy = "";
        } else {
            _addedBy = addedBy;
        }
        if(price == "All") {
            _priceLow = null;
            _priceHigh = null;
        } else if (price == "1000+") {
            _priceLow = 1000;
            _priceHigh = 100000000;
        } else {
            if(price.contains("-")) {
                _priceLow = Integer.parseInt(price.split("-")[0]);
                _priceHigh = Integer.parseInt(price.split("-")[1]);
            } else {
                _priceLow = null;
                _priceHigh = null;
            }



        }

        try {
            rs = SQLConnect.filterProducts(_id, _item, _desc, _status, _priceLow, _priceHigh, _category, _addedBy);
        } catch (Exception e) {
            System.out.println(e);
        }

        try
        {
            while (rs.next())
            {
                DBDataModel a = new DBDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setDescription(rs.getString(3));
                a.setStatus(rs.getString(4));
                a.setCategory(rs.getString(5));
                a.setSKU(rs.getInt(6));
                a.setSerialNumber(rs.getString(7));
                a.setPrice(rs.getInt(8));
                a.setAddedBy(rs.getString(9));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }

    }


}
