import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Product {
    JFrame frame;
    JTextField nameField,detailField,barcodeField,costField, retailField, quantityField, orderLField ;
    Connection con;
    //String record[][] = {};
    JTable table;
    DefaultTableModel dtm;
    String tableHeader[] = {"Product ID", "Product Name", "Details","Barcode", 
                            "Product Cost", "Retail Price", "Quantity", "Re-Order Level"};
    TableModel tableModel = new DefaultTableModel(tableHeader, 0);

        JButton add, edit,delete;
   
   public Product(){
       //vendor
       frame = new JFrame();
       frame.setSize(new Dimension(1200,600));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(100,50));
       frame.setTitle("Product");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new BorderLayout());

       JLabel vHeader = new JLabel("Product");
       vHeader.setFont(new Font("Algerian",  Font.BOLD, 30));
       JPanel headerPanel = new JPanel(new FlowLayout());
       headerPanel.add(vHeader);
       
       JPanel midPanel = new JPanel(new BorderLayout());
           midPanel.setPreferredSize(new Dimension(1000, 300));
       
       JPanel leftPanel = new JPanel(new GridLayout(4,1));
           leftPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           leftPanel.setPreferredSize(new Dimension(500,200));

       JPanel namePanel = new JPanel(new FlowLayout());
           JLabel nameLabel = new JLabel("Product Name");
           namePanel.add(nameLabel);
           nameField = new JTextField(20);
           namePanel.add(nameField);
       leftPanel.add(namePanel);

       JPanel detailPanel = new JPanel(new FlowLayout());
           JLabel detailLabel = new JLabel("Product Details");
           detailPanel.add(detailLabel);
           detailField = new JTextField(20);
           detailPanel.add(detailField);
       leftPanel.add(detailPanel);

       JPanel barcodePanel = new JPanel(new FlowLayout());
           JLabel barcodeLabel = new JLabel("Product barcode");
           barcodePanel.add(barcodeLabel);
           barcodeField = new JTextField(20);
           barcodePanel.add(barcodeField);
       leftPanel.add(barcodePanel);

       JPanel costPanel = new JPanel(new FlowLayout());
           JLabel costLabel = new JLabel("Product Cost");
           costPanel.add(costLabel);
           costField = new JTextField(20);
           costPanel.add(costField);
       leftPanel.add(costPanel);

            JPanel retailPanel = new JPanel(new FlowLayout());
            JLabel retailLabel = new JLabel("Product Retail Price");
            retailPanel.add(retailLabel);
            retailField = new JTextField(20);
            retailPanel.add(retailField);
        leftPanel.add(retailPanel);

        JPanel quantityPanel = new JPanel(new FlowLayout());
            JLabel quantityLabel = new JLabel("Product Quantity");
            quantityPanel.add(quantityLabel);
            quantityField = new JTextField(20);
            quantityPanel.add(quantityField);
        leftPanel.add(quantityPanel);

        JPanel orderLPanel = new JPanel(new FlowLayout());
            JLabel orderLLabel = new JLabel("Product Re-Order Level");
            orderLPanel.add(orderLLabel);
            orderLField = new JTextField(20);
            orderLPanel.add(orderLField);
        leftPanel.add(orderLPanel);

        JPanel rightPanel = new JPanel();
           rightPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           rightPanel.setPreferredSize(new Dimension(700,500));        
       table = new JTable(tableModel); 
       table.addMouseListener(new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent evt){
            dtm = (DefaultTableModel)table.getModel();
            int rowSelected =table.getSelectedRow();
        
            int id = Integer.parseInt(dtm.getValueAt(rowSelected, 0).toString());
            nameField.setText(dtm.getValueAt(rowSelected, 1).toString()); 
            detailField.setText(dtm.getValueAt(rowSelected, 2).toString());
            barcodeField.setText(dtm.getValueAt(rowSelected, 3).toString());
            costField.setText(dtm.getValueAt(rowSelected, 4).toString());
            retailField.setText(dtm.getValueAt(rowSelected, 5).toString());
            quantityField.setText(dtm.getValueAt(rowSelected, 6).toString());
            orderLField.setText(dtm.getValueAt(rowSelected, 7).toString());
            
            add.setEnabled(false);
            edit.setEnabled(true);
            delete.setEnabled(true);
           }
       });
       JScrollPane tableScroll = new JScrollPane(table);
       tableScroll.setPreferredSize(new Dimension(650, 350));
       rightPanel.add(tableScroll);
       

       midPanel.add(leftPanel, BorderLayout.WEST);
       midPanel.add(rightPanel, BorderLayout.EAST);

       JPanel lowerPanel = new JPanel(new FlowLayout());
           lowerPanel.setPreferredSize(new Dimension(600,100));
       add = new JButton("Add");
           add.setPreferredSize(new Dimension(80,40));
           add.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent ae){
                   add();
               }
           });
           lowerPanel.add(add);
        edit = new JButton("Edit");
            edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt){
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this product?", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                    //0 is yes, 1 is no 
                    if (option == 0) {
                        edit(); 
                     }else if (option == 1) {
                        
                     }else{
    
                     } 
                    

                }
            });
            edit.setEnabled(false);
            edit.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(edit);
        delete = new JButton("Delete");
       delete.addActionListener(new ActionListener(){
        @Override
            public void actionPerformed(ActionEvent evt){
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                //0 is yes, 1 is no 
                if (option == 0) {
                    delete(); 
                 }else if (option == 1) {
                    
                 }else{

                 }
           
             }
         });
           delete.setEnabled(false);
           delete.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(delete);
       JButton clear = new JButton("Clear");
         clear.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                nameField.setText("");
                detailField.setText("");
                barcodeField.setText("");
                costField.setText("");
                retailField.setText("");
                quantityField.setText("");
                 orderLField.setText("");

                nameField.requestFocus();
                add.setEnabled(true);
                edit.setEnabled(false);
                delete.setEnabled(false);


                }
            });
           clear.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(clear);

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
       fetchProduct();
      
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
    int c;
    try {
            Statement st = con.createStatement();

            //selecting list of available products in the database
            PreparedStatement prest = con.prepareStatement("select * from product");
            ResultSet rs = prest.executeQuery();
            ResultSetMetaData rmd = rs.getMetaData();
            c = rmd.getColumnCount();
            dtm = (DefaultTableModel)table.getModel();
            dtm.setRowCount(0);
            while(rs.next()){
            Vector v = new Vector<>();
            for (int i = 1; i <= c ; i++) {
                v.add(rs.getString("id"));
                v.add(rs.getString("product_name"));
                v.add(rs.getString("product_details"));
                v.add(rs.getString("product_barcode"));
                v.add(rs.getString("product_cost"));
                v.add(rs.getString("product_retail_price"));
                v.add(rs.getString("product_quantity"));
                v.add(rs.getString("product_order_level"));
            }

       dtm.addRow(v);
     }
   

 //  con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void add(){
     try {
         String name = nameField.getText();
         String detail = detailField.getText();
         String barcode = barcodeField.getText();
         String cost = costField.getText();
         String retail = retailField.getText();
         String quantity = quantityField.getText();
         String orderL = orderLField.getText();
 
         if (name.isEmpty() || detail.isEmpty() || barcode.isEmpty() || cost.isEmpty() || quantity.isEmpty() || retail.isEmpty() || orderL.isEmpty()){
             JOptionPane.showMessageDialog(null, "Please fill all required field");    
         }else {
         
         //adding a product into database 
         String query = "insert into product(product_name, product_details, product_barcode, product_cost, product_retail_price,product_quantity, product_order_level)values(?,?,?,?,?,?,?)";
         //then create the mysql insert preparedstatement
         PreparedStatement prest = con.prepareStatement(query);
         prest.setString(1, name);
         prest.setString(2, detail);
         prest.setString(3, barcode);
         prest.setString(4, cost);
         prest.setString(5, retail);
         prest.setString(6, quantity);
         prest.setString(7, orderL);
         //then execute 
         prest.execute();
         JOptionPane.showMessageDialog(null, "Product Added Succesfully");
 
             nameField.setText("");
             detailField.setText("");
             barcodeField.setText("");
             costField.setText("");
             retailField.setText("");
             quantityField.setText("");
             orderLField.setText("");

             nameField.requestFocus();

     
         fetchProduct();
         }

        
     } catch (Exception e) {
         //TODO: handle exception
         e.printStackTrace();
         }

     
        //con.close();
 } 

 public void edit(){
   

    try {
        dtm = (DefaultTableModel)table.getModel();
        int rowSelected =table.getSelectedRow();
        int id = Integer.parseInt(dtm.getValueAt(rowSelected, 0).toString());   
            String name = nameField.getText();
            String detail = detailField.getText();
            String barcode = barcodeField.getText();
            String cost = costField.getText();
            String retail = retailField.getText();
            String quantity = quantityField.getText();
            String orderL = orderLField.getText();

            if (name.isEmpty() || detail.isEmpty() || barcode.isEmpty() || cost.isEmpty() || quantity.isEmpty() || retail.isEmpty() || orderL.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all required field");    
            }else {
            //Editing a product in database 
            String query = "update product set product_name=?, product_details=?, product_barcode=?, product_cost=?, product_retail_price=?,product_quantity=?, product_order_level=? where id =?";
            //then create the mysql insert preparedstatement
            PreparedStatement prest = con.prepareStatement(query);
            prest.setString(1, name);
            prest.setString(2, detail);
            prest.setString(3, barcode);
            prest.setString(4, cost);
            prest.setString(5, retail);
            prest.setString(6, quantity);
            prest.setString(7, orderL);
            prest.setInt(8, id);
            //then execute 
            prest.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product Edited Succesfully");

                nameField.setText("");
                detailField.setText("");
                barcodeField.setText("");
                costField.setText("");
                retailField.setText("");
                quantityField.setText("");
                orderLField.setText("");

                nameField.requestFocus();
                add.setEnabled(true);
                edit.setEnabled(false);
                delete.setEnabled(false);


            fetchProduct();
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
 }

 public void delete(){        
        dtm = (DefaultTableModel)table.getModel();
        int rowSelected =table.getSelectedRow();
        int id = Integer.parseInt(dtm.getValueAt(rowSelected, 0).toString());
        try {
        //deleting a product in database
        String query = "delete from product where id = ?";
        PreparedStatement prest = con.prepareStatement(query);
        prest.setInt(1, id);
        prest.executeUpdate();
        JOptionPane.showMessageDialog(null, "Product Deleted Succesfully");
                nameField.setText("");
                detailField.setText("");
                barcodeField.setText("");
                costField.setText("");
                retailField.setText("");
                quantityField.setText("");
                orderLField.setText("");

                nameField.requestFocus();
                add.setEnabled(true);
                edit.setEnabled(false);
                delete.setEnabled(false);
        fetchProduct(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
 public void actionPerformed(ActionEvent ae){  
 
 }
 public void mouseClicked(MouseEvent evt){

 }



    

public static void main(String[] args) {
    Product pr = new Product();
}
}

//run using: java -cp .;mysql-connector.jar projectName
