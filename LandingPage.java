import java.awt.*;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
public class LandingPage {

    public LandingPage(){
        Splash sh = new Splash();
       JFrame frame = new JFrame();
       frame.setSize(new Dimension(400,300));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLocation(new Point(400,200));
       frame.setTitle("Welcome");
       frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/superMarketIcon.jpg"));
       frame.setLayout(new GridLayout(2,1));

       JPanel wPanel = new JPanel(new FlowLayout());
       JLabel wLabel = new JLabel("Log In Or Register");
       wLabel.setFont(new Font("Algerian",  Font.BOLD, 30));
       wPanel.add(wLabel);

       JPanel logRegPanel = new JPanel(new BorderLayout());
        JPanel logPanel = new JPanel(new FlowLayout());
        JButton log = new JButton("Log In");
        log.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
                LogIn lg = new LogIn();
            }
        });
        log.setPreferredSize(new Dimension(100,40));
        logPanel.add(log);

        JPanel regPanel = new JPanel(new FlowLayout());
        JButton reg = new JButton("Register");
        reg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
                Register rg = new Register();
            }
        });
        reg.setPreferredSize(new Dimension(100,40));
        regPanel.add(reg);
            
       logRegPanel.add(logPanel, BorderLayout.NORTH);
       logRegPanel.add(regPanel, BorderLayout.CENTER);
       
       frame.add(wPanel);
       frame.add(logRegPanel);
       frame.pack();
       frame.setVisible(true);
    }




    public static void main(String[] args) {
        LandingPage lP= new LandingPage();
    }    
}
