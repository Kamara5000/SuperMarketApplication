
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.*;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.net.*;
import java.text.SimpleDateFormat;


public class SuperMarketApp implements ActionListener {
    JFrame frame;
    
    public  SuperMarketApp(String admin){
        frame = new JFrame();
        frame.setSize(new Dimension(1200,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(new Point(100,50));
        frame.setTitle("Kamara Super Market");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
        frame.setLayout(new BorderLayout());

        JPanel sideNav = new JPanel(new BorderLayout());
        sideNav.setPreferredSize(new Dimension(300,600));
        

        JPanel header = new JPanel(new FlowLayout());
        header.setPreferredSize(new Dimension(100,100));
        header.setBackground(Color.CYAN);
        JLabel hLabel = new JLabel("Shop Manager");
        hLabel.setFont(new Font("Algerian",  Font.BOLD, 30));
        header.add(hLabel);
        
        
        JPanel content = new JPanel(new GridLayout(5,1));
       
        //content.setBackground(Color.CYAN);
        
        JButton vendor = new JButton("Vendor");
            vendor.setPreferredSize(new Dimension(150,40));
           vendor.addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent ae){
                  Vendor vN = new Vendor();
               }
           });
        JButton product = new JButton("Product");
            product.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    Product pro = new Product();
                }
            });
        product.setPreferredSize(new Dimension(150,40));
        JButton purchase = new JButton("Purchase");
            purchase.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    Purchase pS = new Purchase();
                }
            });
        purchase.setPreferredSize(new Dimension(150,40));
        JButton sales = new JButton("Sales");
        sales.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
               Sales sL = new Sales(admin);
            }
        });
        sales.setPreferredSize(new Dimension(150,40));
        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                LandingPage lP= new LandingPage();
               frame.setVisible(false);
            }
        });
        logout.setPreferredSize(new Dimension(150,40));

        JPanel vn = new JPanel(new FlowLayout());
        vn.setBackground(Color.CYAN);
        JPanel pro = new JPanel(new FlowLayout());
        pro.setBackground(Color.CYAN);
        JPanel pur = new JPanel(new FlowLayout());
        pur.setBackground(Color.CYAN);
        JPanel sl= new JPanel(new FlowLayout());
        sl.setBackground(Color.CYAN);
        JPanel lg = new JPanel(new FlowLayout());
        lg.setBackground(Color.CYAN);
        
        vn.add(vendor);
        pro.add(product);
        pur.add(purchase);
        sl.add(sales);
        lg.add(logout);
        
        content.add(vn);
        content.add(pro);
        content.add(pur);
        content.add(sl);
        content.add(lg);

        sideNav.add(header, BorderLayout.NORTH);
        sideNav.add(content, BorderLayout.CENTER);

        frame.add(sideNav, BorderLayout.WEST);
        //frame.pack();
        frame.setVisible(true);
 
    }

   
        public void actionPerformed(ActionEvent ae){
        
        }


    public static void main(String[] args) {
        SuperMarketApp sA = new SuperMarketApp(null);
    }
}
