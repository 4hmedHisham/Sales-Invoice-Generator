package Model;

import java.util.ArrayList;

public class InvoiceHeader {
    public void setInvoiceDate(String invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        Lines = lines;
    }
    int InvoiceNumber;

    public int getInvoiceNumber() {
        return InvoiceNumber;
    }

    public String getInvoiceDate() {
        return InvoiceDate;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public ArrayList<InvoiceLine> getLines() {
        return Lines;
    }

    public int getTotal() {
        return calculateTotal();

    }

    String InvoiceDate;
    String Customer_Name;
    ArrayList<InvoiceLine> Lines;

    public  InvoiceHeader(int invoiceNumber, String invoiceDate, String customer_Name,ArrayList<InvoiceLine> lines){
        this.InvoiceNumber=invoiceNumber;
        this.InvoiceDate=invoiceDate;
        this.Customer_Name=customer_Name;
        this.Lines=lines;

    }
    public int calculateTotal(){
        int sum=0;
        for (InvoiceLine line :Lines){
            sum=sum+(line.itemprice*line.count);
        }
        return sum;
    }
    public String getCustomername(){
        return Customer_Name;
    }
    public int GetInvoiceNumber(){
        return InvoiceNumber;
    }
    public void setInvoiceNumber(int x){
        this.InvoiceNumber=x;
        for (InvoiceLine line: getLines()){
            line.setInvoice_numer(this.getInvoiceNumber());
        }

    }

}
