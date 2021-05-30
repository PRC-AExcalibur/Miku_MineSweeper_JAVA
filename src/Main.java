import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //AudioPlayer
        AudioPlayer APAer = new AudioPlayer();
        APAer.getAudioPlayer();
        APAer.getBGMPlayer();

        //FrameStart
        StartFrame startFm= new StartFrame(APAer);

    }

    public static void NewGame(int x, int y, int boomNumber, int playerNumber, int NumberPerTurn, String SaveStr, AudioPlayer APer, JFrame frameStart) {
        StringBuilder SaveStrTxt=new StringBuilder();
        SaveStrTxt.append(String.format("x%02dy%02dbn%03dpn%01dnp%02dop",x,y,boomNumber,playerNumber,NumberPerTurn));//x,y,bn,pn,np,op
        int[][] opList=new int[x][y];
        if (!SaveStr.contains("op")){
            BoomCreate bc = new BoomCreate();
            opList = bc.outputList(x, y, boomNumber);
            while (bc.is9in()) opList = bc.outputList(x, y, boomNumber);
        }
        else{
            int sIdx=SaveStr.indexOf("op")+2;
            int i=0;int j=0;
            for (int idx=0;idx<x*y;idx++){
                opList[i][j]=Integer.parseInt(SaveStr.substring(sIdx, sIdx + 2));
                j++;
                sIdx=sIdx + 2;
                if (j==y){
                    j=0;
                    i++;
                }
            }
        }
        ArrayList<int[]> bmList=new ArrayList();
        ArrayList<int[]> wyList=new ArrayList();
        for (int i=0;i<x;i++)for (int j=0;j<y;j++) {
            int[] tmp = new int[2];
            tmp[0] = i;
            tmp[1] = j;
            if (opList[i][j] == -1) bmList.add(tmp);
            else  wyList.add(tmp);
        }
        //Frame
        JFrame frame = new JFrame("扫雷");
        frame.setSize(40 * x + 420, Math.max(40 * y + 90, playerNumber * 220 + 110));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        //PlayerBd
        PlayerBoard[] PlayerList = new PlayerBoard[playerNumber];
        for (int i = 0; i < playerNumber; i++) {
            PlayerBoard tmp = new PlayerBoard();
            PlayerList[i] = tmp;
            panel.add(PlayerList[i].getButton(i));
            panel.add(PlayerList[i].getLabel());
            PlayerList[i].StrData();
            int finalI = i;
            PlayerList[i].getButton(i).addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) PlayerList[finalI].PeoplePlay();
                    if (e.getButton() == MouseEvent.BUTTON3) PlayerList[finalI].AiPlay();
                }
            });
        }

        //StateBd
        WinFrame winF=new WinFrame();
        StateBoard stateBd = new StateBoard();
        panel.add(stateBd.getLabel(150, 5, NumberPerTurn, playerNumber, boomNumber,panel,40 * x + 420,PlayerList,x,y));
        stateBd.StrData();

        //Ground
        panel.setLayout(null);
        GroundButton[][] ButtonList = new GroundButton[x][y];
        for (int i = 0; i < x; i++)
            for (int j = 0; j < y; j++) {
                SaveStrTxt.append(String.format("%02d",opList[i][j]));
                GroundButton tmp = new GroundButton();
                tmp.setButton(i, j, opList[i][j]);
                ButtonList[i][j] = tmp;
                panel.add(ButtonList[i][j].getButton());
                int finalI = i;
                int finalJ = j;
                ButtonList[i][j].getButton().addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            ClickUpdate(1,finalI,finalJ,APer,ButtonList,stateBd,PlayerList, winF,frameStart,frame,bmList,wyList,SaveStrTxt);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            ClickUpdate(3,finalI,finalJ,APer,ButtonList,stateBd,PlayerList, winF,frameStart,frame,bmList,wyList,SaveStrTxt);
                        }
                    }
                });

            }
        SaveStrTxt.append("c");

        //CheatModel Button
        JButton CheatButton = new JButton(new ImageIcon("Art/flag (1).png"));
        CheatButton.setBounds(10, 5, 100, 30);
        panel.add(CheatButton);
        CheatButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    for (int i = 0; i < x; i++) for (int j = 0; j < y; j++) ButtonList[i][j].cheat();
                if (e.getButton() == MouseEvent.BUTTON3)
                    for (int i = 0; i < x; i++) for (int j = 0; j < y; j++) ButtonList[i][j].no_cheat();
            }
        });

        //AutoClick
        if (!SaveStr.equals("No")) {
            int Click = SaveStr.indexOf("c") + 1;
            int numClick = (SaveStr.indexOf("e") - Click) / 5;
            int[] ClickX = new int[numClick];
            int[] ClickY = new int[numClick];
            int[] ClickM = new int[numClick];
            for (int i = 0; i < numClick; i++) {
                ClickX[i] = Integer.parseInt(SaveStr.substring(Click, Click + 2));
                ClickY[i] = Integer.parseInt(SaveStr.substring(Click + 2, Click + 4));
                ClickM[i] = Integer.parseInt(SaveStr.substring(Click + 4, Click + 5));
                int finalI = ClickX[i];
                int finalJ = ClickY[i];
                ClickUpdate(ClickM[i],finalI,finalJ,APer,ButtonList,stateBd,PlayerList, winF,frameStart,frame,bmList,wyList,SaveStrTxt);
                Click = Click + 5;
            }
        }

        //SaveButton
        JTextArea SaveName=new JTextArea();
        SaveName.setBounds(40 * x + 420-170, 70, 130, 20);
        panel.add(SaveName);
        JButton SaveButton = new JButton("Save");
        SaveButton.setBounds(40 * x + 420-170, 20, 130, 30);
        panel.add(SaveButton);
        SaveButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    String sName;
                    if (SaveName.getText().length()==0)sName="AutoSave.txt";
                    else sName=SaveName.getText()+".txt";
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(sName));
                        bw.write(SaveStrTxt.toString());
                        bw.write("e");
                        bw.close();
                    } catch (IOException ex) { }
                }
            }
        });
        //CheckButton
        JTextArea CheckName=new JTextArea();
        CheckName.setBounds(40 * x + 420-170, 170, 130, 20);
        panel.add(CheckName);
        JButton CheckSaveButton = new JButton("CheckSave");
        CheckSaveButton.setBounds(40 * x + 420-170, 120, 130, 30);
        panel.add(CheckSaveButton);
        CheckSaveButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String CheckSaveStr="";
                String cName;
                if (CheckName.getText().length()==0)cName="AutoSave.txt";
                else cName=CheckName.getText()+".txt";
                if (e.getButton() == MouseEvent.BUTTON1){
                    try {
                        BufferedReader CheckTxt = new BufferedReader(new FileReader(cName));
                        CheckSaveStr = CheckTxt.readLine();
                        CheckTxt.close();
                    } catch (IOException ex) { }
                    CheckSaveFrame CheckSaveFm=new CheckSaveFrame(CheckSaveStr,SaveStrTxt);
                }
            }
        });
        //ReturnButton
        JButton ReturnMenuButton = new JButton("ReturnMenu");
        ReturnMenuButton.setBounds(40 * x + 420-170, 220, 130, 30);
        panel.add(ReturnMenuButton);
        ReturnMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    frameStart.setVisible(true);
                    frame.dispose();
                }
            }
        });
        //findBoomButton findWayButton
        Random rdd = new Random();
        ItemFind_Button(1,panel,x ,rdd,ButtonList,bmList);
        //findWayButton
        ItemFind_Button(2,panel,x ,rdd,ButtonList,wyList);

        frame.setVisible(true);
    }

    private static void RestartGame(int x,int y,int boomNumber,int playerNumber,int NumberPerTurn,int i,int j,int k,JFrame frame,AudioPlayer APAer,JFrame frameStart){
        frame.dispose();
        String click=String.format("c%02d%02d%01de", i,j,k);
        NewGame(x,y,boomNumber,playerNumber,NumberPerTurn,click,APAer,frameStart);
    }

    public static void ClickUpdate(int mouse,int finalI,int finalJ,AudioPlayer APer,GroundButton[][] ButtonList,StateBoard stateBd,PlayerBoard[] PlayerList,
                                   WinFrame winF,JFrame frameStart,JFrame frame,ArrayList<int[]> bmList,ArrayList<int[]> wyList,StringBuilder SaveStrTxt){
        if (ButtonList[finalI][finalJ].getStateNumber()==-1&&stateBd.getOperatorNumber()==0)
            RestartGame(stateBd.getX(),stateBd.getY(),stateBd.getBoomNumber(),stateBd.getPlayerNumber(),stateBd.getNumberPerTurn(),finalI,finalJ,mouse,frame,APer,frameStart);
        int iTmp=0;
        if (mouse==1)iTmp = ButtonList[finalI][finalJ].open(bmList,wyList,ButtonList,stateBd.getX(),stateBd.getY());
        else if (mouse==3)iTmp = ButtonList[finalI][finalJ].flag(bmList,wyList);
        if (iTmp != -999) {
            if (mouse==1&&APer!=null)APer.getLeftPlayer(iTmp);
            else if (mouse==3&&APer!=null)APer.getRightPlayer(iTmp);
            int jTmp = stateBd.getPlayerIndex();
            if (iTmp == -1) {
                int scoreTmp = PlayerList[jTmp].getPlayerScore();
                if (mouse==1){
                    PlayerList[jTmp].SetPlayerScore(scoreTmp - 1);
                    stateBd.ChangePlayerScore(scoreTmp - 1);
                }
                else if (mouse==3){
                    PlayerList[jTmp].SetPlayerScore(scoreTmp + 1);
                    stateBd.ChangePlayerScore(scoreTmp + 1);
                }
            }
            else {
                if (mouse==3){
                    int WrongTimeTmp = PlayerList[jTmp].getPlayerWrongTime();
                    PlayerList[jTmp].SetPlayerWrongTime(WrongTimeTmp + 1);
                    stateBd.ChangePlayerWrongTime(1);
                }
            }
            PlayerList[jTmp].StrData();
            stateBd.OperatorNumberPlus(bmList,wyList,PlayerList,ButtonList,frameStart,frame,SaveStrTxt,winF);
            stateBd.StrData();
            winF.WinFrameCheck(stateBd,frameStart,frame);
            SaveClickAdd(SaveStrTxt,finalI,finalJ,mouse);
        }
    }

    private static void SaveClickAdd(StringBuilder Save,int i ,int j, int k){
        Save.append(String.format("%02d%02d%01d", i,j,k));
    }

    private static void ItemFind_Button(int type,JPanel panel,int x ,Random rdd, GroundButton[][] ButtonList,ArrayList<int[]> ListI){
        JButton findButton = new JButton();
        if (type==1) findButton = new JButton(new ImageIcon("Art/Item1.jpg"));
        else if(type==2) findButton = new JButton(new ImageIcon("Art/Item2.jpg"));
        if (type==1) findButton.setBounds(40 * x + 420-170, 320, 50, 50);
        else if (type==2)findButton.setBounds(40 * x + 420-90, 320, 50, 50);
        panel.add(findButton);
        findButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    boolean tmpR=false;
                    while (!tmpR && ListI.size()!=0){
                        int tW= rdd.nextInt(ListI.size());
                        tmpR=ButtonList[ListI.get(tW)[0]][ListI.get(tW)[1]].changeColor(type);
                        ListI.remove(tW);
                    }
                }
            }
        });
    }

}
