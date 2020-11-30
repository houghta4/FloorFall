package GameLaunch;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        setTitle("FloorFall");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
