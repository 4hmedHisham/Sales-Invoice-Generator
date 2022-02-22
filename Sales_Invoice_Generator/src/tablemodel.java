import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
class tablemodel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//List<List> data;
    String[] columnNames;
    List<String> items;
    List<ArrayList<String>> data;
    DataStoreIO streamer;
    String fulldata;
    String path;
    public tablemodel(String [] colNames,List<ArrayList<String>> dataout) {
    	data= new ArrayList<ArrayList<String>>();
    	columnNames=colNames;
    	data=dataout;
    }
    public List<ArrayList<String>> getData(){
    	return data;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        try{
            return data.get(0).size();
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public int getRowCount() {
        try{
            return data.size();
        }
        catch(Exception e2){
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            if(rowIndex<data.size() && columnIndex<data.get(0).size()&& (data.get(0).get(0) !="")) {
                return data.get(rowIndex).get(columnIndex);
            }
            else {
                return " ";
            }
        }
        catch(Exception e) {
            return " ";

        }
    }

        public void setValueAt(Object value, int row, int col) {

            if(row<data.size() && col<data.get(0).size()&& (data.get(0).get(0) !="")) {
              data.get(row).set(col,(String) value);
                fireTableCellUpdated(row, col);
            }

    }
    public boolean isCellEditable(int row, int column)
    {
        if (columnNames[column].equals("No.") ||columnNames[column].equals("Total")|| columnNames[column].equals("Item Total") ||columnNames[column].equals("Date")||columnNames[column].equals("Customer"))
        {
            return  false;
        }
        else{
            return true;
        }
    }


}