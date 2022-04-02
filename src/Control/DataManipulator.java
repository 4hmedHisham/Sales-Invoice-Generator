package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DataManipulator {

    public static List<InvoiceLine> ItemsDetailsDataRev;
    public static List<ArrayList<String>> ItemsDeatilsData;
    public static  ArrayList<InvoiceHeader> GlobalHeader;
    public static ArrayList<InvoiceLine> GlobalUnsortedLines= new ArrayList<InvoiceLine>();
    public  static List<ArrayList<ArrayList<String>>> ItemsDeatilsDataSorted;

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
    public static int  GetSumRev(int highlightedrow){
        return ItemsDetailsDataRev.get(highlightedrow).count*ItemsDetailsDataRev.get(highlightedrow).itemprice;
    }
    public static  int GetSum(int highlightedrow){

        return DataManipulator.GlobalHeader.get(highlightedrow).getTotal();
    }
}
