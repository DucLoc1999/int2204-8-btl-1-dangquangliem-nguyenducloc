package updateDict;

import data.DataBase;

import javax.swing.*;

public class RemoveWord extends NewFrame {
    protected JScrollPane scrollPane;
    protected JList<String> listW;
    protected DefaultListModel model1;
    protected DefaultListModel model2;
    public RemoveWord(){
        setTitle("Remove Word");
        model1 = new DefaultListModel<>();
        model2 = new DefaultListModel<>();
        listW = new JList<>();
        scrollPane = new JScrollPane(listW);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setBounds(60, 90, 500, 180);
        ctn.add(scrollPane);

    }

    public JList<String> getListW() {
        return listW;
    }

    @Override
    public void excute(DataBase dict) {
        dict.removeWord(listW.getSelectedValue());

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
