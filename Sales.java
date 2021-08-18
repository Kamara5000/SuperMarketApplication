import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Sales {
    JFrame frame;
    JTextField nameField,barcodeField,priceField, totalField, quantityField,  totalCostField, paymentField, balanceField;
    Connection con;
    //String record[][] = {};
    JTable table;
    DefaultTableModel dtm;
    String tableHeader[] = {"Product Barcode", "Product Name", "Price","Quantity", 
                            "Total"};
    TableModel tableModel = new DefaultTableModel(tableHeader, 0);

        JButton add, edit,delete, finalAdd;

   public Sales(){
       frame = new JFrame();
       frame.setSize(new Dimension(1200,600));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(100,50));
       frame.setTitle("Sales");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new BorderLayout());


       JLabel vHeader = new JLabel("Sales");
       vHeader.setFont(new Font("Algerian",  Font.BOLD, 30));
       JPanel headerPanel = new JPanel(new BorderLayout());
       headerPanel.add(vHeader,BorderLayout.WEST);
       JPanel vPanel = new JPanel();
    //    JLabel vLabel = new JLabel("Vendor");
    //    vField = new JComboBox<>();
     
    //     vPanel.add(vLabel);
    //     vPanel.add(vField);
    //    headerPanel.add(vPanel, BorderLayout.EAST);
       
       JPanel midPanel = new JPanel(new BorderLayout());
           midPanel.setPreferredSize(new Dimension(1000, 300));
           midPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
       JPanel leftPanel = new JPanel(new GridLayout(5,1));
           leftPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           leftPanel.setPreferredSize(new Dimension(500,200));

        JPanel barcodePanel = new JPanel(new FlowLayout());
            JLabel barcodeLabel = new JLabel("Product Barcode");
            barcodePanel.add(barcodeLabel);
            barcodeField = new JTextField(20);
            barcodeField.setToolTipText("Enter product Barcode then press enter");
            barcodeField.addKeyListener(new java.awt.event.KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent ke){
                    if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    fetchProduct();
                    }
                }
            });      
            barcodePanel.add(barcodeField);
        leftPanel.add(barcodePanel);


       JPanel namePanel = new JPanel(new FlowLayout());
           JLabel nameLabel = new JLabel("Product Name");
           namePanel.add(nameLabel);
           nameField = new JTextField(20);
           namePanel.add(nameField);
       leftPanel.add(namePanel);

       JPanel pricePanel = new JPanel(new FlowLayout());
           JLabel priceLabel = new JLabel("Product Price");
           pricePanel.add(priceLabel);
           priceField = new JTextField(20);
           pricePanel.add(priceField);
       leftPanel.add(pricePanel);

        JPanel quantityPanel = new JPanel(new FlowLayout());
            JLabel quantityLabel = new JLabel("Product Quantity");
            quantityPanel.add(quantityLabel);
            quantityField = new JTextField(20);
            quantityField.setText("1");
            quantityPanel.add(quantityField);
        leftPanel.add(quantityPanel);
       
        JPanel addPanel = new JPanel(new FlowLayout());
            add = new JButton("Add");
            add.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                   sales();
                 }
            });
            add.setPreferredSize(new Dimension(60,40));
            add.setEnabled(false);
            addPanel.add(add);
        leftPanel.add(addPanel);
        
        JPanel seperatorPanel = new JPanel(new FlowLayout()); 
        seperatorPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        leftPanel.add(seperatorPanel);
        
        
        JPanel totalCostPanel = new JPanel(new FlowLayout());
        JLabel totalCostLabel = new JLabel("Total Cost");
            totalCostPanel.add(totalCostLabel);
            totalCostField = new JTextField(20);
            totalCostField.setEditable(false);
            totalCostPanel.add(totalCostField);
        leftPanel.add(totalCostPanel);

        JPanel paymentPanel = new JPanel(new FlowLayout());
        JLabel paymentLabel = new JLabel("Payment");
            paymentPanel.add(paymentLabel);
            paymentField = new JTextField(20);
            paymentField.setText("0");
            paymentField.setToolTipText("Enter payment made then press entrer");
            paymentPanel.add(paymentField);
            paymentField.addKeyListener(new java.awt.event.KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent ke){
                    String cost = totalCostField.getText();
                    String payment = paymentField.getText();
                    
                        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                            if (cost.isEmpty() || payment.isEmpty()) {
                                JOptionPane.showMessageDialog(null,"The cost field and payment field must not be empty");
                            }else{
                            int pay = Integer.parseInt(payment);
                            int subTotal = Integer.parseInt(cost);
                            int balance = pay-subTotal;
                             balanceField.setText(balance + "");
                             finalAdd.setEnabled(true);
                        
                        }
                    }
                }
            });
        leftPanel.add(paymentPanel);
        
        JPanel balancePanel = new JPanel(new FlowLayout());
        JLabel balanceLabel = new JLabel("Balance");
            balancePanel.add( balanceLabel);
            balanceField = new JTextField(20);
            balanceField.setEditable(false);
            balancePanel.add( balanceField);
        leftPanel.add( balancePanel);

        JPanel rightPanel = new JPanel();
           rightPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           rightPanel.setPreferredSize(new Dimension(700,500));        
       table = new JTable(tableModel); 
       table.addMouseListener(new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent evt){
            
           }
       });
       JScrollPane tableScroll = new JScrollPane(table);
       tableScroll.setPreferredSize(new Dimension(650, 350));
       rightPanel.add(tableScroll);
       

       midPanel.add(leftPanel, BorderLayout.WEST);
       midPanel.add(rightPanel, BorderLayout.EAST);

       JPanel lowerPanel = new JPanel(new FlowLayout());
           lowerPanel.setPreferredSize(new Dimension(600,100));
       finalAdd = new JButton("Add");
           finalAdd.setPreferredSize(new Dimension(80,40));
           finalAdd.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent ae){
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want make this sales", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                    //0 is yes, 1 is no 
                    if (option == 0) {
                        finalAdd();
                        ((DefaultTableModel) tableModel).setRowCount(0);
                        totalCostField.setText("");
                        balanceField.setText("");
                        paymentField.setText("");
                        barcodeField.setText("");
                        finalAdd.setEnabled(false);
                     }else if (option == 1) {
                        
                     }else{
    
                     }    
                
               }
           });
           finalAdd.setEnabled(false);
           lowerPanel.add(finalAdd);
        
        

           JButton exit = new JButton("Exit");
           exit.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent evt){
                 frame.setVisible(false);
             }
              });
             exit.setPreferredSize(new Dimension(80,40));
             lowerPanel.add(exit);
             
             
        JPanel lp = new JPanel(new BorderLayout());
        lp.add(lowerPanel, BorderLayout.WEST);

 
       frame.add(headerPanel, BorderLayout.NORTH);
       frame.add(midPanel, BorderLayout.CENTER);
       frame.add(lp, BorderLayout.SOUTH);
       frame.setVisible(true);

       connect();
       
   }

   public void connect(){
    try {
        Class.forName("com.mysql.jdbc.Driver");

        //establish connection to the database   
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket_database","root", "");

    } catch (Exception e) {
        e.printStackTrace();
    }

}


