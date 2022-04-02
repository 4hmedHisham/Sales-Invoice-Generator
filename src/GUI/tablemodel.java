package GUI;

import FileOperations.DataStoreIO;
import Model.InvoiceHeader;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

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
    ArrayList<InvoiceHeader> tablemodelHeaders;
    String path;
    int colCount;

    public tablemodel(String [] colNames, ArrayList<InvoiceHeader> headers){
        columnNames=colNames;
        tablemodelHeaders=new ArrayList<InvoiceHeader>();
        tablemodelHeaders=headers;
        colCount=4;
    }
//    public List<ArrayList<String>> getData(){
//    	return data;
//    }
    public ArrayList<InvoiceHeader> getData(){return tablemodelHeaders;}
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        try{
            return colCount;
        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public int getRowCount() {
        try{
            return tablemodelHeaders.size();
        }
        catch(Exception e2){
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            if(rowIndex<tablemodelHeaders.size() && columnIndex<colCount&& (tablemodelHeaders!=null)) {
//                return data.get(rowIndex).get(columnIndex);
                InvoiceHeader single_header=tablemodelHeaders.get(rowIndex);
                if(columnIndex==0){
                    return single_header.getInvoiceNumber();
                }
                else if (columnIndex==1){
                    return single_header.getInvoiceDate();
                }
                else if(columnIndex==2){
                    return single_header.getCustomer_Name();
                }
                else if(columnIndex==3){
                    return single_header.getTotal();

                }
                else{
                    throw new Exception("Invalid Column!!");
                }

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

            if(row<tablemodelHeaders.size() && col<colCount&& (tablemodelHeaders!=null)) {
                InvoiceHeader single_header=tablemodelHeaders.get(row);
                if(col==0){
                    single_header.setInvoiceNumber((int)(value));
                }
                else if (col==1){
                    single_header.setInvoiceDate((String)(value));
                }
                else if(col==2){
                     single_header.setCustomer_Name((String)value);
                }
//                else if(col==3){
//                     single_header.setTotal((int)value);
//
//                }
                else{
                     new Exception("Invalid Column!!");
                }

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