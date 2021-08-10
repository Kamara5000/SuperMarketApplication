//a splash is use to display an image or animation first before loading the main application
//you can add, images, progress bar, animation on a splash

import javax.swing.*;



import java.awt.*;

public class Splash extends JWindow {
   public Splash(){
       JWindow j = new JWindow();
       j.setLayout(new BorderLayout());

       Dimension d  = Toolkit.getDefaultToolkit().getScreenSize();

       Icon img = new ImageIcon(this.getClass().getResource("images/shop1.jpg"));
       JLabel label = new JLabel(img);
       label.setSize(200,300);
       JLabel wlcm = new JLabel("Welcome To Kamara Supermarket");
       wlcm.setForeground(Color.GREEN);
       wlcm.setFont(new Font("Algerian",  Font.BOLD, 30));
       JPanel wlcmpanel = new JPanel( new FlowLayout());
       wlcmpanel.add(wlcm);
       j.getContentPane().add(wlcmpanel, BorderLayout.NORTH);
       j.getContentPane().add(label, BorderLayout.CENTER);
       j.setBounds(((int)d.getWidth()-722)/2, ((int)d.getHeight()-401)/2,722,401);
       j.setVisible(true);

       try {
           Thread.sleep(6000);
           //System.exit(0);
       } catch (InterruptedException e) {
           //TODO: handle exception

           e.printStackTrace();
       }
       j.setVisible(false);
   } 


public static void main(String[] args) {
    Splash sp = new Splash();
}
}