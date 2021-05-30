import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StartFrame {
    public StartFrame(AudioPlayer APAer){
        JFrame frameStart = new JFrame("扫雷启动器");
        frameStart.setSize(800,500);
        frameStart.setLocationRelativeTo(null);
        frameStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("Art/backP5.jpg");
        JLabel BACK = new JLabel(image);
        BACK.setBounds(0, 0, frameStart.getWidth(), frameStart.getHeight());
        frameStart.getLayeredPane().add(BACK, new Integer(Integer.MIN_VALUE));
        JPanel panelStart = (JPanel) frameStart.getContentPane();
        panelStart.setOpaque(false);
        panelStart.setLayout(null);

        JButton startSmall = new JButton("Easy");
        startSmall.setBounds(50,50,100,30);
        JButton startMid = new JButton("Normal");
        startMid.setBounds(50,100,100,30);
        JButton startBig = new JButton("Hard");
        startBig.setBounds(50,150,100,30);
        JButton selfDf = new JButton("Self Define");
        selfDf.setBounds(50,200,100,30);
        JButton load = new JButton("Load");
        load.setBounds(50,300,100,30);
        JButton story = new JButton("Story");
        story.setBounds(50,350,100,30);
        panelStart.add(startSmall);
        panelStart.add(startMid);
        panelStart.add(startBig);
        panelStart.add(selfDf);
        panelStart.add(load);
        panelStart.add(story);

        JTextArea Xt = new JTextArea();
        Xt.setBackground(Color.LIGHT_GRAY);
        JLabel XL=new JLabel("X");
        XL.setBounds(200, 65, 100, 20);
        Xt.setBounds(300, 65, 130, 20);
        JTextArea Yt = new JTextArea();
        Yt.setBackground(Color.LIGHT_GRAY);
        JLabel YL=new JLabel("Y");
        YL.setBounds(200, 95, 100, 20);
        Yt.setBounds(300, 95, 130, 20);
        JTextArea Bnt = new JTextArea();
        Bnt.setBackground(Color.LIGHT_GRAY);
        JLabel BNL=new JLabel("Boom Number");
        BNL.setBounds(200, 125, 100, 20);
        Bnt.setBounds(300, 125, 130, 20);
        JTextArea Pnt = new JTextArea();
        Pnt.setBackground(Color.LIGHT_GRAY);
        JLabel PNL=new JLabel("Player Number");
        PNL.setBounds(200, 155, 100, 20);
        Pnt.setBounds(300, 155, 130, 20);
        JTextArea Ntt = new JTextArea();
        Ntt.setBackground(Color.LIGHT_GRAY);
        JLabel NTL=new JLabel("Operate Per Turn");
        NTL.setBounds(200, 185, 100, 20);
        Ntt.setBounds(300, 185, 130, 20);
        JTextArea SaveT = new JTextArea();
        SaveT.setBackground(Color.LIGHT_GRAY);
        JLabel SVL=new JLabel("Save Name");
        SVL.setBounds(200, 300, 100, 20);
        SaveT.setBounds(300, 300, 130, 20);
        panelStart.add(XL);
        panelStart.add(YL);
        panelStart.add(BNL);
        panelStart.add(PNL);
        panelStart.add(NTL);
        panelStart.add(SVL);
        panelStart.add(Xt);
        panelStart.add(Yt);
        panelStart.add(Bnt);
        panelStart.add(Pnt);
        panelStart.add(Ntt);
        panelStart.add(SaveT);

        startSmall.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Main.NewGame(9, 9, 10, 2, 1, "No",APAer,frameStart);
                    frameStart.setVisible(false);
                }
            }
        });
        startMid.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Main.NewGame(16, 16, 40, 2, 1, "No",APAer,frameStart);
                    frameStart.setVisible(false);
                }
            }
        });
        startBig.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Main.NewGame(30, 16, 99, 2, 1, "No",APAer,frameStart);
                    frameStart.setVisible(false);
                }
            }
        });

        selfDf.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = 15;
                    int y = 15;
                    int playerNumber = 3;
                    int boomNumber = 15;
                    int NumberPerTurn = 2;
                    if (Xt.getText().length()!=0 && Yt.getText().length()!=0 && Pnt.getText().length()!=0 &&Bnt.getText().length()!=0 && Ntt.getText().length()!=0)
                        if (Integer.parseInt(Xt.getText())<31 &&Integer.parseInt(Yt.getText())<25 &&Integer.parseInt(Bnt.getText())<=0.5*Integer.parseInt(Xt.getText())*Integer.parseInt(Yt.getText())){
                            x = Integer.parseInt(Xt.getText());
                            y = Integer.parseInt(Yt.getText());
                            playerNumber = Integer.parseInt(Pnt.getText());
                            boomNumber = Integer.parseInt(Bnt.getText());
                            NumberPerTurn = Integer.parseInt(Ntt.getText());
                        }
                    Main.NewGame(x, y, boomNumber, playerNumber, NumberPerTurn, "No",APAer,frameStart);
                    frameStart.setVisible(false);
                }
            }
        });

        load.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String SaveStr="";
                    String saveNm=SaveT.getText();
                    if (saveNm.length()==0)saveNm="AutoSave";
                    try {
                        BufferedReader loadTxt = new BufferedReader(new FileReader(saveNm+".txt"));
                        SaveStr = loadTxt.readLine();
                        loadTxt.close();
                        //System.out.print(SaveStr);
                    } catch (IOException ex) {}
                    int x=Integer.parseInt(SaveStr.substring(1,3));
                    int y=Integer.parseInt(SaveStr.substring(4,6));
                    int bn=Integer.parseInt(SaveStr.substring(8,11));
                    int pn=Integer.parseInt(SaveStr.substring(13,14));
                    int np=Integer.parseInt(SaveStr.substring(16,18));
                    Main.NewGame(x, y, bn, pn, np, SaveStr,APAer,frameStart);
                    frameStart.setVisible(false);
                }
            }
        });
        story.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    GameStory tmp= new GameStory(frameStart);
                    frameStart.setVisible(false);
                }
            }
        });

        frameStart.setVisible(true);
    }
}
