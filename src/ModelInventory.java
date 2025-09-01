/**
 * Initializes a new data model before inserting into the database.
 * Internal variables replicate the column names in the database.
 */

public class ModelInventory {
    /**
     * @setId - obsolete, shouldn't be used as id is generated from SQLite
     * @getId - integer returns the id in integer
     * @getItem - String returns the item name
     * @setItem - void sets the Item name
     * @getDescription - Strings returns the Description of the item
     * @setDescription - void sets the Description of the item
     * @setStatus - void sets the status of the item. Could be any but used as Available or sold
     * @getStatus - String returns the status of the item
     * @getPrice - Integer returns the price
     * @setPrice - void sets the price in Integer
     * @getQty - Integer returns the qty - do not use!!! obsolete
     * @setQty - void sets the Qty do not use!!! Obsolete as of ver 1.2
     * @getCategory - String returns the item category
     * @setCategoty - void sets the item category. Could be any
     * @getSKU - Integer returns the item SKU
     * @setSKU - void sets the item SKU
     * @getSerialNumber - String returns the Serial Number of the item if any
     * @setSerialNumber - void sets the serial number. Could be blank for items with no SN.
     * @getAddedBy - String returns the user inserting the record
     * @setAddedBy - void sets the addedBy value (the logged in user by default).
     */

    int id;
    String Item;
    String Description;
    String Status;
    int Price;
    int Qty;
    String Category;
    String addedBy;
    int SKU;
    String SerialNumber;

    public ModelInventory() {

    }

    public void setId (int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setItem (String Item) {
        this.Item = Item;
    }

    public String getItem() {
        return this.Item;
    }

    public void setDescription (String Description) {
        this.Description = Description;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setPrice (int Price) {
        this.Price = Price;
    }

    public int getPrice () {
        return this.Price;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }

    public int getQty() {
        return this.Qty;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setAddedBy(String addedBy) {this.addedBy = addedBy;}
    public String getAddedBy() {return this.addedBy;}

    public int getSKU() {
        return this.SKU;
    }

    public void setSKU(int _SKU) {
        this.SKU = _SKU;
    }

    public String getSerialNumber() {
        return this.SerialNumber;
    }

    public void setSerialNumber(String _SerialNumber) {
        this.SerialNumber = _SerialNumber;
    }
}
