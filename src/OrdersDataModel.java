public class OrdersDataModel {
    int id;
    String Item;
    String SoldTo;
    String VAT;
    String SKU;
    String SerialNumber;
    String Status;
    String CompletedOn;
    String WarrantyBegin;
    String WarrantyEnd;
    String completedBy;

    public int getId() {
        return this.id;
    }
    public void setId(int _id) {
        this.id = _id;
    }

    public String getItem() {
        return this.Item;
    }

    public void setItem(String _itemId) {
        this.Item = _itemId;
    }

    public String getSKU() {
        return this.SKU;
    }

    public void setSKU(String _sku) {
        this.SKU = _sku;
    }

    public String getSerialNumber() {
        return this.SerialNumber;
    }

    public void setSerialNumber(String _sn) {
        this.SerialNumber = _sn;
    }

    public String getSoldTo() {
        return this.SoldTo;
    }

    public void setSoldTo(String _sold) {
        this.SoldTo = _sold;
    }

    public String getVAT() {
        return this.VAT;
    }

    public void setVAT(String _vat) {
        this.VAT = _vat;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setStatus(String _status) {
        this.Status = _status;
    }

    public String getCompletedOn() {
        return this.CompletedOn;
    }

    public void setCompletedOn(String _completed) {
        this.CompletedOn = _completed;
    }

    public String getWarrantyBegin() {
        return this.WarrantyBegin;
    }

    public void setWarrantyBegin(String _warrB) {
        this.WarrantyBegin = _warrB;
    }

    public String getWarrantyEnd() {
        return this.WarrantyEnd;
    }

    public void setWarrantyEnd(String _warrE) {
        this.WarrantyEnd = _warrE;
    }

    public String getCompletedBy() {
        return this.completedBy;
    }

    public void setCompletedBy(String _c) {
        this.completedBy = _c;
    }
}
