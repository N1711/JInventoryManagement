import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;

//add orderId to each order. When listing orders, group by OrderId
public class Orders extends JPanel {

    public static JTable tblListItems;
    public static OrdersTableModel data = new OrdersTableModel();
    JTextField startDate = new JTextField("From");
    JTextField endDate = new JTextField("To");

    public Orders() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(30, 30, 30, 30));
        String loggedInUser = User.getUsername();
        ImageIcon plusIcon = null;
        ImageIcon updateIcon = null;
        ImageIcon deleteIcon = null;
        ImageIcon searchIcon = null;
        ImageIcon viewIcon = null;
        ImageIcon refreshIcon = null;

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
        JLabel lblInventory = new JLabel("Orders");
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

        JFrame f = new JFrame();
        JButton btnFilter = new JButton("Filter");
        startDate.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 200, 10, 100, 30);
        endDate.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 320, 10, 100, 30);
        btnFilter.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 440, 10, 100, 30);
        startDate.setEditable(false);
        endDate.setEditable(false);

        JButton btnAdd = new JButton(plusIcon);
        btnAdd.setText("New Order");

        btnAdd.setBackground(Color.decode("#4cb44c"));
        btnAdd.setIconTextGap(32);
        btnAdd.setForeground(Color.white);
        JButton btnUpdate = new JButton(updateIcon);
        btnUpdate.setText("Update");
        btnUpdate.setBackground(Color.decode("#4cb44c"));
        btnUpdate.setIconTextGap(32);
        btnUpdate.setForeground(Color.white);
        JButton btnView = new JButton(viewIcon);
        btnView.setText("Invoice");
        btnView.setBackground(Color.decode("#4cb44c"));
        btnView.setIconTextGap(32);
        btnView.setForeground(Color.white);
        JButton btnRefresh = new JButton(refreshIcon);
        btnRefresh.setText("Refresh");
        btnRefresh.setBackground(Color.decode("#4cb44c"));
        btnRefresh.setIconTextGap(32);
        btnRefresh.setForeground(Color.white);
        JButton btnWarranty = new JButton(refreshIcon);
        btnWarranty.setText("Warranty");
        btnWarranty.setBackground(Color.decode("#4cb44c"));
        btnWarranty.setIconTextGap(32);
        btnWarranty.setForeground(Color.white);

        btnAdd.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,50,150,50);
        btnAdd.setMargin(new Insets(0,0,0,0));
        btnUpdate.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,120,150,50);
        btnUpdate.setMargin(new Insets(0,0,0,0));
        btnView.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,190,150,50);
        btnView.setMargin(new Insets(0,0,0,0));
        btnRefresh.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,260,150,50);
        btnRefresh.setMargin(new Insets(0,0,0,0));
        btnWarranty.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,330,150,50);
        btnWarranty.setMargin(new Insets(0,0,0,0));

        JTextField txtSearch = new JTextField("...find order");
        txtSearch.setBounds(10,10,(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2,30);

        JButton btnSearch = new JButton(searchIcon);
        btnSearch.setText("Search");
        //btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSearch.setIconTextGap(18);
        btnSearch.setBounds((graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 200) / 2 + 30, 10, 150, 30);

        tblListItems = new JTable(data);
        tblListItems.setBounds(0,0,graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 100,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 150);
        tblListItems.setAutoCreateRowSorter(true);
        JScrollPane scrPane = new JScrollPane(tblListItems);
        scrPane.setBounds(10,50,graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 100,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 150);

        lblUsername.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() - graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 5 - 80,graphics.getDefaultScreenDevice().getDisplayMode().getHeight() - 200,150,100);
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);

        updateTable();

        add(scrPane);
        add(btnAdd);
        add(btnUpdate);
        add(txtSearch);
        add(btnSearch);
        add(btnView);
        add(btnRefresh);
        add(lblUsername);
        add(startDate);
        add(endDate);
        add(btnFilter);

        setVisible(true);
        setBounds(0,0,640,640);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddOrder();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tblListItems.getSelectionModel().isSelectionEmpty()) {
                    return;
                }
                int row = tblListItems.getSelectedRow();
                new UpdateOrder(Integer.parseInt(tblListItems.getModel().getValueAt(row,0).toString()), tblListItems.getModel().getValueAt(row,2).toString(), tblListItems.getModel().getValueAt(row,3).toString(), tblListItems.getModel().getValueAt(row,9).toString() );
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtSearch.getText() == "") {
                    return;
                }
                try {
                    searchTable(txtSearch.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startDate.getText() == "" || startDate.getText() == "From" || endDate.getText() == "" || endDate.getText() == "To") {
                    return;
                }
                try {
                    filterTable(startDate.getText(), endDate.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        startDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startDate.setText(new DatePicker(f).setPickedDate());
            }
        });

        endDate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                endDate.setText(new DatePicker(f).setPickedDate());
            }
        });
    }


    public static void updateTable() {
        ResultSet rs = null;
        data.blankData();
        try {
            rs = SQLConnect.getOrders();
        } catch (Exception e) {
            System.out.println(e);
        }

        try
        {
            while (rs.next())
            {
                OrdersDataModel a = new OrdersDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setSoldTo(rs.getString(3));
                a.setVAT(rs.getString(4));
                a.setSKU(rs.getString(5));
                a.setSerialNumber(rs.getString(6));
                a.setWarrantyBegin(rs.getString(7));
                a.setWarrantyEnd(rs.getString(8));
                a.setCompletedOn(rs.getString(9));
                a.setStatus(rs.getString(10));
                a.setCompletedBy(rs.getString(11));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
            JOptionPane.showMessageDialog(new JFrame(), "Error fetching products", "Dialog", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void searchTable(String _search) {

        ResultSet rs = null;
        data.blankData();

        try {
            rs = SQLConnect.searchOrders(_search);
        } catch (Exception e) {
            System.out.println(e);
        }

        try
        {
            while (rs.next())
            {
                OrdersDataModel a = new OrdersDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setSoldTo(rs.getString(3));
                a.setVAT(rs.getString(4));
                a.setSKU(rs.getString(5));
                a.setSerialNumber(rs.getString(6));
                a.setWarrantyBegin(rs.getString(7));
                a.setWarrantyEnd(rs.getString(8));
                a.setCompletedOn(rs.getString(9));
                a.setStatus(rs.getString(10));
                a.setCompletedBy(rs.getString(11));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private static void filterTable(String _start, String _end) {
        ResultSet rs = null;
        data.blankData();

        try {
            rs = SQLConnect.filterOrders(_start, _end);
        } catch (Exception e) {
            System.out.println(e);
        }
        try
        {
            while (rs.next())
            {
                OrdersDataModel a = new OrdersDataModel();
                a.setId(rs.getInt(1));
                a.setItem(rs.getString(2));
                a.setSoldTo(rs.getString(3));
                a.setVAT(rs.getString(4));
                a.setSKU(rs.getString(5));
                a.setSerialNumber(rs.getString(6));
                a.setWarrantyBegin(rs.getString(7));
                a.setWarrantyEnd(rs.getString(8));
                a.setCompletedOn(rs.getString(9));
                a.setStatus(rs.getString(10));
                a.setCompletedBy(rs.getString(11));
                data.addData(a);
            }
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
    }


}
