import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameStory extends JFrame {
    int x;
    public GameStory(JFrame frameS) {
       ImageIcon [] background = new ImageIcon[4];
        x=0;
        background[0] = new ImageIcon("Art/S001.png");
        background[1] = new ImageIcon("Art/S002.png");
        background[2] = new ImageIcon("Art/S003.png");
        background[3] = new ImageIcon("Art/S004.png");



        JButton btn1 = new JButton("上一页");
        btn1.setBounds(50, 30, 100, 30);

        JButton btn2 = new JButton("下一页");
        btn2.setBounds(450, 30, 100, 30);

        JButton re = new JButton("返回主页");
        re.setBounds(650, 30, 100, 30);


        JLabel myLabel = new JLabel(background[0]);
        myLabel.setBounds(0,0,background[0].getIconWidth(),background[0].getIconHeight());

        JPanel myPanel = new JPanel();
        myPanel = (JPanel) this.getContentPane();
        myPanel.setOpaque(false);
        myPanel.setLayout(new FlowLayout());
        setBounds(0,0,background[0].getIconWidth(),background[0].getIconHeight());

/*
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JLabel myLabel = new JLabel(background2);
                myLabel.setBounds(0,0,background2.getIconWidth(),background2.getIconHeight());
                setBounds(0,0,background2.getIconWidth(),background2.getIconHeight());
            }

        });
        btn1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JLabel myLabel = new JLabel(background1);
                myLabel.setBounds(0,0,background1.getIconWidth(),background1.getIconHeight());
                setBounds(0,0,background1.getIconWidth(),background1.getIconHeight());
            }

        });

 */

        myPanel.add(btn1);
        myPanel.add(btn2);
        myPanel.add(re);


        this.getLayeredPane().add(myLabel,new Integer(Integer.MIN_VALUE));

        setTitle("故事背景");
        setLocationRelativeTo(null);


        this.setResizable(false);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(x<3) {
                    ImageIcon img = background[x + 1];
                    x++;
                    myLabel.setIcon(img);

                    myLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

                }
            }

        });
        re.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameS.setVisible(true);
                dispose();
            }

        });




        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(x>0) {
                    ImageIcon img = background[x - 1];
                    x--;
                    myLabel.setIcon(img);

                    myLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());

                }
            }

        });

    }




}