public void fetchProduct(){
    try {
        String code = barcodeField.getText();

        PreparedStatement prest = con.prepareStatement("select * from product where product_barcode = ?");
        prest.setString(1, code);
        ResultSet rs = prest.executeQuery();
        if (rs.next()) {
            String name = rs.getString("product_name");
            String price = rs.getString("product_retail_price");

            nameField.setText(name);
            priceField.setText(price);

            add.setEnabled(true);
            
        } else {
            JOptionPane.showMessageDialog(null, "Barcode not found"); 
            barcodeField.setText("");  
            quantityField.requestFocus(); 
        }
        
    } catch (Exception e) {
        //TODO: handle exception
    }
}

public void sales(){
    try {

        String barcode = barcodeField.getText();
        PreparedStatement prest = con.prepareStatement("select * from product where product_barcode = ?");
        prest.setString(1, barcode);
        ResultSet rs = prest.executeQuery();

            while(rs.next()){

                //checking if available number of item is enough for sale
                int availableQuantity;
                availableQuantity = rs.getInt("product_quantity");

                int price = Integer.parseInt(priceField.getText());
                int qty = Integer.parseInt(quantityField.getText());
        
                int total = price*qty;

                if (qty >= availableQuantity){
                    JOptionPane.showMessageDialog(null, "Available Quantity is " + availableQuantity);
                    quantityField.requestFocus();
                    add.setEnabled(true);
                } else {
                  //adding each item to row in the sales table
                    dtm = (DefaultTableModel)table.getModel();
                    dtm.addRow(new Object[]{
                    barcodeField.getText(),
                    nameField.getText(),
                    priceField.getText(),
                    quantityField.getText(),
                    total
                    });  

                    
                    //getting value from total column to add up  to obtain the total cost
                    int c = dtm.getRowCount();
                    int addtotal = 0; 
                    for (int i = 0; i < c; i++) {
                        addtotal= addtotal+Integer.parseInt(table.getValueAt(i, 4).toString());
                    }
                    totalCostField.setText(addtotal+"");

                    barcodeField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("1");
                    barcodeField.requestFocus();

                    add.setEnabled(false);
                }
            }

       
        
        


    } catch (Exception e) {
        //TODO: handle exception
    }


}

