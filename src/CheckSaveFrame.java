import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckSaveFrame {
    public CheckSaveFrame(String CheckSaveStr,StringBuilder SaveStrTxt){
        JFrame outW=new JFrame();
        JButton ob=new JButton();
        outW.setLocationRelativeTo(null);
        outW.setSize(200,100);
        outW.add(ob);
        if (CheckSaveStr.equals(SaveStrTxt.toString()+"e"))ob.setText("存档相同");
        else ob.setText("存档不同");
        outW.setVisible(true);
        ob.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)outW.dispose();
            }

        });
    }
}
