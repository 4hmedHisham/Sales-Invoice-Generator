package GUI;

import FileOperations.DataParser;
import FileOperations.DataStoreIO;
import FileOperations.DataWriteIO;
import Model.DataManipulator;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;



public class GUI extends JFrame implements ActionListener {
    public class MyRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if(table.getColumnName(column).equals("No.")  ||table.getColumnName(column).equals("Total")||table.getColumnName(column).equals("Item Total")||table.getColumnName(column).equals("Date")||table.getColumnName(column).equals("Customer"))
                c.setBackground( Color.LIGHT_GRAY);
            else
                c.setBackground(table.getBackground());

            return c;
        }

    }
    private boolean IsReset=true;

    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JPanel inoviceNumberPanel;
    private JPanel invoiceDatePanel;
    private JPanel CustomerNamePanel;
    private JPanel invoiceTotalPanel;
    private JPanel LeftButtonPanel;
    private JPanel RightUpperTitlesPanel;
    private JPanel RighterUpperDataPanel;
    private JPanel CombinedRightUpperPanel;
    private JPanel RightButtonPanel;


    private JButton CreateNewInvoice;
    private JButton DeleteInvoice;
    private JButton SaveButton;
    private JButton CancelButton;
    private JButton AddNewItem;
    
    
    private JLabel invoiceTabelLabel;
    private JLabel invoiceNumber;
    private JLabel invoiceNumber_title;
    private JLabel invoiceDateLabel;
    private JLabel CustomerNameLabel;
    private JLabel invoiceTotalLabel;
    private JLabel Total_items_label_right_side;
    private JLabel invoiceItems;


    private JTable invoice_table;
    private JTable invoiceItemsTable;
    private JScrollPane scroll2;
    private JScrollPane scroll1;


    private int invoice_Number;
    private int invoicetotal;

    private JTextField invoiceDate;
    private JTextField CustomerName;
    private JMenuBar filemenubar;
    private JMenu file;
    private JMenuItem savefile;
    private JMenuItem loadfile;
    private int highlightedrow=-1;
    String HeaderPath;
    String InvoicePath;
    int CurrentRowsAdded=0;
    int prevHighlighed=0;

    String fulldata;
    private String [] cols2 ={"No.","Item Name","Item Price","Count","Item Total"};
    String [] colNames=  {"No.", "Date","Customer","Total"};

    String [] emptyleft={"","","",""};



    
    String InvoiceNumber;

    tablemodel HeaderTableModel;
    tablemodel DetailsTableModel;

    void update_InvoiceLine_model(){
        DetailsTableModel= new tablemodel(cols2,DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow));

        invoiceItemsTable.setModel(DetailsTableModel);
        invoiceItemsTable.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                // your code goes here, whatever you want to do when something changes in the table
                tablemodel modelx= (tablemodel) e.getSource();
                String dataToReplace =(String) modelx.getValueAt(e.getFirstRow(),e.getColumn());
                DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).get(e.getFirstRow()).set(e.getColumn(),dataToReplace);
                int itemprice=0;
                try{
                    itemprice =Integer.parseInt((String) DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).get(e.getFirstRow()).get(DataManipulator.DetailsEnum.Item_Price.ordinal()));

                }
                catch (Exception ee){}
                int item_count=0;
                try{
                    item_count =Integer.parseInt((String) DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).get(e.getFirstRow()).get(DataManipulator.DetailsEnum.Count.ordinal()));
                }
                catch (Exception eee){}
                modelx.removeTableModelListener(this);
                modelx.setValueAt(String.valueOf(item_count*itemprice),e.getFirstRow(), DataManipulator.DetailsEnum.itemtotal.ordinal());
                modelx.addTableModelListener(this);


                int sum=DataManipulator.GetSum(highlightedrow);


                Total_items_label_right_side.setText(String.valueOf(sum));
                CustomerName.setText(DataManipulator.HeaderData.get(highlightedrow).get(DataManipulator.HeaderEnum.CusomterName.ordinal()));



                HeaderTableModel=new tablemodel(colNames,DataManipulator.HeaderData);
                invoice_table.setModel(HeaderTableModel);
                invoice_table.getModel().addTableModelListener(new TableModelListener() {

                    @Override
                    public void tableChanged(TableModelEvent e) {
                        int sum=DataManipulator.GetSum(highlightedrow);
                        Total_items_label_right_side.setText(String.valueOf(sum));
                        CustomerName.setText(DataManipulator.HeaderData.get(highlightedrow).get(DataManipulator.HeaderEnum.CusomterName.ordinal()));
                        if(e.getColumn()== DataManipulator.HeaderEnum.Date.ordinal()){

                                SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
                                try {
                                    Date today= formatter2.parse("x-11-2022");
                                } catch (ParseException parseException) {
                                    Object x =e.getSource();
//                                    JOptionPane.showMessageDialog(,
//                                            "Bad Date Formatting!");
                                }


                        }
                    }
                });



            }
        });
    }
    public GUI(){
        super("Sales Invoice Generator");

        InvoiceNumber="INIT";

        LeftPanel = new JPanel();

        //invoice_Number=23;
        DataManipulator.HeaderData = new ArrayList<ArrayList<String>>();
        //String [] emptyleft={" "," "," "," "};
        //listoflists.add(new ArrayList<String> (Arrays.asList(emptyleft)));
        HeaderTableModel= new tablemodel(colNames, DataManipulator.HeaderData);

        invoice_table=new JTable();
        //invoice_table.setModel(HeaderTableModel);
        MyRenderer myRenderer = new MyRenderer();
        invoice_table.setDefaultRenderer(Object.class, myRenderer);
        invoice_table.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        invoiceTabelLabel=new JLabel("InvoicesTable");
        invoiceTabelLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        CreateNewInvoice = new JButton("Create New Invoice");
        CreateNewInvoice.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        CreateNewInvoice.setActionCommand("Create");
        CreateNewInvoice.addActionListener(this);
        DeleteInvoice = new JButton("Delete Invoice");
        DeleteInvoice.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        DeleteInvoice.addActionListener(this);
        DeleteInvoice.setActionCommand("DeleteInvoice");
        LeftButtonPanel=new JPanel();
        LeftButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        LeftButtonPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        LeftButtonPanel.add(CreateNewInvoice);
        LeftButtonPanel.add(DeleteInvoice);
        scroll2=new JScrollPane(invoice_table);
        scroll2.setAlignmentX(JLabel.LEFT_ALIGNMENT);

        //LeftPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        //LeftPanel.setBackground(Color.red);

        LeftPanel.setLayout(new BoxLayout(LeftPanel,BoxLayout.Y_AXIS));
        LeftPanel.add(invoiceTabelLabel);
        LeftPanel.add(scroll2);
        LeftPanel.add(LeftButtonPanel);



        RightPanel = new JPanel();
        //RightPanel.setBackground(Color.blue);
        //b1=new JButton("SUPB");
        RightPanel.setLayout(new BoxLayout(RightPanel,BoxLayout.Y_AXIS));


        inoviceNumberPanel = new JPanel();
        //inoviceNumberPanel.setBackground(Color.YELLOW);
        inoviceNumberPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        invoiceNumber_title= new JLabel("Invoice Number");
        invoiceNumber=new JLabel(InvoiceNumber);
        inoviceNumberPanel.add(invoiceNumber_title);
        inoviceNumberPanel.add(invoiceNumber);

        invoiceDateLabel=new JLabel("Invoice Date");
        invoiceDate = new JTextField(15);
        invoiceDate.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        invoiceDatePanel = new JPanel();
        invoiceDatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        invoiceDatePanel.add(invoiceDateLabel);
        invoiceDatePanel.add(invoiceDate);




        CustomerNameLabel= new JLabel("Customer Name");
        CustomerNamePanel = new JPanel();
        CustomerName = new JTextField("");
        CustomerName.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        CustomerNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        CustomerNamePanel.add(CustomerNameLabel);
        CustomerNamePanel.add(CustomerName);

        invoiceTotalLabel=new JLabel("Invoice Total");
        invoicetotal=300;
        Total_items_label_right_side = new JLabel("INIT");
        Total_items_label_right_side.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        invoiceTotalPanel=new JPanel();
        invoiceTotalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        invoiceTotalPanel.add(invoiceTotalLabel);
        invoiceTotalPanel.add(Total_items_label_right_side);

        RightUpperTitlesPanel= new JPanel();
        //RightUpperTitlesPanel.setBackground(Color.red);
        RightUpperTitlesPanel.setLayout(new BoxLayout(RightUpperTitlesPanel,BoxLayout.Y_AXIS));
        RightUpperTitlesPanel.add(invoiceNumber_title);
        RightUpperTitlesPanel.add(invoiceDateLabel);
        RightUpperTitlesPanel.add(CustomerNameLabel);
        RightUpperTitlesPanel.add(invoiceTotalLabel);



        RighterUpperDataPanel= new JPanel();
        //RighterUpperDataPanel.setBackground(Color.BLUE);
        RighterUpperDataPanel.setLayout(new BoxLayout(RighterUpperDataPanel,BoxLayout.Y_AXIS));
        RighterUpperDataPanel.add(invoiceNumber);
        RighterUpperDataPanel.add(invoiceDate);
        RighterUpperDataPanel.add(CustomerName);
        RighterUpperDataPanel.add(Total_items_label_right_side);



        CombinedRightUpperPanel=new JPanel();
        CombinedRightUpperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //CombinedRightUpperPanel.add(invoiceDatePanel);
        CombinedRightUpperPanel.add(RightUpperTitlesPanel);
        CombinedRightUpperPanel.add(RighterUpperDataPanel);





        invoiceItems=new JLabel("Invoice Items");



        invoiceItemsTable = new JTable();

        DataManipulator.ItemsDeatilsData = new ArrayList<ArrayList<String>>();

        //listoflists2.add(new ArrayList<String> (Arrays.asList(emptyright)));
        String []  ColsRight= {"No.","Item Name","Item Price","Count","Item Total"};
        tablemodel DataDetailsTableModel = new tablemodel(ColsRight, DataManipulator.ItemsDeatilsData);

        //invoiceItemsTable.setModel(HeaderTableModel);
        invoiceItemsTable.setDefaultRenderer(Object.class, myRenderer);

        SaveButton = new JButton("Save Current Header/Details status");
        SaveButton.addActionListener(this);
        SaveButton.setActionCommand("LocalSave");
        CancelButton=new JButton("Cancel");
        CancelButton.addActionListener(this);
        CancelButton.setActionCommand("Cancel");
        AddNewItem= new JButton("Add New Item");
        AddNewItem.addActionListener(this);
        AddNewItem.setActionCommand("AddItem");
        RightButtonPanel= new JPanel();
        RightButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        RightButtonPanel.add(SaveButton);
        RightButtonPanel.add(CancelButton);
        RightButtonPanel.add(AddNewItem);

        //bluePanel.add(b1);
//        RightPanel.add(inoviceNumberPanel);
        RightPanel.add(invoiceDatePanel);
//        RightPanel.add(CustomerNamePanel);
//        RightPanel.add(invoiceTotalPanel);
        CombinedRightUpperPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        invoiceItems.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        scroll1=new JScrollPane(invoiceItemsTable);
        scroll1.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        RightButtonPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        RightPanel.add(CombinedRightUpperPanel);
        RightPanel.add(invoiceItems);
        RightPanel.add(scroll1);
        RightPanel.add(RightButtonPanel);




        filemenubar=new JMenuBar();
        file= new JMenu("File");
        savefile=new JMenuItem("Save");
        savefile.setActionCommand("Save");
        savefile.addActionListener(this);
        loadfile = new JMenuItem("Load");

        file.add(savefile);
        file.add(loadfile);
        filemenubar.add(file);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JPanel bigPanel= new JPanel();
        bigPanel.add(LeftPanel);
        bigPanel.add(RightPanel);
        setSize(400,400);


        filemenubar.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        bigPanel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        add(filemenubar);
        add(bigPanel);


        setVisible(true);
        pack();



        loadfile.addActionListener(this);
        loadfile.setActionCommand("l");

        invoice_table.addMouseListener(new MouseAdapter() {
        	  public void mouseClicked(MouseEvent e) {
        		    if (e.getClickCount() == 1) {

        		      JTable target = (JTable)e.getSource();
        		      int row = target.getSelectedRow();
        		      prevHighlighed=highlightedrow;
        		      highlightedrow=row;
        		      InvoiceNumber=(String) invoice_table.getValueAt(row, DataManipulator.HeaderEnum.No.ordinal());
        		      invoiceNumber.setText(InvoiceNumber);
        		      CustomerName.setText((String) invoice_table.getValueAt(row, DataManipulator.HeaderEnum.CusomterName.ordinal()));
        		      invoiceDate.setText((String) invoice_table.getValueAt(row, DataManipulator.HeaderEnum.Date.ordinal()));
        		     try {
        		    	 Total_items_label_right_side.setText((String) invoice_table.getValueAt(row, DataManipulator.HeaderEnum.Total.ordinal()));
        		     }
        		     catch (Exception e1) {
						// TODO: handle exception
        		    	 Total_items_label_right_side.setText("0");
					}
        		     try{
                       //updatetotalPrice
                         DataManipulator.UpdateTotalPrice(highlightedrow,row,invoice_table);
                         update_InvoiceLine_model();
                     }
        		     catch (Exception ex){
                         DetailsTableModel=new tablemodel(cols2,new ArrayList<ArrayList<String>>());
                         invoiceItemsTable.setModel(DetailsTableModel);
                         update_InvoiceLine_model();
                     }

        		        while(CurrentRowsAdded>0 ){
        		            if(prevHighlighed!= -1){
                                DataManipulator.ItemsDeatilsDataSorted.get(prevHighlighed).remove((DataManipulator.ItemsDeatilsDataSorted.get(prevHighlighed).size()-1));
                                CurrentRowsAdded--;
                            }
                        }
        		    }
        		  }
        		});



    }
    public void UpdateItemTotal(){
        List<ArrayList<String>> rightlist=new ArrayList<ArrayList<String>>();
        for(ArrayList<String> row1 : DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow)) {
            ArrayList<String>  rowCopied=new ArrayList(row1);
            String leftcomp=rowCopied.get(0);
            String rightcomp=(String) invoice_table.getValueAt(highlightedrow, DataManipulator.HeaderEnum.No.ordinal());
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
    public void cancel(){

        while(CurrentRowsAdded>0 ){
            if(highlightedrow!= -1){
                DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).remove((DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).size()-1));
                CurrentRowsAdded--;
            }

            else{
                break;
            }
        }
        CustomerName.setText((String)invoice_table.getValueAt(highlightedrow, DataManipulator.HeaderEnum.CusomterName.ordinal()));
        invoiceDate.setText((String)invoice_table.getValueAt(highlightedrow, DataManipulator.HeaderEnum.Date.ordinal()));

        update_InvoiceLine_model();
    }
    public void cancel2(){

        LoadLines(InvoicePath);
        LoadHeaders(HeaderPath);
        if(highlightedrow == -1)
        {
            DetailsTableModel=new tablemodel(cols2,new ArrayList<ArrayList<String>>());
        }
        else{
            UpdateItemTotal();
            update_InvoiceLine_model();
        }

    }
    public void CreateNewInvoice(){
//            SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
//            String today= formatter2.format(Instant.now());
//            today=today.replaceAll("T.*","");
            int rows=invoice_table.getRowCount();
            String [] line={String.valueOf(rows+1),""," "," "};

        DataManipulator.HeaderData.add(new ArrayList<String> (Arrays.asList(line)));
        tablemodel newmodel= new tablemodel(colNames, DataManipulator.HeaderData);
        invoice_table.setModel(newmodel);
        invoice_table.getModel().addTableModelListener(new TableModelListener(){

            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getColumn()== DataManipulator.HeaderEnum.Date.ordinal()){
                    SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date today= formatter2.parse((String)invoice_table.getValueAt(e.getFirstRow(),e.getColumn()));
                        System.out.println("DATE IS "+today);
                    }
                    catch (ParseException parseException) {
                        JOptionPane.showMessageDialog(invoice_table.getParent(),
                                "Invalid Date Format");
                        //parseException.printStackTrace();
                    }
                }
            }
        });
        DetailsTableModel=new tablemodel(cols2,new ArrayList<ArrayList<String>>());
        invoiceItemsTable.setModel(DetailsTableModel);




    }
    public void DeleteInvoice(){
        DataManipulator.HeaderData.remove(highlightedrow);
        int index=1;
        for(ArrayList<String> Row : DataManipulator.HeaderData){
            Row.set(DataManipulator.HeaderEnum.No.ordinal(), String.valueOf(index));
            index++;
        }
        DataManipulator.ItemsDeatilsDataSorted.remove(highlightedrow);
        index=1;
        for(ArrayList<ArrayList<String>>batch : DataManipulator.ItemsDeatilsDataSorted){
                for(ArrayList<String> Row : batch){
                    Row.set(DataManipulator.HeaderEnum.No.ordinal(), String.valueOf(index));
                }
                index++;
        }
        tablemodel newmodel= new tablemodel(colNames, DataManipulator.HeaderData);
        invoice_table.setModel(newmodel);
        invoice_table.getModel().addTableModelListener(new TableModelListener(){

            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getColumn()== DataManipulator.HeaderEnum.Date.ordinal()){
                    SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
                    try {
                        Date today= formatter2.parse("x2-11-2022");
                        System.out.println("DATE IS "+today);
                    }
                    catch (ParseException parseException) {
                        JOptionPane.showMessageDialog((JTable)e.getSource(),
                                "PRASE ERROR");
                        parseException.printStackTrace();
                    }
                }
            }
        });
        DetailsTableModel=new tablemodel(cols2,new ArrayList<ArrayList<String>>());
        invoiceItemsTable.setModel(DetailsTableModel);

    }
    public void save(){
        DataWriteIO write= new DataWriteIO(HeaderPath,InvoicePath,DataManipulator.HeaderData,DataManipulator.ItemsDeatilsDataSorted);
    }
    public void LoadHeaders(String path){
        //path =openfile();
        DataStoreIO streamer;

        DataManipulator.ItemsDeatilsDataSorted= new ArrayList<ArrayList<ArrayList<String>>>();
        DataManipulator.HeaderData = new ArrayList<ArrayList<String>>();


        //Boolean isHeader;
        //isHeader=path.contains("Header");
        try {
            DataParser.parseHeader(path);
                tablemodel newmodel= new tablemodel(colNames, DataManipulator.HeaderData);
                invoice_table.setModel(newmodel);
            invoice_table.getModel().addTableModelListener(new TableModelListener(){

                @Override
                public void tableChanged(TableModelEvent e) {
                    if(e.getColumn()== DataManipulator.HeaderEnum.Date.ordinal()){
                        SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date today= formatter2.parse((String) invoice_table.getValueAt(e.getFirstRow(),e.getColumn()));
                            System.out.println("DATE IS "+today);
                        }
                        catch (ParseException parseException) {
                            JOptionPane.showMessageDialog((JTable)invoice_table,
                                    "PRASE ERROR");
                            //parseException.printStackTrace();
                        }
                    }
                }
            });




            // invoice_table=new JTable(listoflists,cols);
        }
        catch (IOException ioException)
        {ioException.printStackTrace();}


    }
    public void LoadLines(String path){

        DataManipulator.ItemsDeatilsData = new ArrayList<ArrayList<String>>();
        //DataManipulator.ItemsDeatilsDataSorted=
        //Boolean isHeader;
        //isHeader=path.contains("Header");
        //loadlines


        // invoice_table=new JTable(listoflists,cols);


    }
    public void actionPerformed(ActionEvent e){

        List<String> items;
        //listoflists= new ArrayList<ArrayList<String>>();
        DataStoreIO streamer;
        String path="";
        if(e.getActionCommand()=="DeleteInvoice")
        {
            DeleteInvoice();
        }
        if(e.getActionCommand()=="LocalSave"){
            CurrentRowsAdded=0;
            invoice_table.setValueAt(CustomerName.getText(),highlightedrow, DataManipulator.HeaderEnum.CusomterName.ordinal());
            HeaderTableModel=new tablemodel(colNames,DataManipulator.HeaderData);
            invoice_table.setModel(HeaderTableModel);

            invoice_table.setValueAt(invoiceDate.getText(),highlightedrow, DataManipulator.HeaderEnum.Date.ordinal());
            HeaderTableModel=new tablemodel(colNames,DataManipulator.HeaderData);
            invoice_table.setModel(HeaderTableModel);


        }
        if(e.getActionCommand()=="Save"){
            save();


        }
        if(e.getActionCommand()=="Cancel"){
            cancel();


        }
        if(e.getActionCommand()=="AddItem") {

            CurrentRowsAdded++;
		     String [] arrIn= {String.valueOf(highlightedrow+1),"","","","0"};
		     //rightlist.add(new ArrayList<String> (Arrays.asList(arrIn)));
		     try{
                 DataManipulator.ItemsDeatilsDataSorted.get(highlightedrow).add(new ArrayList<String> (Arrays.asList(arrIn)));
             }
		     catch (Exception eee3){
		         ArrayList<ArrayList<String>> localList=new ArrayList<ArrayList<String >>();
		         localList.add(new ArrayList<String> (Arrays.asList(arrIn)));
		         DataManipulator.ItemsDeatilsDataSorted.add(localList);

             }
            update_InvoiceLine_model();

        	
        }
        if(e.getActionCommand()=="Create"){
            CreateNewInvoice();
        }
        if(e.getActionCommand()=="l") {
            //clearData(); TODO clear data
            JOptionPane.showMessageDialog(this,
                    "Please Choose the invoice Line");
            path = openfile();
            while(!path.endsWith(".csv")){
                JOptionPane.showMessageDialog(this,
                        "Invalid Fiel Format Please Re-chose the csv file for invoice line");
                path=openfile();
            }
            // isHeader;
            InvoicePath=path;
            LoadLines(path);

            JOptionPane.showMessageDialog(this,
                    "Please Choose the Header Line");
            path = openfile();
            while(!path.endsWith(".csv")){
                JOptionPane.showMessageDialog(this,
                        "Invalid Field Format Please Re-chose the csv file for Header line");
                path=openfile();
            }
            HeaderPath=path;
            LoadHeaders(path);
        }

    }


    public String openfile(){
        String path;
        JFileChooser fc=new JFileChooser();
        if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
            path=fc.getSelectedFile().getPath();
            return path;
	    }
	        else { return "";}
    }
//    public void CreateInvoice(){
//
//        GUI.GUI.tablemodel newmodel= new GUI.GUI.tablemodel(colNames,listoflists);
//        invoice_table.setModel(newmodel);
//    }

}
