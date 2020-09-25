package sml.algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 骑士周游算法，马踏棋盘
 */
public class HorseChessboard {

    private static int X; //棋盘的列数
    private static int Y; //棋盘的行数
    //创建一个数组，标记棋盘的各个位置是否被访问过
    private static boolean[] visited;
    //使用一个属性，标记是否棋盘的所有位置都被访问过
    private static boolean finished;

    public static void main(String[] args) {
        X = 6;
        Y = 6;
        int row = 1;
        int column = 1;
        int[][] chessboard = new int[X][Y];
        visited = new boolean[X*Y];
        traversalChessBoard(chessboard,row-1,column-1,1);

        for (int[] rows : chessboard){
            System.out.println(Arrays.toString(rows));
        }
    }

    /**
     * 其实周游算法
     * @param chessboard 棋盘
     * @param row 马儿当前的位置的的行，从0开始
     * @param column 马儿当前位置的列，从0开始
     * @param step 是第几步，初始1
     */
    public static void traversalChessBoard(int[][] chessboard,int row,int column,int step){
        chessboard[row][column] = step;
        //visited是一维数组，是否被访问过，一行一行的排开记录
        visited[row*X + column] = true;//标记该位置已访问
        //获取当前位置可以走的下一步集合
        ArrayList<Point> points = next(new Point(column,row));

        while (!points.isEmpty()){
            Point p = points.remove(0);//取出下一个可以走的位置
            //判断该点是否已经访问过
            if(!visited[p.y*X + p.x]) { //说明还没有被访问过
                traversalChessBoard(chessboard,p.y,p.x,step+1);
            }
        }
        //判断马儿是否完成任务，使用step和应该走的步数比较
        //如果没有达到数量，则表示没有完成任务，将整个棋盘置零
        if(step < X*Y && !finished){
            chessboard[row][column] = 0;
            visited[row*X + column] = false;
        }else {
            finished = true;
        }

    }

    /**
     * 根据当前的位置（Point对象），计算马儿还能走哪些位置
     * @param curPoint 当前的位置
     * @return
     */
    public static ArrayList<Point> next(Point curPoint){
        //创建一个ArrayList
        ArrayList<Point> points = new ArrayList<>();
        //创建一个Point
        Point p1 = new Point();
        //判断马儿能够走的位置，马走日
        if((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y - 1) >= 0){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y - 2) >= 0){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y - 2) >= 0){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y - 1) >= 0){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x + 2) < X && (p1.y = curPoint.y + 1) < Y){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x + 1) < X && (p1.y = curPoint.y + 2) < Y){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x - 1) >= 0 && (p1.y = curPoint.y + 2) < Y){
            points.add(new Point(p1));
        }
        if((p1.x = curPoint.x - 2) >= 0 && (p1.y = curPoint.y + 1) < Y){
            points.add(new Point(p1));
        }
        return points;
    }
}
