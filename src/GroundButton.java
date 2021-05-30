import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GroundButton  {
    private int stateNumber;
    private int indexX=0;
    private int indexY=0;
    private int[] tmp=new int[2];
    private int xLength=40;
    private int yLength=40;
    private boolean change=false;
    private boolean find=false;
    private JButton GroundButton=new JButton(new ImageIcon("Art/.jpg"));
    public JButton getButton(){
        GroundButton.setBounds(indexX*xLength+200,indexY*yLength+40,xLength,yLength);
        return GroundButton;
    }
    public void setButton(int indexX,int indexY,int number) {
        this.indexX = indexX;
        this.indexY = indexY;
        stateNumber= number;
        tmp[0]=indexX;
        tmp[1]=indexY;
    }
    public int open(ArrayList<int[]> bmList,ArrayList<int[]> wyList,GroundButton[][] ButtonList,int xm,int ym){
        if (!change){
            checkNumber();
            change=true;
            Iterator<int[]> itb =bmList.iterator();
            Iterator<int[]> itw =wyList.iterator();
            if (stateNumber==-1)while (itb.hasNext()) {
                int[] item = itb.next();
                if (Arrays.equals(item, tmp)) itb.remove();
            }
            else while (itw.hasNext()){
                int[] item = itw.next();
                if (Arrays.equals(item, tmp)) itw.remove();
            }
            if (stateNumber==0)for (int i=Math.max(indexX-1,0);i<Math.min(indexX+2,xm);i++)
                for (int j=Math.max(indexY-1,0);j<Math.min(indexY+2,ym);j++){
                    if (ButtonList[i][j].getStateNumber()!=-1)ButtonList[i][j].open(bmList,wyList,ButtonList,xm,ym);
            }
            return stateNumber;
        }
        else return -999;
    }
    public int flag(ArrayList<int[]> bmList,ArrayList<int[]> wyList){
        if (!change){
            if (stateNumber==-1)GroundButton.setIcon(new ImageIcon("Art/flag (1).png"));
            else checkNumberBk();
            change=true;
            Iterator<int[]> itb =bmList.iterator();
            Iterator<int[]> itw =wyList.iterator();
            if (stateNumber==-1)while (itb.hasNext()) {
                int[] item = itb.next();
                if (Arrays.equals(item, tmp)) itb.remove();
            }
            else while (itw.hasNext()){
                int[] item = itw.next();
                if (Arrays.equals(item, tmp)) itw.remove();
            }
            return stateNumber;
        }
        else return -999;
    }
    public void checkNumber() {
        if (stateNumber == 1) GroundButton.setIcon(new ImageIcon("Art/01 (1).png"));
        else if (stateNumber == 2) GroundButton.setIcon(new ImageIcon("Art/02 (1).png"));
        else if (stateNumber == 3) GroundButton.setIcon(new ImageIcon("Art/03 (1).png"));
        else if (stateNumber == 4) GroundButton.setIcon(new ImageIcon("Art/04 (1).png"));
        else if (stateNumber == 5) GroundButton.setIcon(new ImageIcon("Art/05 (1).png"));
        else if (stateNumber == 6) GroundButton.setIcon(new ImageIcon("Art/06 (1).png"));
        else if (stateNumber == 7) GroundButton.setIcon(new ImageIcon("Art/07 (1).png"));
        else if (stateNumber == 0) GroundButton.setIcon(new ImageIcon("Art/00.png"));
        else if (stateNumber == -1)  GroundButton.setIcon(new ImageIcon("Art/Boom.jpg"));
    }
    public void checkNumberBk() {
        if (stateNumber == 1) GroundButton.setIcon(new ImageIcon("Art/012.png"));
        else if (stateNumber == 2) GroundButton.setIcon(new ImageIcon("Art/022.png"));
        else if (stateNumber == 3) GroundButton.setIcon(new ImageIcon("Art/032.png"));
        else if (stateNumber == 4) GroundButton.setIcon(new ImageIcon("Art/042.png"));
        else if (stateNumber == 5) GroundButton.setIcon(new ImageIcon("Art/052.png"));
        else if (stateNumber == 6) GroundButton.setIcon(new ImageIcon("Art/062.png"));
        else if (stateNumber == 7) GroundButton.setIcon(new ImageIcon("Art/072.png"));
        else if (stateNumber == 0) GroundButton.setIcon(new ImageIcon("Art/00x.png"));
    }
    public void cheat(){
        if (stateNumber==-1&&!change)GroundButton.setIcon(new ImageIcon("Art/GG.jpg"));
    }
    public void no_cheat(){
        if (stateNumber==-1&&!change)GroundButton.setIcon(new ImageIcon(""));
    }

    public int getStateNumber() { return stateNumber; }
    public boolean changeColor(int colorI){
        if (change||find)return false;
        else {
            if (colorI==1)GroundButton.setBackground(Color.red);
            else if (colorI==2)GroundButton.setBackground(Color.green);
            find=true;
            return true;
        }
    }

}
