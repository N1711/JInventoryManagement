import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.sql.Date;
import java.time.*;

/**
 * Class SQLConnect which handles all database connections / queries.
 * Call new SQLConnect() to connect to the database.
 * If tables inventory, users, orders, history do not exist will be created by default.
 * If admin user does not exist will add it in the users table. Default password is admin.
 * Get the connection instance by using SQLConnect.connection.
 * Get the status by using SQLConnect.getStatus()
 * @see org.sqlite.SQLiteConnection
 */

public class SQLConnect {

    /**
     * @setSQLStatus - internal function to set the SQL status - Success or failed
     * @getSQLStatus - static so can be used to get the current connection status
     * @authenticate - function to authenticate the user against the users table in SQLite
     * @insertProduct - required params are Item (string), Description(string), Status(string), Category(string), SKU(int), SerialNumber(string), Price (int), addedBy(string). Returns Integer (num rows)
     * @getProducts - returns ResultSet from SQLite and executes Select * from inventory
     * @deleteProduct - returns integer. Deletes a record from inventory by id (integer).
     * @searchProduct - returns ResultSet from SQLite. Requires String argument used in a like clause. Matches by SKU, Serial Number, Description or Item
     * @filterProducts - returns ResultSet from SQLite. Requires arguments passed over from Filter form.
     * @updateProduct - Returns int. Updates a product based on the input from the Update Form. SQL query is built using StringBuilder. No fields are mandatory.
     * @param _username - String from the login form
     * @param _password - String from the login form
     */

