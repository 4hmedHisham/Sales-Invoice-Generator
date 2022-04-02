package Model;

public class InvoiceLine {
    int invoice_numer;

    public void setInvoice_numer(int invoice_numer) {
        this.invoice_numer = invoice_numer;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setItemprice(int itemprice) {
        this.itemprice = itemprice;
    }

    public void setCount(int count) {
        this.count = count;
    }



    String itemname;

    public int getInvoice_numer() {
        return invoice_numer;
    }

    public String getItemname() {
        return itemname;
    }

    public int getItemprice() {
        return itemprice;
    }

    public int getCount() {
        return count;
    }

    public int getSum() {

        return itemprice*count;
    }

    int itemprice;
    int count;

    public  InvoiceLine(int invoice_numer,String itemname, int itemprice,int count){
        this.invoice_numer=invoice_numer;
        this.itemname=itemname;
        this.itemprice=itemprice;
        this.count=count;

    }
}
