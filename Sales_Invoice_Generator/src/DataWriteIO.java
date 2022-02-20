import javafx.animation.ScaleTransition;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DataWriteIO {

DataWriteIO(String HeaderPath,String InvoicePath, List<ArrayList<String>> Header,List<ArrayList<ArrayList<String>>> Invoice){
    try{
        FileWriter file = new FileWriter(HeaderPath);//"C:\\Users\\Ahmed\\Downloads\\SIG\\test1.csv"
        PrintWriter write = new PrintWriter(file);
        for (ArrayList<String> line: Header){
            for(String element: line) {
                write.append(element);
                write.append(",");
            }
            write.append("\r\n");
        }

        write.close();


        FileWriter file2 = new FileWriter(InvoicePath);//"C:\\Users\\Ahmed\\Downloads\\SIG\\test2.csv"
        PrintWriter write2 = new PrintWriter(file2);
        for (ArrayList<ArrayList<String>> InvoiceNumber: Invoice){
            for(ArrayList<String> line: InvoiceNumber) {
                int elementindx=0;
                for(String element : line){
                    elementindx++;
                    write2.append(element);
                    write2.append(",");
                    if(elementindx==3){
                        break;
                    }
                }
                write2.append("\r\n");
            }

        }

        write2.close();
    }
    catch( IOException exe){
        System.out.println("Cannot create file");
    }
}

}
