import java.awt.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Key;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
  
public class Register {

    Connection con;
    JTextField  nameField,  userNameField,  phoneField,  adressField;
    JPasswordField passwordField;
    JFrame frame;
    
    

    public Register(){
        
       
       frame = new JFrame();
       frame.setSize(new Dimension(400,300));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(400,200));
       frame.setTitle("Register");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new BorderLayout());

       JPanel wPanel = new JPanel(new FlowLayout());
       JLabel wLabel = new JLabel("REGISTER");
       wLabel.setFont(new Font("Casteller",  Font.BOLD, 20));
       wPanel.add(wLabel);

       JPanel registerPanel = new JPanel(new GridLayout(6,1));
            JPanel namePanel = new JPanel(new FlowLayout());
            JLabel nameLabel = new JLabel("FullName           ");
            nameField = new JTextField(20);
            namePanel.add(nameLabel);
            namePanel.add(nameField);

            JPanel userNamePanel = new JPanel(new FlowLayout());
            JLabel userNameLabel = new JLabel("Username        ");
            userNameField = new JTextField(20);
            userNamePanel.add(userNameLabel);
            userNamePanel.add(userNameField);
            

            JPanel phonePanel = new JPanel(new FlowLayout());
            JLabel phoneLabel = new JLabel("Phone Number");
            phoneField = new JTextField(20);
            phonePanel.add(phoneLabel);
            phonePanel.add(phoneField);

            JPanel adressPanel = new JPanel(new FlowLayout());
            JLabel adressLabel = new JLabel("Address           ");
            adressField = new JTextField(20);
            adressPanel.add(adressLabel);
            adressPanel.add(adressField);

            
            JPanel passwordPanel = new JPanel(new FlowLayout());
            JLabel passwordLabel = new JLabel("Password         ");
            passwordField = new JPasswordField(20);
            passwordPanel.add(passwordLabel);
            passwordPanel.add(passwordField);

            JPanel regButtonPanel = new JPanel(new FlowLayout());
            JButton reg = new JButton("Register");
            reg.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    register();
                }
            });
            regButtonPanel.add(reg);
     
       registerPanel.add(namePanel);
       registerPanel.add(userNamePanel);
       registerPanel.add(phonePanel);
       registerPanel.add(adressPanel);
       registerPanel.add(passwordPanel);
       registerPanel.add(regButtonPanel);

        



       frame.add(wPanel, BorderLayout.NORTH);
       frame.add(registerPanel, BorderLayout.CENTER);
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

  

    public void register(){
        try {
    
            String uname = userNameField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String adress = adressField.getText();
            String pass = passwordField.getText();
            

                    //to encrypt the password before saving to databse
                    EncryptDecrypt en = new EncryptDecrypt();
                    String p = en.encrypt(pass);
                
            PreparedStatement prest = con.prepareStatement("select * from admin where user_name = ?");
            prest.setString(1, uname);
            ResultSet rs = prest.executeQuery();

            //to check if any of the  field if empty
            if (uname.isEmpty() || name.isEmpty() || phone.isEmpty() || adress.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "fill all required field before proceeding to register");
            } else {
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Admin with this user name already exist please choose another username");
                }else{
                    String query="insert into admin (name,  user_name, phone, adress,password) values(?,?,?,?,?)"; 
                    PreparedStatement prest1 = con.prepareStatement(query);
                    prest1.setString(1, name);
                    prest1.setString(2, uname);
                    prest1.setString(3,phone);
                    prest1.setString(4, adress);
                    prest1.setString(5, p);
                    prest1.executeUpdate();

                    JOptionPane.showMessageDialog(null, "New admin added succesfully");

                    int option = JOptionPane.showConfirmDialog(null, "Do you want to log in", "warning", JOptionPane.YES_NO_CANCEL_OPTION);
                    //0 is yes, 1 is no 
                    if (option == 0) {
                        LogIn lg = new LogIn();
                        frame.setVisible(false);
                     }else if (option == 1) {
                        userNameField.setText("");
                        nameField.setText("");
                        phoneField.setText("");
                        adressField.setText("");
                        passwordField.setText("");
                        userNameField.requestFocus();
                     }else{
    
                     }    
                
                }
    
            }
    
                
        } catch (Exception e) {
            //TODO: handle exception
        }
    
    
    }




    public static void main(String[] args) {
        Register rg= new Register();
    }    
}

