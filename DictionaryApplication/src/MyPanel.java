import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    Image mouseIcon = new ImageIcon("src/image/mouseIcon.png").getImage();
    Image bg = new ImageIcon("src/image/bg1.jpg").getImage();

    public MyPanel() {

        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                mouseIcon,
                new Point(0, 0),
                "Cursor"
                )
        );
        setFocusable(true);
        this.setLayout(null);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        super.paintComponent(g2D);
        g2D.drawImage(bg, 0, 0, MyFrame.W_FRAME, MyFrame.H_FRAME, null);
        g2D.setFont(new Font(null, Font.BOLD, 10));
        g2D.drawString("D I C T I O N A R Y  A P L I C A T I O N", 580, 20);
        g2D.setColor(Color.darkGray);
    }
}




