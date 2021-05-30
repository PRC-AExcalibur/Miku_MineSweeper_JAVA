import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StateBoard {
    private Clock clk;
    private boolean overtime=false;
    private int OperatorNumber=0;
    private int x;
    private int y;
    private int BoomNumber;
    private int NumberPerTurn;
    private int PlayerNumber;
    private int BoomNumber_Now;
    private int PlayerIndex=0;
    private int[]PlayerScore;
    private int[]PlayerWrongTime;
    private int[][] PlayerWinList;
    private String data;
    private JLabel DataBoard=new JLabel();
    private Random rdd = new Random();
    private PlayerBoard[] PlayerL;
    public StateBoard() {
    }

    public JLabel getLabel(int x_pos,int y_pos,int NumberPerTurn,int PlayerNumber,int boomNumber,JPanel panel,int xm,PlayerBoard[] PlayerList,int x,int y){
        this.x=x;
        this.y=y;
        this.BoomNumber=boomNumber;
        PlayerL=PlayerList;
        this.NumberPerTurn=NumberPerTurn;
        this.PlayerNumber=PlayerNumber;
        BoomNumber_Now=boomNumber;
        PlayerScore=new int[PlayerNumber];
        PlayerWrongTime=new int[PlayerNumber];
        PlayerWinList=new int[PlayerNumber][3];//[Score][WrongT][PlayerX]
        for (int i=0;i<PlayerNumber;i++){
            PlayerScore[i]=0;
            PlayerWrongTime[i]=0;
        }
        DataBoard.setBounds(x_pos,y_pos,600,30);
        Font fn = new Font("宋体",Font.BOLD,12);
        DataBoard.setFont(fn);

        clk=new Clock(panel,xm-170,400,this);
        return DataBoard;
    }
    public void StrData(){
        data="有效点击数"+ (OperatorNumber)+" 玩家数："+(PlayerNumber)+" 每轮玩家行动数："+(NumberPerTurn)+" 当前玩家"+(PlayerIndex+1)+" 剩余炸弹："+(BoomNumber_Now);
        DataBoard.setText(data);
    }
    public int getPlayerIndex(){
        PlayerIndex =(OperatorNumber%(NumberPerTurn*PlayerNumber))/NumberPerTurn;
        return PlayerIndex;
    }
    public void OperatorNumberPlus(ArrayList<int[]>bmList,ArrayList<int[]>wyList,PlayerBoard[] PlayerList,GroundButton[][] ButtonList,JFrame frameStart,JFrame frame,StringBuilder SaveStrTxt,WinFrame winF){
        OperatorNumber++;
        clk.reset();
        if (PlayerList[this.getPlayerIndex()].isAi()) {
            int[] tmpXY = new int[2];
            int rc = rdd.nextInt(10);
            boolean OPai = (rc < 5);
            if (wyList.size()==0)tmpXY = bmList.get(rdd.nextInt(bmList.size()));
            else if (bmList.size()==0)tmpXY = wyList.get(rdd.nextInt(wyList.size()));
            else if (rc < 1) tmpXY = bmList.get(rdd.nextInt(bmList.size()));
            else tmpXY = wyList.get(rdd.nextInt(wyList.size()));
            int finalI = tmpXY[0];
            int finalJ = tmpXY[1];
            int mouseTMP=0;
            if (OPai)mouseTMP=1;
            else mouseTMP=3;
            Main.ClickUpdate(1,finalI,finalJ,null,ButtonList,this,PlayerList, winF,frameStart,frame,bmList,wyList,SaveStrTxt);
        }
    }
    public void ChangePlayerScore(int score){
        PlayerScore[PlayerIndex]=score;
        BoomNumber_Now=BoomNumber_Now-1;
    }
    public void ChangePlayerWrongTime(int wrongTimeAdd){ PlayerWrongTime[PlayerIndex]=PlayerWrongTime[PlayerIndex]+wrongTimeAdd; }

    public int getOperatorNumber(){ return OperatorNumber; }

    public int winner(){
        if (PlayerNumber==1){
            if (BoomNumber_Now==0) return 1;
            else return 0;
        }
        else{
        int[] tmpPS = Arrays.copyOf(PlayerScore, PlayerScore.length);
        int[] tmpPW = Arrays.copyOf(PlayerWrongTime, PlayerWrongTime.length);
        List<Integer> tmpPSL=new ArrayList<>();
        List<Integer> tmpPWL=new ArrayList<>();
        for (int i=0;i<tmpPS.length;i++)tmpPSL.add(tmpPS[i]);
        Arrays.sort(tmpPS);
        if (tmpPS[PlayerNumber-1]>BoomNumber_Now+tmpPS[PlayerNumber-2])return tmpPSL.indexOf(tmpPS[PlayerNumber-1])+1;
        else if (BoomNumber_Now==0 && tmpPS[PlayerNumber-1]==tmpPS[PlayerNumber-2]){
            for (int i=0;i<PlayerNumber;i++)if (tmpPSL.get(i)==tmpPS[PlayerNumber-1])tmpPWL.add(tmpPW[i]);
            tmpPWL.sort(Comparator.naturalOrder());
            if (tmpPWL.get(0).equals(tmpPWL.get(1)))return -1;
            else for (int i=0;i<PlayerNumber;i++)if (tmpPSL.get(i)==tmpPS[PlayerNumber-1] &&tmpPWL.get(0)==tmpPW[i])return i+1;
        }
        return 0;
        }
    }

    public void SetOvertime() {
        getPlayerIndex();
        ChangePlayerWrongTime(1);
        PlayerL[this.getPlayerIndex()].SetPlayerWrongTime(PlayerL[this.getPlayerIndex()].getPlayerWrongTime()+1);
        PlayerL[this.getPlayerIndex()].StrData();
        StrData();
    }
    public int getNumberPerTurn() { return NumberPerTurn; }
    public int getPlayerNumber() { return PlayerNumber; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getBoomNumber() { return BoomNumber; }
}



