import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clock implements ActionListener, ChangeListener {
    JProgressBar progressbar;
    JLabel label;
    Timer timer;
    int time=30;
    StateBoard stateB;
    boolean Overtime=false;

    public Clock(JPanel panel,int x,int y,StateBoard stb) {
        stateB=stb;
        label=new JLabel();
        progressbar=new JProgressBar();
        progressbar.setOrientation(JProgressBar.HORIZONTAL);
        progressbar.setMinimum(0);
        progressbar.setMaximum(time);
        progressbar.setValue(time);
        progressbar.addChangeListener(this);
        progressbar.setPreferredSize(new Dimension(200,20));
        progressbar.setBorderPainted(false);
        progressbar.setBackground(Color.gray);
        progressbar.setBounds(x,y,130,20);
        label.setBounds(x,y+30,130,20);
        panel.add(label);
        panel.add(progressbar);
        reset();
    }
    public void reset(){
        Overtime=false;
        progressbar.setValue(time);
        timer=new Timer(1000, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==timer) {
            int value=progressbar.getValue();
            if(value>0) progressbar.setValue(--value);
            else {
                label.setText("超时,失误+1");
                timer.stop();
                stateB.SetOvertime();
            }
        }
    }

    public void stateChanged(ChangeEvent e1) {
        int value=progressbar.getValue();
        if(e1.getSource()==progressbar) {
            label.setText("剩余时间"+Integer.toString(value)+"s");
            label.setForeground(Color.black);
        }
    }
}
