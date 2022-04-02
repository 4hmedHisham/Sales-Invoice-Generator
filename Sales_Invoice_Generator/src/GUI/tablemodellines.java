package GUI;

import FileOperations.DataStoreIO;
import Model.InvoiceHeader;
import Model.InvoiceLine;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

class tablemodellines extends AbstractTableModel {

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
    ArrayList<InvoiceLine> tablemodellines;
    String path;
    int colCount;

    public tablemodellines(String [] colNames, ArrayList<InvoiceLine> lines){
        columnNames=colNames;
        tablemodellines=new ArrayList<InvoiceLine>();
        tablemodellines=lines;
        colCount=4;
    }
//    public List<ArrayList<String>> getData(){
//    	return data;
//    }
    public ArrayList<InvoiceLine> getData(){return tablemodellines;}
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
            return tablemodellines.size();
        }
        catch(Exception e2){
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            if(rowIndex<tablemodellines.size() && columnIndex<colCount&& (tablemodellines!=null)) {
//                return data.get(rowIndex).get(columnIndex);
                InvoiceLine single_header=tablemodellines.get(rowIndex);
                if(columnIndex==0){
                    return single_header.getInvoice_numer();
                }
                else if (columnIndex==1){
                    return single_header.getItemname();
                }
                else if(columnIndex==2){
                    return single_header.getItemprice();
                }
                else if(columnIndex==3){
                    return single_header.getCount();

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

            if(row<tablemodellines.size() && col<colCount&& (tablemodellines!=null)) {
                InvoiceLine single_header=tablemodellines.get(row);
                if(col==0){
                    single_header.setInvoice_numer((int)value);
                }
                else if (col==1){
                    single_header.setItemname((String)value);
                }
                else if(col==2){
                     single_header.setItemprice(Integer.parseInt((String)value));
                }
                else if(col==3){
                     single_header.setCount(Integer.parseInt((String)value));

                }
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