import javax.swing.*;
import java.awt.*;

public class PlayerBoard {
    private JButton PButton=new JButton(new ImageIcon());
    private JLabel DataBoard=new JLabel();
    private int playerIndex;
    private int playerPic;
    private int score=0;
    private int wrongTime=0;
    private String data;
    private boolean ai=false;

    public JButton getButton(int i){
        playerIndex=i;
        playerPic=i-1;
        getPicture();
        PButton.setBounds(10,playerIndex*(210+20)+40,180,180);
        return PButton;
    }
    public JLabel getLabel(){
        DataBoard.setBounds(10,playerIndex*(210+20)+40+180,180,30);
        Font fn = new Font("宋体",Font.BOLD,12);
        DataBoard.setFont(fn);
        return DataBoard;
    }
    public void StrData(){
        data="玩家"+ (playerIndex + 1) +" "+"得分:"+ score +" "+"失误数:"+ wrongTime;
        DataBoard.setText(data);
    }
    public int getPlayerScore(){return score;}
    public int getPlayerWrongTime(){return wrongTime;}
    public void SetPlayerScore(int Score){ score=Score; }
    public void SetPlayerWrongTime(int WrongTime){ wrongTime=WrongTime; }

    public JButton getPicture(){
        playerPic=(playerPic+1)%4;
        if (playerPic==0) PButton.setIcon(new ImageIcon("Art/0Miku01.png"));
        else if (playerPic==1) PButton.setIcon(new ImageIcon("Art/0Miku02.png"));
        else if (playerPic==2) PButton.setIcon(new ImageIcon("Art/0Miku03.png"));
        else if (playerPic==3) PButton.setIcon(new ImageIcon("Art/0Miku04.png"));
        return PButton;
    }

    public void PeoplePlay(){
        if (ai)playerPic=playerPic-1;
        ai=false;
        getPicture();
    }
    public void AiPlay(){
        ai=true;
        PButton.setIcon(new ImageIcon("Art/hackx.jpg"));
    }

    public boolean isAi() {
        return ai;
    }
}
