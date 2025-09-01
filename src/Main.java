import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Main {

    private JFrame frame = new JFrame("Inventory Management System");
    JPanel panelInventory = new Inventory();
    JPanel panelOrders = new Orders();
    private JPanel panelHistory = new JPanel();
    private JPanel panelReports = new JPanel();
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public Main() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        UIManager.getLookAndFeelDefaults().put("TabbedPane:TabbedPaneTab.contentMargins", new Insets(30, 30, 30, 30));
        String loggedInUser = User.getUsername();

        ImageIcon inventoryIcon = null;
        ImageIcon ordersIcon = null;
        ImageIcon historyIcon = null;
        ImageIcon reportIcon = null;

        URL imgInventoryURL = Main.class.getResource("/img/checklist.png");
        if(imgInventoryURL != null) {
            inventoryIcon = new ImageIcon(imgInventoryURL);
        }

        URL imgOrdersURL = Main.class.getResource("/img/restock.png");
        if(imgOrdersURL != null) {
            ordersIcon = new ImageIcon(imgOrdersURL);
        }

        URL imgHistoryURL = Main.class.getResource("/img/history.png");
        if(imgHistoryURL != null) {
            historyIcon = new ImageIcon(imgHistoryURL);
        }

        URL imgReportURL = Main.class.getResource("/img/report.png");
        if(imgReportURL != null) {
            reportIcon = new ImageIcon(imgReportURL);
        }

        JMenu menu = new JMenu("File");
        JMenu menu1 = new JMenu("Help");
        JMenu submenu = new JMenu();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem3 = new JMenuItem("Connect to DB");
        JMenuItem menuItem = new JMenuItem("Settings");
        JMenuItem menuItem2 = new JMenuItem("Sign Out");
        JMenuItem menuItem1 = new JMenuItem("Close");

        JMenuItem helpItem1 = new JMenuItem("About");
        JMenuItem helpItem2 = new JMenuItem("Guide");
        //JMenuItem helpItem3 = new JMenuItem("Version");

        menu.add(menuItem);
        menu.add(menuItem2);
        menu.add(menuItem1);
        menu.add(menuItem3);

        menu1.add(helpItem1);
        menu1.add(helpItem2);
        //menu1.add(helpItem3);

        menuBar.add(menu);
        menuBar.add(menu1);

        tabbedPane.addTab("Inventory", inventoryIcon , panelInventory, "Inventory");
        JLabel lblInv = new JLabel("Inventory");
        lblInv.setIcon(inventoryIcon);
        lblInv.setIconTextGap(32);
        lblInv.setHorizontalAlignment(SwingConstants.CENTER);
        lblInv.setPreferredSize(new Dimension(200,100));
        tabbedPane.setTabComponentAt(0, lblInv);

        tabbedPane.addTab("Orders", ordersIcon, panelOrders, "Orders");
        JLabel lblOrd = new JLabel("Orders");
        lblOrd.setIcon(ordersIcon);
        lblOrd.setIconTextGap(32);
        lblOrd.setHorizontalAlignment(SwingConstants.CENTER);
        lblOrd.setPreferredSize(new Dimension(200,100));
        tabbedPane.setTabComponentAt(1, lblOrd);

        tabbedPane.addTab("History", historyIcon, panelHistory, "History");
        JLabel lblHist = new JLabel("History");
        lblHist.setIcon(historyIcon);
        lblHist.setIconTextGap(32);
        lblHist.setHorizontalAlignment(SwingConstants.CENTER);
        lblHist.setPreferredSize(new Dimension(200,100));
        tabbedPane.setTabComponentAt(2, lblHist);
        //pane.setRightComponent(panel2);
        tabbedPane.addTab("Reports", reportIcon, panelReports, "Reports");
        JLabel lblRep = new JLabel("Reports");
        lblRep.setIcon(reportIcon);
        lblRep.setIconTextGap(32);
        lblRep.setHorizontalAlignment(SwingConstants.CENTER);
        lblRep.setPreferredSize(new Dimension(200,100));
        tabbedPane.setTabComponentAt(3, lblRep);

        tabbedPane.setOpaque(true);
        tabbedPane.setBackground(Color.decode("#454545"));
        tabbedPane.setPreferredSize(new Dimension(250,graphics.getDefaultScreenDevice().getDisplayMode().getHeight()-300));
        tabbedPane.setBackgroundAt(0, Color.decode("#454545"));
        tabbedPane.setForegroundAt(0, Color.white);
        tabbedPane.setBackgroundAt(1, Color.decode("#454545"));
        tabbedPane.setForegroundAt(1, Color.white);
        tabbedPane.setBackgroundAt(2, Color.decode("#454545"));
        tabbedPane.setForegroundAt(2, Color.white);
        tabbedPane.setBackgroundAt(3, Color.decode("#454545"));
        tabbedPane.setForegroundAt(3, Color.white);

        frame.add(tabbedPane);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //event listeners
        menuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User(null);
                new Login();
                frame.dispose();
            }
        });

        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DBChange();
            }
        });

    }
}
