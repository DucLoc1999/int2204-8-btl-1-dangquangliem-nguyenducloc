import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    static final int W_FRAME = 800;
    static final int H_FRAME = 600;
    public static MyPanel panel;
    public static Container ctn;
    public MyFrame() throws HeadlessException {
        setTitle("Dictionary");
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // add panel
        panel = new MyPanel();
        ctn = getContentPane();
        ctn.add(panel);
        // add menubar
        MenuBar menuBar = new MenuBar();
        setJMenuBar(menuBar);
        // add management
        DictionaryManager managerment = new DictionaryManager(panel, menuBar);


    }


}
