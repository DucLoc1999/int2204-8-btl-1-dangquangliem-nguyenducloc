package container;

import data.DataBase;

import javax.swing.*;

public class ListWord {
    private String word_;
    private String[] listWord_;
    private int row_;
    private int colum_;
    private int x_;
    private int y_;
    private int width_;
    private int height_;
    private DefaultListModel<String> model1;
    private DefaultListModel<String> model2;
    private JList<String> myList_;
    private JScrollPane scroll_;
    private int rB = 0;
    private int rE ;
    private int sB = 0;
    private int sE = 0;

    public JList<String> getMyList_() {
        return myList_;
    }

    public void setMyList_(JList<String> myList_) {
        this.myList_ = myList_;
    }

    public ListWord(DataBase dict) {
        this.row_ = 500;
        this.colum_ = 200;
        this.x_ = 20;
        this.y_ = 80;
        this.width_ = 250;
        this.height_ = 450;
        model1 = new DefaultListModel<>();
        for(String word : dict.getKeyList()){
            model1.addElement(word);
        }
        model2 = new DefaultListModel<>();
        myList_ = new JList<>();
        scroll_ = new JScrollPane(myList_);
        scroll_.setBounds(x_, y_, width_, height_);
    }

    public JScrollPane getScroll_() {
        return scroll_;
    }

    public JScrollPane addLW() {
        return scroll_;
    }

    public void printWord(DataBase dict) {
//        for(String word : dict.getKeyList()){
//            model1.addElement(word);
//        }
        myList_.setModel(model1);
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
        myList_.setModel(model2);

    }
}

