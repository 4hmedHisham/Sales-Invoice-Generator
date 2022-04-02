package FileOperations;

import Model.DataManipulator;
import Model.InvoiceHeader;
import Model.InvoiceLine;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataParser {

    public static void parseHeader(String path) throws IOException {
        DataStoreIO streamer = new DataStoreIO(path);

        List<String> HeaderLineData;
        String fulldata =streamer.fulldata;
        System.out.println(fulldata);
        HeaderLineData= Arrays.asList(fulldata.split("\r\n"));

        for (String item : HeaderLineData) {
            ArrayList<InvoiceLine> lines= new ArrayList<InvoiceLine>();
            List<String> HeaderElements=new ArrayList<String>(Arrays.asList(item.split(",")));


            int sum=0;
            //making total value

            for(InvoiceLine singleline : DataManipulator.GlobalUnsortedLines){
                if(singleline.getInvoice_numer() == Integer.parseInt(HeaderElements.get(0))){
                    int itemPrice=singleline.getItemprice();
                    int itemCount=singleline.getCount();
                    sum=sum+itemPrice*itemCount;

                    lines.add(singleline);
                }

            }

            DataManipulator.GlobalHeader.add(new InvoiceHeader(Integer.parseInt(HeaderElements.get(0)), HeaderElements.get(1),HeaderElements.get(2),new ArrayList<InvoiceLine>(lines)));
            //DataManipulator.ItemsDeatilsDataSorted.add(new ArrayList<ArrayList<String>>(currentHeaderItems));




        }
    }
    public static void parseLines(String path) throws IOException {
        List<String> items;
        //DataStoreIO streamer;
        DataStoreIO streamer = new DataStoreIO(path);
        String fulldata =streamer.fulldata;
        System.out.println(fulldata);
        items= Arrays.asList(fulldata.split("\r\n"));
        DataManipulator.GlobalUnsortedLines=new ArrayList<InvoiceLine>();
        for (String item : items) {
            List<String> HeaderLine=new ArrayList<String>(Arrays.asList(item.split(",")));

            DataManipulator.GlobalUnsortedLines.add(new InvoiceLine(Integer.parseInt(HeaderLine.get(0)),HeaderLine.get(1),Integer.parseInt(HeaderLine.get(2)),Integer.parseInt(HeaderLine.get(3))));
            //DataManipulator.ItemsDeatilsData.add(new ArrayList<String> (HeaderLine));


        }
        DataManipulator.GlobalUnsortedLines.add(new InvoiceLine(0,"",0,0));
        //DataManipulator.ItemsDeatilsData.add(new ArrayList<String> (Arrays.asList(DataManipulator.emptyright)));
    }
}
