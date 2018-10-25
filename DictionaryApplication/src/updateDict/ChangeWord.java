package updateDict;

import data.DataBase;

import javax.swing.*;

public class ChangeWord extends NewFrame {
    protected JList<String> listW;
    protected JScrollPane scrollPane;
    protected JScrollPane scroll2;
    protected DefaultListModel<String> model1;
    protected DefaultListModel<String> model2;
    protected JTextPane wordInfo;
    public ChangeWord(){
        setTitle("Change Word");
        initChange();
    }

    public JList<String> getListW() {
        return listW;
    }

    public JTextPane getWordInfo() {
        return wordInfo;
    }

    @Override
    public void excute(DataBase dict){
        dict.changeWordInfo(listW.getSelectedValue(), wordInfo.getText());
    }
    public void initChange(){

        listW = new JList<>();
        model1 = new DefaultListModel<>();
        model2 = new DefaultListModel<>();

        scrollPane = new JScrollPane(listW);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setBounds(60, 90, 150, 180);
        ctn.add(scrollPane);

        wordInfo = new JTextPane();
       // wordInfo.setBounds(220, 90, 340, 180);
        wordInfo.setContentType("text/html");

        scroll2 = new JScrollPane(wordInfo);
        scroll2.setBounds(220, 90, 340, 180);
        scroll2.setBorder(BorderFactory.createLoweredBevelBorder());
        ctn.add(scroll2);
    }
    public void printWord(DataBase dict) {
        listW.setModel(model1);
        if(!model1.isEmpty())
            model1.clear();
        for(String word : dict.getKeyList()){
            model1.addElement(word);
        }
    }
    public void printWord(DataBase dict, String s) {
        if (!model2.isEmpty())
            model2.clear();
        for (String word : dict.getKeyList()) {
            if (word.startsWith(s)) {
                model2.addElement(word);
            }
        }
        listW.setModel(model2);

    }
}
