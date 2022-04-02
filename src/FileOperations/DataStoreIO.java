package FileOperations;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataStoreIO {
private String CurrentLine;
String filepath;
public String fulldata;
FileInputStream fis;
public DataStoreIO(String inputpath) throws IOException {
     filepath=inputpath;
    fis = new FileInputStream(filepath);
    int size = fis.available();
    byte [] b = new byte[size];
    fis.read(b);
     fulldata=new String(b);

    //System.out.println(fulldata);

}


}
