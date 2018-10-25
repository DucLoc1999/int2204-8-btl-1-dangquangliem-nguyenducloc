import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {

    ImageIcon newIcon = new ImageIcon("src/image/new.png");
    ImageIcon changeIcon = new ImageIcon("src/image/change.png");
    ImageIcon removeIcon = new ImageIcon("src/image/remove.png");
    private JMenu updateMenu;
    private JMenu toolMenu;
    private JMenuItem addWordItem;
    private JMenuItem changeWordItem;
    private JMenuItem removeWordItem;

    private JMenuItem translator;


    public MenuBar() {
        init();
    }

    public void init() {
        //update
        updateMenu = new JMenu("Update");
        updateMenu.setMnemonic(KeyEvent.VK_U);

        addWordItem = new JMenuItem("Add word", newIcon);
        addWordItem.setMnemonic(KeyEvent.VK_A);

        changeWordItem = new JMenuItem("Change word",changeIcon);
        changeWordItem.setMnemonic(KeyEvent.VK_C);

        removeWordItem = new JMenuItem("Remove word",removeIcon);
        removeWordItem.setMnemonic(KeyEvent.VK_R);

        updateMenu.add(addWordItem);
        updateMenu.add(changeWordItem);
        updateMenu.add(removeWordItem);

        //tool
        toolMenu = new JMenu("Tool");
        toolMenu.setMnemonic(KeyEvent.VK_T);

        translator = new JMenuItem("Translator", new ImageIcon("src/image/translator.png"));
        translator.setMnemonic(KeyEvent.VK_T);

        toolMenu.add(translator);

        this.add(updateMenu);
        this.add(toolMenu);

    }

}
