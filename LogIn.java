import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class LogIn {
  
    JFrame frame;
    Connection con;
    JTextField  userNameField;
    JPasswordField passwordField;
    private static String algorithm = "DESede";
    private static Key key = null;
    private static Cipher cipher = null;

    public LogIn(){
       frame = new JFrame();
       frame.setSize(new Dimension(400,300));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(400,200));
       frame.setTitle("LogIn");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new BorderLayout());

       JPanel wPanel = new JPanel(new FlowLayout());
       JLabel wLabel = new JLabel("LOG IN");
       wLabel.setFont(new Font("Casteller",  Font.BOLD, 20));
       wPanel.add(wLabel);

       JPanel logInPanel = new JPanel(new GridLayout(3,1));
            JPanel userNamePanel = new JPanel(new FlowLayout());
            JLabel userNameLabel = new JLabel("Username");
            userNameField = new JTextField(20);
            userNamePanel.add(userNameLabel);
            userNamePanel.add(userNameField);
            
            JPanel passwordPanel = new JPanel(new FlowLayout());
            JLabel passwordLabel = new JLabel("Password");
            passwordField = new JPasswordField(20);
            passwordPanel.add(passwordLabel);
            passwordPanel.add(passwordField);

            JPanel logButtonPanel = new JPanel(new FlowLayout());
            JButton log = new JButton("LOG IN");
            log.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    login();
                }
            });
            logButtonPanel.add(log);
     
       
       logInPanel.add(userNamePanel);
       logInPanel.add(passwordPanel);
       logInPanel.add(logButtonPanel);

       frame.add(wPanel, BorderLayout.NORTH);
       frame.add(logInPanel, BorderLayout.CENTER);
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


    
      public void login(){
        try {
    
            String uname = userNameField.getText();
            String passE = passwordField.getText();
            

            if (uname.isEmpty() ||  passE.isEmpty()) {
                JOptionPane.showMessageDialog(null, "username and password cannot be empty");
            } else {

                PreparedStatement prest = con.prepareStatement("select * from admin where user_name = ?");
                prest.setString(1, uname);
                ResultSet rs = prest.executeQuery();

                if(rs.next()){


                    //to get the ecncypted password in the databse and decrypt it
                    String passwrd = rs.getString("password");
                   EncryptDecrypt en = new EncryptDecrypt();
                   String p = en.decrypt(passwrd);
                   
                   //String p = decrypt(passwrd);
                

                    if (p.equals(passE)) {
                       SuperMarketApp ap = new SuperMarketApp();
                       frame.setVisible(false);
                        
                    } else {

                        JOptionPane.showMessageDialog(null, "enter correct password");
                     
                    }

                }else{
                    
                    JOptionPane.showMessageDialog(null, "Enter a valid username ");
                
                }
    
            }
    
                
        } catch (Exception e) {
            //TODO: handle exception

            e.printStackTrace();
        }
    
    
    }


    public static void main(String[] args) {
        LogIn lg= new LogIn();
    }    
}

