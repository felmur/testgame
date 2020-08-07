import javax.swing.*;
import java.awt.*;

public class game {
    private static String prgname="TestGame";
    private static int width=1280;
    private static int height=720;

    game(){
        log.d("Starting "+prgname+"...");
        Dimension dimension = new Dimension(width, height);
        JFrame win = new JFrame(prgname);
        win.setPreferredSize(dimension);
        win.setMaximumSize(dimension);
        win.setMinimumSize(dimension);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.pack();
        win.setVisible(true);
    }


}
