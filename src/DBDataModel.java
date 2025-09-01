public class DBDataModel {
    int id;
    String Item;
    String Description;
    String Status;
    int Price;
    String Category;
    String addedBy;
    int SKU;
    String SerialNumber;

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getItem() {
        return this.Item;
    }

    public void setItem(String _item) {
        this.Item = _item;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String _desc) {
        this.Description = _desc;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String _status) {
        this.Status = _status;
    }

    public int getPrice() {
        return this.Price;
    }

    public void setPrice(int _price) {
        this.Price = _price;
    }

    public String getCategory() {
        return this.Category;
    }

    public void setCategory(String _category) {
        this.Category = _category;
    }

    public String getAddedBy() {
        return this.addedBy;
    }

    public void setAddedBy(String _addedby) {
        this.addedBy = _addedby;
    }

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