public void finalAdd(){
    
       try {
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
         String date = dt.format(now);
         
         String totalCost = totalCostField.getText();
         String payment = paymentField.getText();
         String balance = balanceField.getText();
         
        
         //inserting the sales into the purchases table in the  database
         int lastid = 0;
         String query1="insert into sales(sales_date, total, payment, balance) values(?,?,?,?)"; 
         PreparedStatement prest1 = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
         prest1.setString(1, date);
         prest1.setString(2, totalCost);
         prest1.setString(3, payment);
         prest1.setString(4, balance);
         prest1.executeUpdate();
         ResultSet rs = prest1.getGeneratedKeys();
        
         if (rs.next()) {

            //this will generate an id for the sales to be use as a foreign key for all item sold  together
            //it will be save as sales_id in the sales_items table
            lastid = rs.getInt(1);
         }

         //inserting all sold items into the sales_items table in the database
         String query2="insert into sales_items(sales_id, product_id, price, quantity, total) values(?,?,?,?,?)"; 
         PreparedStatement prest2 = con.prepareStatement(query2);

         String productId;
         String rPrice;
         String qty;
         int totalP = 0;

         for (int i = 0; i < table.getRowCount(); i++) {
             productId = (String)table.getValueAt(i, 0);
             rPrice = (String)table.getValueAt(i, 2);
             qty =  (String)table.getValueAt(i, 3);
             totalP = (int)table.getValueAt(i, 4);

             prest2.setInt(1, lastid);
             prest2.setString(2, productId);
             prest2.setString(3,rPrice);
             prest2.setString(4,qty);
             prest2.setInt(5,totalP);
             prest2.executeUpdate();
             
         }


         // the total quantity of each avaibale product in the product table need to decrement for each sales of the product 
         String query3="update product set product_quantity = product_quantity - ? where product_barcode = ?"; 
         PreparedStatement prest3 = con.prepareStatement(query3);

        String productBcode;
         String quant;
         for (int i = 0; i < table.getRowCount(); i++) {
           productBcode = (String)table.getValueAt(i, 0);
            quant =  (String)table.getValueAt(i, 3);
         
            prest3.setString(1, quant);
            prest3.setString(2, productBcode);
            prest3.executeUpdate();
        }

        JOptionPane.showMessageDialog(null, "New sales made succesfully");




       } catch (Exception e) {
           
       }

}
 


 public void actionPerformed(ActionEvent ae){  
 
 }
 public void mouseClicked(MouseEvent evt){

 }

 public void keyPressed(KeyEvent ke){

}



public static void main(String[] args) {
    Sales sL = new Sales();
}
}
//after javac projectname.java
//run using: java -cp .;mysql-connector.jar projectName
