import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinFrame {
    private JFrame outW = new JFrame();
    private JButton ob = new JButton();
    public WinFrame() {
        outW = new JFrame();
        ob = new JButton();
        outW.setLocationRelativeTo(null);
        outW.setSize(200, 100);
        outW.add(ob);
        outW.setVisible(false);
    }

    public void WinFrameCheck(StateBoard stateBd,JFrame frameStart,JFrame frame){
            int winnerIndex=stateBd.winner();
            if (winnerIndex > 0) ob.setText(String.format("player%d 胜利", winnerIndex));
            else if (winnerIndex == -1) ob.setText(String.format("平局"));
            if (winnerIndex != 0) {
                outW.setVisible(true);
                ob.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            outW.dispose();
                            frameStart.setVisible(true);
                            frame.dispose();
                        }
                    }
                });
            }
    }

}
