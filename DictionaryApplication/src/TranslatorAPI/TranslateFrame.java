package TranslatorAPI;

import com.sun.xml.internal.ws.handler.HandlerException;

import javax.swing.*;
import java.awt.*;

public class TranslateFrame extends JFrame {
    private static final int W_FRAME = 600;
    private static final int H_FRAME = 400;
    private Container ctn;
    private JLabel lbWords;
    private JLabel lbResult;
    private JTextArea txtWords;
    private JTextArea txtResult;
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    public JButton btnTsl;
    public JButton btnFromImage;

    public JTextArea getTxtWords() {
        return txtWords;
    }

    public JTextArea getTxtResult() {
        return txtResult;
    }

    public TranslateFrame() throws HandlerException {
        setTitle("Translate");
        setResizable(false);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        init();
        add(ctn);
        setVisible(true);
    }
    public void init(){

        ctn = new Container();
        lbWords = new JLabel("Words");
        lbWords.setBounds(10, 25, 50, 30);
        lbResult = new JLabel("Result");
        lbResult.setBounds(10, 160, 50, 30);

        txtWords = new JTextArea();
        txtWords.setBackground(Color.PINK);
        txtResult = new JTextArea();
        txtResult.setBackground(Color.LIGHT_GRAY);
        scroll1 = new JScrollPane(txtWords);
        scroll1.setBorder(BorderFactory.createLoweredBevelBorder());
        scroll1.setBounds(60, 30, 500, 120);
        scroll2 = new JScrollPane(txtResult);
        scroll2.setBorder(BorderFactory.createLoweredBevelBorder());
        scroll2.setBounds(60,160, 500, 150);

        btnTsl = new JButton("Translate");
        btnTsl.setBounds(470, 320, 90, 30);

        btnFromImage = new JButton("Image");
        Image img = new ImageIcon("src/image/fromImage.png").getImage();
        btnFromImage.setIcon(new ImageIcon(img));
        btnFromImage.setBounds(360, 320, 90, 30);

        ctn.add(lbWords);
        ctn.add(lbResult);
        ctn.add(scroll1);
        ctn.add(scroll2);
        ctn.add(btnTsl);
        ctn.add(btnFromImage);

    }
}
