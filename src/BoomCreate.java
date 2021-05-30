import java.util.Random;
public class BoomCreate {
    private int[][] outputList;
    private int[][] resultList;
    private int[][] boomIndex;
    private int[][] numberIndex;

public int[][] outputList(int x,int y,int boomNumber){
    int i=0;
    outputList = new int[x+2][y+2];
    resultList = new int[x][y];
    boomIndex = new int[boomNumber][2];
    numberIndex=new int[8*boomNumber][2];
    Random rdm = new Random();
    while (i<boomNumber){
        int xTmp=rdm.nextInt(x)+1;
        int yTmp=rdm.nextInt(y)+1;
        if (outputList[xTmp][yTmp]!=-1){
            outputList[xTmp][yTmp]=-1;
            boomIndex[i][0]=xTmp;
            boomIndex[i][1]=yTmp;
            i++;
        }
    }
    for (int j=0;j<boomNumber;j++){
        int k=8*j,xj,yj;
        xj=boomIndex[j][0];
        yj=boomIndex[j][1];
        if (xj-1>0 && yj-1>0) {
            numberIndex[k][0] = xj - 1;//-1,-1*
            numberIndex[k][1] = yj - 1;
        }
        if (xj-1>0){
            numberIndex[k+1][0]=xj-1;//-1,0*
            numberIndex[k+1][1]=yj;
        }
        if (xj-1>0 && yj+1<y+2){
            numberIndex[k+2][0]=xj-1;//-1,1*
            numberIndex[k+2][1]=yj+1;
        }
        if (yj-1>0){
            numberIndex[k+3][0]=xj;//0,-1*
            numberIndex[k+3][1]=yj-1;
        }
        if (yj+1<y+2) {
            numberIndex[k+4][0]=xj;//0,1*
            numberIndex[k+4][1]=yj+1;
        }
        if (xj+1<x+2 && yj-1>0){
            numberIndex[k+5][0]=xj+1;//1,-1*
            numberIndex[k+5][1]=yj-1;
        }
        if (xj+1<x+2){
            numberIndex[k+6][0]=xj+1;//1,0*
            numberIndex[k+6][1]=yj;
        }
        if (xj+1<x+2 && yj+1<y+2){
            numberIndex[k+7][0]=xj+1;//1,1*
            numberIndex[k+7][1]=yj+1;
        }
    }
    for (int j=0;j<8*boomNumber;j++){
        int xn,yn,boomNb=0;
        xn=numberIndex[j][0];
        yn=numberIndex[j][1];
        if (outputList[xn][yn]==0){
            if (xn - 1 > 0 & yn - 1 > 0) if (outputList[xn - 1][yn - 1] == -1) boomNb++;
            if (xn - 1 > 0) if (outputList[xn - 1][yn] == -1) boomNb++;
            if (xn - 1 > 0 & yn + 1 < y + 2) if (outputList[xn - 1][yn + 1] == -1) boomNb++;
            if (yn - 1 > 0) if (outputList[xn][yn - 1] == -1) boomNb++;
            if (yn + 1 < y + 2) if (outputList[xn][yn + 1] == -1) boomNb++;
            if (xn + 1 < x + 2 & yn - 1 > 0) if (outputList[xn + 1][yn - 1] == -1) boomNb++;
            if (xn + 1 < x + 2) if (outputList[xn + 1][yn] == -1) boomNb++;
            if (xn + 1 < x + 2 & yn + 1 < y + 2) if (outputList[xn + 1][yn + 1] == -1) boomNb++;
            outputList[xn][yn] = boomNb;
        }
    }
    for (int i0=0;i0<x;i0++){
        if (y >= 0) System.arraycopy(outputList[i0 + 1], 1, resultList[i0], 0, y);
    }
    return resultList;
}
    public boolean is9in(){
        for (int[] index : boomIndex) {
            int x = index[0], y = index[1];
            if (outputList[x][y - 1] == -1 && outputList[x][y + 1] == -1)
                if (outputList[x - 1][y - 1] == -1 && outputList[x - 1][y] == -1 && outputList[x - 1][y + 1] == -1)
                    if (outputList[x + 1][y - 1] == -1 && outputList[x + 1][y] == -1 && outputList[x + 1][y + 1] == -1)
                        return true;
        }
        return false;
    }
    public int[][] getResultList() { return resultList; }
    public int[][] getNumberIndex() { return numberIndex; }
}