    public static String status = "";
    public static Connection connection = null;
    public static final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public SQLConnect() {
        String url = "jdbc:sqlite:warehouse.db";
        String sql = "CREATE TABLE IF NOT EXISTS inventory ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Item text not null,"
                + "Description text not null,"
                + "Status text not null,"
                + "Category text not null,"
                + "SKU INTEGER NOT NULL,"
                + "Price INTEGER NOT NULL,"
                + "Qty INTEGER NOT NULL,"
                + "addedBy text not null"
                + ");";
        String userInitialize = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username text not null unique,"
                + "password text not null" + ");";
        String sqlDefaultUser=  "INSERT OR IGNORE INTO users (username, password) VALUES (\"admin\",\"YWRtaW4=\")";
        String sqlOrders = "CREATE TABLE IF NOT EXISTS orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "SoldTo text not null,"
                + "VAT text,"
                + "Status text not null,"
                + "CompletedOn TEXT,"
                + "completedBy text not null"
                + ");";
        String sqlOrderItems = "CREATE TABLE IF NOT EXISTS orderitems ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "orderId INTEGER NOT NULL,"
                + "itemId INTEGER NOT NULL,"
                + "WarrantyBegin TEXT NOT NULL,"
                + "WarrantyEnd TEXT NOT NULL,"
                + "SerialNumber TEXT,"
                + "Discount Integer not null,"
                + "finalPrice Integer not null,"
                + "FOREIGN KEY (orderId) REFERENCES orders (id),"
                + "FOREIGN KEY (itemId) REFERENCES inventory (id)"
                + ");";
        String sqlHistory = "CREATE TABLE IF NOT EXISTS history ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "action TEXT NOT NULL,"
                + "date TEXT NOT NULL"
                + ");";
        String sqlReports = "CREATE TABLE IF NOT EXISTS reports ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Status TEXT NOT NULL,"
                + "Statement TEXT NOT NULL"
                + ");";
        try {
            this.connection = DriverManager.getConnection(url);
            Statement statement = this.connection.createStatement();
            DatabaseMetaData connData = this.connection.getMetaData();
            //System.out.println(String.valueOf(connData));
            statement.setQueryTimeout(30);
            try {
                statement.execute(sql);
                statement.execute(userInitialize);
                statement.execute(sqlDefaultUser);
                statement.execute(sqlOrders);
                statement.execute(sqlHistory);
                System.out.println("Connected to the database.");
                setSQLStatus("Success");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                setSQLStatus("Failed");
            }

            //setConnectionStatus("Success");
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            System.out.println(e.getMessage());
            setSQLStatus("Failed");
            JOptionPane.showMessageDialog(new JFrame(), "Error connecting ot the DB", "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setSQLStatus (String _status) {
        status = _status;
    }

    public static String getSQLStatus () {
        return status;
    }

    public static boolean authenticate (String _username, String _password) {
        Boolean auth = false;
        String url = "jdbc:sqlite:warehouse.db";
        String authentication = "SELECT * from users where username = ? and password = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(authentication);
            String encodedPassword = Base64.getEncoder().encodeToString(_password.getBytes());
            stmt.setString(1, _username);
            stmt.setString(2, encodedPassword);
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                auth = true;
            } else {
                auth = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            auth = false;
        }

        return auth;
    }

    public static int insertProduct (String Item, String Description, String Status, String Category, int SKU, int Price, int qty, String addedBy) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        String insertCommand = "INSERT INTO inventory(Item, Description, Status, Category, SKU, Price, Qty, addedBy) VALUES(?,?,?,?,?,?,?,?)";
        int numRows = 0;
        try {
            statement = connection.prepareStatement(insertCommand);
            statement.setString(1, Item);
            statement.setString(2, Description);
            statement.setString(3,Status);
            statement.setString(4, Category);
            statement.setInt(5, SKU);
            statement.setInt(6, Price);
            statement.setInt(7, qty);
            statement.setString(8, addedBy);
            numRows = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRows;
    }

    public static int deleteProduct (Integer Id) throws SQLException {
        PreparedStatement statement = null;
        String insertCommand = "DELETE FROM inventory where id = ?";
        int numRows = 0;
        try {
            statement = connection.prepareStatement(insertCommand);
            statement.setInt(1, Id);
            numRows = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRows;
    }

    public static ResultSet getProducts () throws SQLException {
        Statement stmt = connection.createStatement();
        String sqlQuery = "SELECT * from inventory";
        ResultSet products = null;
        try {
            products = stmt.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ResultSet searchProducts (String search) throws SQLException {
        String sqlQuery = "SELECT * from inventory where Description like ? or Item like ? or SKU like ?";
        PreparedStatement stmt = null;
        ResultSet products = null;
        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, '%' + search + '%');
            stmt.setString(2, '%' + search + '%');
            stmt.setString(3, '%' + search + '%');
            products = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static int updateProduct (int id, String Item, String Description, String Status, String Category, int SKU, int Price, int qty, String addedBy) throws SQLException {
        int numRows = 0;
        PreparedStatement stmt = null;
        String sqlCommand = "SELECT * from inventory where id = ?";
        String sqlUpdateCommand = "UPDATE inventory SET Item = ?, Description = ?, Status = ?, Category = ?, SKU = ?, Price = ?, Qty = ?, addedBy = ? where id = ?";
        ResultSet product = null;
        ModelInventory mi = new ModelInventory();
        try {
            stmt = connection.prepareStatement(sqlCommand);
            stmt.setInt(1, id);
            product = stmt.executeQuery();
            while(product.next()) {
                mi.setId(product.getInt(1));
                mi.setItem(product.getString(2) == Item ? product.getString(2) : Item );
                mi.setDescription(product.getString(3) == Description ? product.getString(3) : Description );
                mi.setStatus(product.getString(4) == Status ? product.getString(4) : Status);
                mi.setCategory(product.getString(5) == Category ? product.getString(5) : Category);
                mi.setSKU(product.getInt(6) == SKU ? product.getInt(6) : SKU);
                mi.setPrice(product.getInt(7) == Price ? product.getInt(7) : Price);
                mi.setQty(product.getInt(8) == qty ? product.getInt(8) : qty);
                mi.setAddedBy(addedBy);
            }
            try {
                stmt = connection.prepareStatement(sqlUpdateCommand);
                stmt.setString(1, mi.getItem());
                stmt.setString(2, mi.getDescription());
                stmt.setString(3, mi.getStatus());
                stmt.setString(4, mi.getCategory());
                stmt.setInt(5, mi.getSKU());
                stmt.setInt(6, mi.getPrice());
                stmt.setInt(7, mi.getQty());
                stmt.setString(8, mi.getAddedBy());
                stmt.setInt(9, mi.getId());
                numRows = stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return numRows;
    }

    public static ResultSet filterProducts (Integer id, String item, String desc, String status, Integer price_low, Integer price_high, String category, String addedBy ) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * from inventory where ");
        if(id != null) sb.append("id = " + id + "? ");
        if( item != "") sb.append("Item like '%" + item + "'%? ");
        if( desc != "") sb.append("Description like '%" + desc + "'%? ");
        if(status != "") sb.append("Status = " + status + "? ");
        if(price_low != null && price_high != null) sb.append("(Price >= " + price_low + " and Price<= " + price_high + ")? ");
        //if(qty_low != null && qty_high != null) sb.append("(Qty >= " + qty_low + " and Qty <= " + qty_high + ")? ");
        if(category != "") sb.append("Category = '" + category + "'? ");
        if(addedBy != "") sb.append("addedBy = '" + addedBy + "'? ");
        String sqlQueryTemp = sb.toString();
        //replace question marks with and + &nbsp
        String sqlQuery2 = sqlQueryTemp.replace("?", " and");
        int index = sqlQuery2.lastIndexOf("and");
        String sqlQuery = sqlQuery2.substring(0,index).trim();
        System.out.println(sqlQuery);
        //String sqlQuery = sqlQueryTemp.replace("?", "? and ");
        Statement stmt = connection.createStatement();
        //PreparedStatement stmt = null;
        ResultSet products = null;
        try {
            products = stmt.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ResultSet getOrders() throws SQLException {
        ResultSet rs = null;
        Statement stmt = connection.createStatement();
        String sqlQuery = "Select * from orders";
        try {
            rs = stmt.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    /*public static int insertOrder(ArrayList<OrderItemsDataModel> Items, String soldTo, String vat, String status, String completedBy) throws SQLException {
        ResultSet rs = null;
        int numRows = 0;
        int n = 0;
        String sqlQuery = "Insert into orders (SoldTo, VAT, Status, CompletedOn, completedBy) values (?,?,?,?,?)";
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime warrantyEnd = date.plusMonths(36);
        try {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1,soldTo);
            stmt.setString(2, vat);
            stmt.setString(3, status);
            stmt.setString(4, formatter.format(date));
            stmt.setString(5, completedBy);
            numRows = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if(numRows > 0) {
            String updateSqlQuery = "Update inventory SET Qty = ? where SKU = ?";
            try {
                PreparedStatement st = connection.prepareStatement(updateSqlQuery);
                st.setInt(1, itemId);
                st.setInt(2, itemId);
                n = st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                numRows = 0;
                n = 0;
            }
        }
        if(numRows > 0 && n > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(completedBy);
            sb.append(" created an order for item: ");
            sb.append(itemId);
            String action = formatter.format(date);
            String qInsertHistory = "Insert into history (action, date) values(?,?)";
            try {
                PreparedStatement st = connection.prepareStatement(qInsertHistory);
                st.setString(1,sb.toString());
                st.setString(2, action);
                st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return numRows;
    } */

    public static ResultSet getAvailableProducts () throws SQLException {
        Statement stmt = connection.createStatement();
        String sqlQuery = "SELECT * from inventory where Qty > 0";
        ResultSet products = null;
        try {
            products = stmt.executeQuery(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static int updateOrder(int id, String soldTo, String vat, String status, String completedBy) throws SQLException {
        ResultSet rs = null;
        int numRows = 0;
        String sqlGetOrder = "SELECT * from orders where id = ?";
        String sqlUpdateCommand = "UPDATE orders SET SoldTo = ?, VAT = ?, Status = ?, completedBy = ? where id = ?)";
        OrdersDataModel od = new OrdersDataModel();
        try {
            PreparedStatement pm = connection.prepareStatement(sqlGetOrder);
            pm.setInt(1, id);
            ResultSet r = pm.executeQuery();
            while(r.next()) {
                od.setId(r.getInt(1));
                od.setItem(r.getString(2));
                od.setSoldTo(r.getString(3) == soldTo ? r.getString(3) : soldTo );
                od.setVAT(r.getString(4) == vat ? r.getString(4) : vat);
                od.setStatus(r.getString(5) == status ? r.getString(5) : status);
                od.setCompletedOn(r.getString(6));
                od.setWarrantyBegin(r.getString(7));
                od.setWarrantyEnd(r.getString(8));
                od.setCompletedBy(completedBy);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            PreparedStatement pmst = connection.prepareStatement(sqlUpdateCommand);
            pmst.setString(1, od.getSoldTo());
            pmst.setString(2, od.getVAT());
            pmst.setString(3, od.getStatus());
            pmst.setString(4, od.getCompletedBy());
            pmst.setInt(5, od.getId());
            numRows = pmst.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
            numRows = 0;
        }

        if(numRows > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(completedBy);
            sb.append(" updated  order: ");
            sb.append(id);
            sb.append(" . Status is ");
            sb.append(status);
            sb.append(". Sold To and VAT Are: ");
            sb.append(soldTo);
            sb.append(", ");
            sb.append(vat);
            LocalDateTime date = LocalDateTime.now();
            String action = formatter.format(date);
            String qInsertHistory = "Insert into history (action, date) values(?,?)";
            try {
                PreparedStatement st = connection.prepareStatement(qInsertHistory);
                st.setString(1,sb.toString());
                st.setString(2, action);
                st.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return numRows;
    }

    public static ResultSet searchOrders (String search) throws SQLException {
        String sqlQuery = "SELECT * from orders where SoldTo like ? or VAT like ?";
        PreparedStatement stmt = null;
        ResultSet products = null;
        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, '%' + search + '%');
            stmt.setString(2, '%' + search + '%');
            products = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public static ResultSet filterOrders (String dateStart, String dateEnd) throws SQLException {
        String sqlQuery = "SELECT * from orders where CompletedOn >= ? and CompletedOn <= ?";
        PreparedStatement stmt = null;
        ResultSet products = null;
        if(dateStart.split("-").length !=3 && dateEnd.split("-").length !=3) {
            return null;
        }
        try {
            stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, dateStart);
            stmt.setString(2, dateEnd);
            products = stmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


}
