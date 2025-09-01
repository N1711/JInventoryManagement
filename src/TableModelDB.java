import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class TableModelDB extends AbstractTableModel {

    public static final String[] COLUMN_NAMES  = {"id","Item","Description", "Status", "Category", "SKU", "SerialNumber", "Price", "Added By"};

    public static List<DBDataModel> data = new ArrayList<>();

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void addData(DBDataModel _data){
        data.add(_data);
        fireTableRowsInserted(0, data.size() - 1);
    }

    public void removeRowAt(int id) {
        int index =  -1;
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getId() == id) {
                index = i;
            }
        }
        data.remove(index);
        //fireTableDataChanged();
        fireTableRowsDeleted(index - 1, data.size() - 1);
    }


    public void blankData() {
        int rows = getRowCount();
        if(rows == 0) {
            return;
        }
        data.clear();
        fireTableRowsDeleted(0, rows - 1);
    }

    @Override
    public Object getValueAt(int row, int column) {
        DBDataModel dt = data.get(row);

        switch(column){
            case 0: return dt.getId();
            case 1: return dt.getItem();
            case 2: return dt.getDescription();
            case 3: return dt.getStatus();
            case 4: return dt.getCategory();
            case 5: return dt.getSKU();
            case 6: return dt.getSerialNumber();
            case 7: return dt.getPrice();
            case 8: return dt.getAddedBy();
            default : return null;
        }

    }

    public boolean isCellEditable(int row, int col)
    { return true; }
}
