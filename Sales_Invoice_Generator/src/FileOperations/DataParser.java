package FileOperations;

import Model.DataManipulator;

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
            List<String> HeaderElements=new ArrayList<String>(Arrays.asList(item.split(",")));
            List<ArrayList<String>> currentHeaderItems= new ArrayList<ArrayList<String>>();
            int sum=0;
            //making total value
            for(ArrayList<String> itemdetail : DataManipulator.ItemsDeatilsData){
                if(itemdetail.get(DataManipulator.DetailsEnum.No.ordinal()).equals(HeaderElements.get(DataManipulator.DetailsEnum.No.ordinal()))){
                    int itemPrice=Integer.parseInt(itemdetail.get(DataManipulator.DetailsEnum.Item_Price.ordinal()));
                    int itemCount=Integer.parseInt(itemdetail.get(DataManipulator.DetailsEnum.Count.ordinal()));
                    sum=sum+itemPrice*itemCount;
                    currentHeaderItems.add(itemdetail);

                }

            }
            DataManipulator.ItemsDeatilsDataSorted.add(new ArrayList<ArrayList<String>>(currentHeaderItems));
            //HeaderElements.add(String.valueOf(sum));
            DataManipulator.HeaderData.add(new ArrayList<String> (HeaderElements));



        }
    }
    public static void parseLines(String path) throws IOException {
        List<String> items;
        //DataStoreIO streamer;
        DataStoreIO streamer = new DataStoreIO(path);
        String fulldata =streamer.fulldata;
        System.out.println(fulldata);
        items= Arrays.asList(fulldata.split("\r\n"));
        for (String item : items) {
            List<String> HeaderLine=new ArrayList<String>(Arrays.asList(item.split(",")));


            DataManipulator.ItemsDeatilsData.add(new ArrayList<String> (HeaderLine));


        }
        DataManipulator.ItemsDeatilsData.add(new ArrayList<String> (Arrays.asList(DataManipulator.emptyright)));
    }
}
