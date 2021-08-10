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

public class Vendor implements ActionListener{
    JFrame frame;
    JTextField nameField,phoneField,adressField,mailField ;
    Connection con;
    String record[][] = {};
    JTable table;
    DefaultTableModel dtm;
    String tableHeader[] = {"Vendor ID", "Vendor Name", "Phone", "Email", "Address"};
    TableModel tableModel = new DefaultTableModel(tableHeader, 0);
   
   
   public Vendor(){
       //vendor
       frame = new JFrame();
       frame.setSize(new Dimension(1200,600));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(100,50));
       frame.setTitle("Vendor");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new BorderLayout());

       JLabel vHeader = new JLabel("Vendor");
       vHeader.setFont(new Font("Algerian",  Font.BOLD, 30));
       JPanel headerPanel = new JPanel(new FlowLayout());
       headerPanel.add(vHeader);

       JPanel midPanel = new JPanel(new BorderLayout());
           midPanel.setPreferredSize(new Dimension(1000, 300));
       
       JPanel leftPanel = new JPanel(new GridLayout(4,1));
           leftPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           leftPanel.setPreferredSize(new Dimension(500,200));

       JPanel namePanel = new JPanel(new FlowLayout());
           JLabel nameLabel = new JLabel("Vendor Name");
           namePanel.add(nameLabel);
           nameField = new JTextField(20);
           namePanel.add(nameField);
       leftPanel.add(namePanel);

       JPanel phonePanel = new JPanel(new FlowLayout());
           JLabel phoneLabel = new JLabel("Phone");
           phonePanel.add(phoneLabel);
           phoneField = new JTextField(20);
           phonePanel.add(phoneField);
       leftPanel.add(phonePanel);

       JPanel mailPanel = new JPanel(new FlowLayout());
           JLabel mailLabel = new JLabel("Email");
           mailPanel.add(mailLabel);
           mailField = new JTextField(20);
           mailPanel.add(mailField);
       leftPanel.add(mailPanel);

       JPanel adressPanel = new JPanel(new FlowLayout());
           JLabel adressLabel = new JLabel("Address");
           adressPanel.add(adressLabel);
           adressField = new JTextField(20);
           adressPanel.add(adressField);
       leftPanel.add(adressPanel);


      
       JPanel rightPanel = new JPanel();
           rightPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
           rightPanel.setPreferredSize(new Dimension(650,500));
       //JPanel tablPanel = new JPanel();
       String tableHeader[] = {"Vendor ID", "Vendor Name", "Phone", "Email", "Address"};
       String record[][] = {
          
       };
        
       table = new JTable(tableModel); 
       table.addMouseListener(new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent evt){
            dtm = (DefaultTableModel)table.getModel();
            int rowSelected =table.getSelectedRow();
        
            int id = Integer.parseInt(dtm.getValueAt(rowSelected, 0).toString());
            nameField.setText(dtm.getValueAt(rowSelected, 1).toString()); 
            phoneField.setText(dtm.getValueAt(rowSelected, 2).toString()); 
            mailField.setText(dtm.getValueAt(rowSelected, 3).toString()); 
            adressField.setText(dtm.getValueAt(rowSelected, 4).toString()); 
           }
       });
       JScrollPane tableScroll = new JScrollPane(table);
       tableScroll.setPreferredSize(new Dimension(600, 350));
       rightPanel.add(tableScroll);
       

       midPanel.add(leftPanel, BorderLayout.WEST);
       midPanel.add(rightPanel, BorderLayout.EAST);

       JPanel lowerPanel = new JPanel(new FlowLayout());
           lowerPanel.setPreferredSize(new Dimension(600,100));
       JButton add = new JButton("Add");
           add.setPreferredSize(new Dimension(80,40));
           add.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent ae){
                   addVendor();
               }
           });
           lowerPanel.add(add);
       JButton edit = new JButton("Edit");
            edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent evt){
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to edit this vendor?", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                    //0 is yes, 1 is no 
                    if (option == 0) {
                        edit(); 
                     }else if (option == 1) {
                        
                     }else{
    
                     } 
                    

                }
            });
            edit.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(edit);
       JButton delete = new JButton("Delete");
       delete.addActionListener(new ActionListener(){
        @Override
            public void actionPerformed(ActionEvent evt){
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this vendor?", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                //0 is yes, 1 is no 
                if (option == 0) {
                    delete(); 
                 }else if (option == 1) {
                    
                 }else{

                 }
           
             }
         });
           delete.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(delete);
       JButton cancel = new JButton("Cancel");
         cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                nameField.setText("");
                phoneField.setText("");
                mailField.setText("");
                adressField.setText("");
                nameField.requestFocus();

                }
            });
           cancel.setPreferredSize(new Dimension(80,40));
           lowerPanel.add(cancel);

           JButton close = new JButton("Close");
           close.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent evt){
                 frame.setVisible(false);
                
             }
              });
             close.setPreferredSize(new Dimension(80,40));
             lowerPanel.add(close);
         
       
       JPanel lp = new JPanel(new BorderLayout());
       lp.add(lowerPanel, BorderLayout.WEST);
       
     
       frame.add(headerPanel, BorderLayout.NORTH);
       frame.add(midPanel, BorderLayout.CENTER);
       frame.add(lp, BorderLayout.SOUTH);
       frame.setVisible(true);

       connect();
       fetchVendor();

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

   public void fetchVendor(){
       int c;
       try {
        Statement st = con.createStatement();

        //selecting list of registered vendor 
        PreparedStatement prest = con.prepareStatement("select * from vendor");
        ResultSet rs = prest.executeQuery();
        ResultSetMetaData rmd = rs.getMetaData();
        c = rmd.getColumnCount();
        dtm = (DefaultTableModel)table.getModel();
        dtm.setRowCount(0);



      while(rs.next()){
          Vector v = new Vector<>();
          for (int i = 1; i <= c ; i++) {
              v.add(rs.getString("id"));
              v.add(rs.getString("name"));
              v.add(rs.getString("phone"));
              v.add(rs.getString("email"));
              v.add(rs.getString("address"));
              
          }

          dtm.addRow(v);
        }
      
 
    //  con.close();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public void addVendor(){
        try {
            String vName = nameField.getText();
            String vPhone = phoneField.getText();
            String vMail = mailField.getText();
            String vAdress = adressField.getText();
    
            if (vName.isEmpty() || vPhone.isEmpty() || vMail.isEmpty() || vAdress.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all required field");    
            }else {
            
            //Resgistring vendor into database 
            String query = "insert into vendor(name, phone, email, address)values(?,?,?,?)";
            //then create the mysql insert preparedstatement
            PreparedStatement prest = con.prepareStatement(query);
            prest.setString(1, vName);
            prest.setString(2, vPhone);
            prest.setString(3, vMail);
            prest.setString(4, vAdress);
            //then execute 
            prest.execute();
            JOptionPane.showMessageDialog(null, "Vendor Added Succesfully");
    
                nameField.setText("");
                phoneField.setText("");
                mailField.setText("");
                adressField.setText("");
                nameField.requestFocus();
        
            fetchVendor();
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


        String vName = nameField.getText();
        String vPhone = phoneField.getText();
        String vMail = mailField.getText();
        String vAdress = adressField.getText();

        if (vName.isEmpty() || vPhone.isEmpty() || vMail.isEmpty() || vAdress.isEmpty()){
        JOptionPane.showMessageDialog(null, "Please fill all required field");    
        }else {

        //updatin vendor in database 
        String query = "update vendor set name = ?, phone= ?, email= ?, address= ? where id = ?";
        //then create the mysql insert preparedstatement
        PreparedStatement prest = con.prepareStatement(query);
        prest.setString(1, vName);
        prest.setString(2, vPhone);
        prest.setString(3, vMail);
        prest.setString(4, vAdress);
        prest.setInt(5, id);
        //then execute 
        prest.executeUpdate();
        JOptionPane.showMessageDialog(null, "Vendor Edited Succesfully");

        nameField.setText("");
        phoneField.setText("");
        mailField.setText("");
        adressField.setText("");
        nameField.requestFocus();

        fetchVendor(); 
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
         String query = "delete from vendor where id = ?";
         PreparedStatement prest = con.prepareStatement(query);
         prest.setInt(1, id);
         prest.executeUpdate();
         JOptionPane.showMessageDialog(null, "Vendor Deleted Succesfully");
         nameField.setText("");
         phoneField.setText("");
         mailField.setText("");
         adressField.setText("");
         nameField.requestFocus();
         fetchVendor(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
    public void actionPerformed(ActionEvent ae){  
    
    }
    public void mouseClicked(MouseEvent evt){

    }

public static void main(String[] args) {
    Vendor vn = new Vendor();
}
}

//run using: java -cp .;mysql-connector.jar projectName
