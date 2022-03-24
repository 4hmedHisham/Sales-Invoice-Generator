package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DataManipulator {
    public static List<ArrayList<String>> ItemsDeatilsData;
    public  static List<ArrayList<ArrayList<String>>> ItemsDeatilsDataSorted;
    public static List<ArrayList<String>> HeaderData;
    public static String [] emptyright={"","","","",""};
    public enum DetailsEnum {
    	  No,
    	  Item_Name,
          Item_Price,
          Count,
          itemtotal
    	}

    public enum HeaderEnum {
        No,
        Date,
        CusomterName,
        Total
    }
    public static  int GetSum(int highlightedrow){
        int sum=0;
        for(ArrayList<String> row : DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow))
        {
            try{
                int itemTotalPrice=(Integer.parseInt(row.get(DataManipulator.DetailsEnum.Item_Price.ordinal())))*(Integer.parseInt(row.get(DataManipulator.DetailsEnum.Count.ordinal())));

                sum=sum+itemTotalPrice;
            }
            catch (Exception e5){

            }
        }
        HeaderData.get(highlightedrow).set(HeaderEnum.Total.ordinal(),String.valueOf(sum));
        return sum;
    }
    public static void UpdateTotalPrice(int highlightedrow, int row,  JTable invoice_table){
        List<ArrayList<String>> rightlist=new ArrayList<ArrayList<String>>();
        for(ArrayList<String> row1 : DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow)) {
            ArrayList<String>  rowCopied=new ArrayList(row1);
            String leftcomp=rowCopied.get(0);
            String rightcomp=(String) invoice_table.getValueAt(row, DataManipulator.HeaderEnum.No.ordinal());
            int TotalItemPrice=0;
            if(leftcomp.equals(rightcomp)) {
                int itemPrice=Integer.parseInt(rowCopied.get(DataManipulator.DetailsEnum.Item_Price.ordinal()));
                int itemCount=Integer.parseInt(rowCopied.get(DataManipulator.DetailsEnum.Count.ordinal()));

                try {
                    TotalItemPrice=itemPrice*itemCount;
                }
                catch (Exception e3){
                    TotalItemPrice=0;
                }
                try {
                    row1.set(4,String.valueOf(TotalItemPrice));


                }
                catch (Exception e4){
                    row1.add(String.valueOf(TotalItemPrice));
                }

                rowCopied.add(String.valueOf(TotalItemPrice));
                rightlist.add(rowCopied);
            }
        }
    }
}
