package FileOperations;

import Model.DataManipulator;
import Model.InvoiceHeader;
import Model.InvoiceLine;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DataWriteIO {



    public DataWriteIO(String HeaderPath, String InvoicePath,ArrayList<InvoiceHeader> Headers){
        try{
            FileWriter file = new FileWriter(HeaderPath);//"C:\\Users\\Ahmed\\Downloads\\SIG\\test1.csv"
            PrintWriter write = new PrintWriter(file);

            for (InvoiceHeader singleheader:Headers){
                write.append(String.valueOf(singleheader.getInvoiceNumber()));
                write.append(",");
                write.append(singleheader.getInvoiceDate());
                write.append(",");
                write.append(singleheader.getCustomer_Name());
                write.append(",");
                write.append(String.valueOf(singleheader.getTotal()));
                write.append("\r\n");
            }
            write.close();
                } catch (IOException e) {
            System.out.println("Cannot create file");
        }

        try{
            FileWriter file = new FileWriter(InvoicePath);//"C:\\Users\\Ahmed\\Downloads\\SIG\\test1.csv"
            PrintWriter write = new PrintWriter(file);

            for (InvoiceHeader singleheader:Headers){
               for(InvoiceLine singleline: singleheader.getLines()){
                   write.append(String.valueOf(singleline.getInvoice_numer()));
                   write.append(",");
                   write.append(singleline.getItemname());
                   write.append(",");
                   write.append(String.valueOf(singleline.getItemprice()));
                   write.append(",");
                   write.append(String.valueOf(singleline.getCount()));
                   write.append("\r\n");
               }

            }
            write.close();
        }
        catch( IOException exe){
            System.out.println("Cannot create file");
        }
    }

}
