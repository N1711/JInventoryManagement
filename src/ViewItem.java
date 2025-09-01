import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ViewItem {
    private JPanel panel = new JPanel();
    private JFrame frame = new JFrame();

    public ViewItem(String productName, String description) {
        String user = User.getUsername();
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ImageIcon imageIcon = null;

        URL imgURL = Inventory.class.getResource("/img/y9DpT.png");
        if(imgURL != null) {
            imageIcon = new ImageIcon(imgURL);
        }

        if(description.length() == 0) {
            return;
        }

        ArrayList<String> itemDescription = parseItem(description);

        JLabel productImage = new JLabel(imageIcon);
        productImage.setBounds(10,10,100,100);

        JLabel sp = new JLabel("Specifications");
        sp.setBounds(130, 10, 260, 30);
        sp.setHorizontalTextPosition(SwingConstants.CENTER);

        JTextArea txtSpecs = new JTextArea();
        if(itemDescription.size() > 0) {
            txtSpecs.setText(itemDescription.stream().toArray(String[]::new)[0]);
        }
        txtSpecs.setBounds(130,40,250,70);
        txtSpecs.setLineWrap(true);
        txtSpecs.setEditable(false);

        JLabel ds = new JLabel("Description");
        ds.setBounds(10,130,400, 30);
        ds.setHorizontalTextPosition(SwingConstants.CENTER);

        JTextArea txtDescription = new JTextArea();
        txtDescription.setBounds(10,160, 370,260);
        txtDescription.setLineWrap(true);
        txtDescription.setEditable(false);

        if(itemDescription.size() > 0) {
            txtDescription.setText(itemDescription.stream().toArray(String[]::new)[1]);
        }

        panel.setLayout(null);
        panel.add(productImage);
        panel.add(sp);
        panel.add(txtSpecs);
        panel.add(ds);
        panel.add(txtDescription);

        frame.add(panel);
        frame.setTitle(productName);
        frame.setBounds(graphics.getDefaultScreenDevice().getDisplayMode().getWidth() / 2 - 200, graphics.getDefaultScreenDevice().getDisplayMode().getHeight() / 2 - 200, 410,500);
        frame.setVisible(true);

    }

    private ArrayList<String> parseItem(String item) {
        String _item = item;
        if(_item.length() == 0) return new ArrayList<>();
        ArrayList<String> itemSpecs = new ArrayList<>();
        String[] desc = _item.split(";");
        if(desc.length > 1) {
            itemSpecs.add(desc[0]);
            itemSpecs.add(desc[1]);
        }
        return itemSpecs;
    }
}
