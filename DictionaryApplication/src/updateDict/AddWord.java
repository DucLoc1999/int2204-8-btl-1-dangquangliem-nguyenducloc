package updateDict;

import data.DataBase;

import javax.swing.*;
import java.awt.*;

public class AddWord extends NewFrame {
    protected JTextPane txtInfo;
    protected JLabel lbInfo;

    public AddWord(){
        //Set title
        setTitle("Add Word");
        //lbInfo
        lbInfo = new JLabel("Info");
        lbInfo.setBounds(10, 95, 50, 30);
        ctn.add(lbInfo);

        //txtInfo
        txtInfo = new JTextPane();
        txtInfo.setContentType("text/html");
        txtInfo.setBackground(Color.LIGHT_GRAY);
        txtInfo.setBorder(BorderFactory.createLoweredBevelBorder());
        txtInfo.setBounds(60, 100, 500, 180);
        ctn.add(txtInfo);

    }
    @Override
    public void excute(DataBase dict) {
        dict.addWord(txtWord.getText(), txtInfo.getText());
    }
}
