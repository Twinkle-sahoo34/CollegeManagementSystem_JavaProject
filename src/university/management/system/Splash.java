package university.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {

    Thread t;

    Splash() {
        setLayout(null);  // important to set no layout manager

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/first.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1000, 700);  // set bounds when layout is null
        add(image);

        setVisible(true);

        // Start the thread to hide splash after delay
        t = new Thread(this);
        t.start();

        int x = 1;
        for (int i = 2; i <= 600; i += 4, x += 1) {
            setLocation(600 - ((i + x) / 2), 350 - (i / 2));
            setSize(1 + 3 * x, i + x / 2);

            try {
                Thread.sleep(7000);
            } catch (Exception e) {}
        }
    }

    // Implement run method outside constructor
    public void run() {
        try {
            Thread.sleep(7000);
            setVisible(false);
            // You can open next window here if needed
            // new NextWindow().setVisible(true);
            new Login();
        } catch (Exception e) {
            
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
