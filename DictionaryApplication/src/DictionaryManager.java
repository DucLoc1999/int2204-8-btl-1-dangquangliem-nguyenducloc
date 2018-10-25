import TranslatorAPI.ImageToText;
import TranslatorAPI.Translate;
import TranslatorAPI.TranslateFrame;
import container.ChooseWord;
import container.ListWord;
import container.RenderWord;
import data.DataBase;
import recorder.CaptureVoice;
import recorder.SpeechRecognize;
import textToSpeech.TextToSpeech;
import updateDict.AddWord;
import updateDict.ChangeWord;
import updateDict.RemoveWord;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class DictionaryManager {
    private String FILE_PATH = "src\\file\\E_V.txt";
    private JLabel lb;
    private ChooseWord chooseWord;
    private ListWord listWords;
    private RenderWord renderWord;
    private DataBase dict;

    private JButton btnVoice;

    private JButton btnRecorder;
    private JFileChooser fileChooser;
    private static CaptureVoice capture;
    private static Thread Capturewait;

    private JLabel lbLoading;


    public DictionaryManager(JPanel panel, JMenuBar menuBar){
        init();
        add(panel);
        doNothing(panel, menuBar);
    }
    public void init(){
        dict = new DataBase();
        lb = new JLabel("Nhập từ");
        chooseWord = new ChooseWord();
        listWords = new ListWord(dict);
        renderWord = new RenderWord();

        btnRecorder = new JButton();
        try {
            Image img = new ImageIcon("src/image/recorder.png").getImage();
            btnRecorder.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        btnRecorder.setBounds(115, 36, 20, 20);


        btnVoice = new JButton();
        Image imgVoice = new ImageIcon("src/image/voice.png").getImage();
        btnVoice.setIcon(new ImageIcon(imgVoice));
        btnVoice.setBounds(700, 35, 40, 40);
        fileChooser = new JFileChooser();

        Image imgIcon = new ImageIcon("src/image/loading.gif").getImage();
        lbLoading = new JLabel(new ImageIcon(imgIcon));
        lbLoading.setBounds(280, 80, 480, 450);
        lbLoading.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void setVisiable(boolean bl){
        btnVoice.setVisible(bl);
        btnRecorder.setVisible(bl);
        chooseWord.getTxtChW_().setVisible(bl);
        renderWord.getPanelBd_().setVisible(bl);
        listWords.getScroll_().setVisible(bl);
    }

    public void add(JPanel panel) {
        //add label
        panel.add(lb);

        //add choose word
        panel.add(chooseWord.addCW());

        //add list word
        panel.add(listWords.addLW());

        //add render word
        panel.add(renderWord.addRW());

        //add btn choose
        panel.add(btnRecorder);

        panel.add(btnVoice);

        panel.add(lbLoading);
    }
    public void doNothing(JPanel panel, JMenuBar menuBar) {
        lbLoading.setVisible(false);
        dict.readFile(FILE_PATH);
        listWords.printWord(dict);
        chooseWord.getTxtChW_().setFocusable(true);

        // event Chooseword
        chooseWord.getTxtChW_().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //listWords.getMyList_().clearSelection();
                String s = chooseWord.getTxtChW_().getText().trim();
                if (s.compareTo("") == 0) {
                    listWords.printWord(dict);
                } else {
                    if ((s.length() == 1) && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                        chooseWord.getTxtChW_().setText("");
                        listWords.printWord(dict);
                    } else
                        listWords.printWord(dict, s);
                }
            }
        });

        //test add word
        btnVoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //setVisiable(false);
                String s = listWords.getMyList_().getSelectedValue();
                TextToSpeech.speak(s);

            }
        });

        //           LISTWORD CONECT RENDERWORD
        listWords.getMyList_().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                lbLoading.setVisible(false);
                renderWord.getPanelBd_().setVisible(true);
                String s = listWords.getMyList_().getSelectedValue();
                if (s != null)
                    renderWord.printInfo(dict, s);
                //listWords.getMyList_().clearSelection();
            }
        });

        // Btn recorder
        btnRecorder.addActionListener(new ActionListener() { /// Loc: my addition
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                lbLoading.setVisible(true);
                renderWord.getPanelBd_().setVisible(false);
                if(CaptureVoice.alive){
                    CaptureVoice.finish();
                    try {
                        Thread.sleep(500);//
                    } catch (InterruptedException ex) { }

                } else {
                    Capturewait = new Thread(new Runnable() {
                        public void run() {
                            DictionaryManager.capture = new CaptureVoice();/////////////
                            DictionaryManager.capture.start();

                            while(DictionaryManager.capture.isAlive()){}

                            try {
                                Thread.sleep(500);//
                            } catch (InterruptedException ex) {}

                            System.out.println(CaptureVoice.getData().length);
                            String result = new SpeechRecognize().voiceToText(CaptureVoice.getData());
                            chooseWord.getTxtChW_().setText(result);
                            DictionaryManager.capture = null;
                        }
                    });

                    Capturewait.start();
                }
            }
        });

        // UPDATE DICT

        // event add word
        menuBar.getMenu(0).getItem(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddWord addW = new AddWord();
                addW.getBtnOK().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addW.excute(dict);
                    }
                });
            }
        });

        //event change word
        menuBar.getMenu(0).getItem(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeWord changeW = new ChangeWord();
                changeW.getListW().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        String s = changeW.getListW().getSelectedValue();
                        if (s != null)
                            changeW.getWordInfo().setText(dict.getInfoWord(s));

                    }
                });
                changeW.getTxtWord().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        changeW.getListW().clearSelection();
                        String s = changeW.getTxtWord().getText().trim();
                        if (s.compareTo("") == 0) {
                            changeW.printWord(dict);
                        } else {
                            if ((s.length() == 1) && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                                changeW.getTxtWord().setText("");
                                changeW.printWord(dict);
                            } else
                                changeW.printWord(dict, s);
                        }
                        super.keyReleased(e);
                    }
                });
                changeW.getBtnOK().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            changeW.excute(dict);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
            }
        });

        //event remove word
        menuBar.getMenu(0).getItem(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveWord removeW = new RemoveWord();
                removeW.getTxtWord().addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        removeW.getListW().clearSelection();
                        String s = removeW.getTxtWord().getText().trim();
                        if (s.compareTo("") == 0) {
                            removeW.printWord(dict);
                        } else {
                            if ((s.length() == 1) && (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                                removeW.getTxtWord().setText("");
                                removeW.printWord(dict);
                            } else
                                removeW.printWord(dict, s);
                        }
                        super.keyReleased(e);
                    }
                });

                removeW.getBtnOK().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            removeW.excute(dict);
                        }catch (Exception exc){
                            exc.printStackTrace();
                        }

                    }
                });
            }
        });

        // TOOL

        //event translator
        menuBar.getMenu(1).getItem(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TranslateFrame tl = new TranslateFrame();
                tl.btnTsl.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String result = Translate.execute(tl.getTxtWords().getText(), "en", "vi");
                            tl.getTxtResult().setText(result);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                //  Image to text
                tl.btnFromImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int returnVal = fileChooser.showOpenDialog(new MyFrame());
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            java.io.File file = fileChooser.getSelectedFile();
                            String filePath = file.toString();
                            System.out.println(filePath);

                            // get text from image
                            String textFromImage = ImageToText.getText(file.toString());
                            System.out.println(textFromImage);

                            //add text to txtwords
                            tl.getTxtWords().setText(textFromImage);
                        }
                    }
                });
            }
        });
    }
}
