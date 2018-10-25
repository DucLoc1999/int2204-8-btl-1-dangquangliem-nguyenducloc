package updateDict;

import data.DataBase;

import javax.swing.*;
import java.awt.*;

    public abstract class NewFrame extends JFrame{
        protected static final int W_FRAME = 600;
        protected static final int H_FRAME = 400;

        protected JLabel lbWord;
        protected JTextField txtWord;
        protected JButton btnOK;
        protected Container ctn;

        public JButton getBtnOK() {
            return btnOK;
        }

        public NewFrame()throws HeadlessException{
        setResizable(false);
        setSize(W_FRAME, H_FRAME);
        setLocationRelativeTo(null);
        init();
        add(ctn);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

        public JTextField getTxtWord() {
            return txtWord;
        }

        public void init(){
        //lb word
        lbWord = new JLabel("Word");
        lbWord.setBounds(10, 25, 50, 30);

        //txtword
        txtWord = new JTextField();
        txtWord.setBorder(BorderFactory.createLoweredBevelBorder());
        txtWord.setBackground(Color.PINK);
        txtWord.setBounds(60, 30, 500, 30);

        //btnOK
        btnOK = new JButton("OK");
        btnOK.setBounds(500, 300, 55, 30);

        ctn = new Container();
        ctn.setLayout(null);
        ctn.add(lbWord);
        ctn.add(txtWord);
        ctn.add(btnOK);
    }
    public abstract void excute(DataBase dict);

}
