# 五子棋 

Author: WangHao

Email: [wanghaovip2020@gmail.com](wanghaovip2020@gmail.com)  

**如果遇到Bug问题，欢迎Email我！**

配置环境： JDk1.7

棋子默认是**黑子**先行

----------

**主要功能：**

- 人人对弈
- 人机对弈
- 电脑对弈 

**使用说明：**

- 系统栏中，有**重新开始**、**悔棋**和**退出**等功能
- 中间的面板展示棋局，默认黑子先行，最后下的棋子用红色矩阵框住显示
- 底部按钮有**人人对弈**、**人机对弈**和**电脑对弈**三种模式，每次点击按钮选择对应模式，并清除棋盘重新开始


----------

## 程序说明


程序中主要分为两个包，代码结构如下所示

- **BaseChess**
- 　　ChessBoard.java
- 　　Point.java
- **Strategy**
- 　　ChessStrategy.java
- 　　Main.java

#### BaseChess包
BaseChess包主要包含是棋盘ChessBoard和棋子Point的两个基本类

#### Strategy包
Strategy包含是棋子的策略Strategy和程序主函数Main两个类

下面对每个类别中函数进行简单的说明解释。

#### 主函数　Main

1. `public Main()`构造函数：初始化程序布局，添加对应的空间和布局

2. `private class MyItemListener implements ActionListener{}`：实现控件事件监听


#### 棋子类　Point

包含棋子的基本信息

1. `private int x`：棋子的X坐标


2. `private int y`：棋子的Y坐标


3. `private Color color`：棋子的颜色


4. `public static final int DIAMETER = 30`：绘制棋子的直径大小

#### 棋盘类　ChessBoard
棋盘类，继承JPanel类，实现基本的棋盘绘画功能；实现Runnable接口和MouseListener监听接口。主要的功能有：

1. 棋盘和棋子的绘制


2. 界面监听机制实现


3. 增加棋子


4. 搜索棋子和得到棋子对应的坐标


5. 判断棋局胜利


6. 使用多线程实现人人对弈，人机对弈和电脑对弈三种模式

主要函数解释如下

1. `public ChessBoard()`构造函数：初始化棋局、设置背景和添加监听机制

2. `public void paintChess(Graphics g)`：绘制棋子

3. `public void paintComponent(Graphics g)`：棋盘界面绘制

4. `public Point[] addChess(int x,int y)`：根据棋子坐标添加新的棋子，返回添加后的棋子数组，并判断棋局胜利

5. `public Dimension getPreferredSize()`：绘制矩形框


6. `public void mousePressed(MouseEvent e)`：鼠标在棋盘界面监听机制实现。在人人对弈模式零下正常监听，在人机对弈模式一下，只在人在下棋步骤时监听棋盘。在电脑对弈模式二下，对棋盘监听到的事件不再理会。


7. `private boolean isWin()`：判断棋局的输赢


8. `public void restartGame()`重新开始比赛


9. `public void goback()`：悔棋


10. `public void run()`： 使用多线程，分别实现棋局的三种模式

#### 棋子策略类　ChessStrategy
实现棋局的下子策略

1. `public Point WhiteNextStep(Point[] chessList)`：根据当前棋局，确定白子的下一个最佳位置

2. `public Point BlackNextStep(Point[] chesslistB)`：根据当前棋局，确定黑子的下一个最佳位置

3. `public Point BlackNextStep(Point[] chesslistB)`：根据当前棋局，找到使棋子最大的连子的位置

4. `public boolean findNeedPoint(int xIndex, int yIndex, Color c, Point[] chesslist)`：能够找到对应的坐标的棋子

5. `public ArrayList<Point> PointEvalution(ArrayList<Point> plist, Point[] chesslist, int longnum) `：对候选的棋子根据评价函数返回最佳评价的棋子

6. `public ArrayList<ArrayList<Point>> NextCandidatedPoints(Point[] chesslist)`：根据当前棋盘，分别得到黑白子下一步的候选棋子

7. `public boolean IsHasChess(int xIndex, int yIndex, Point[] chesslist)`：当前位置是否有棋子

8. `public int numsofthelongchess(Point p, Point[] chesslist, int longnum)`：给定的棋子位置最长连子数的个数

9. `public int lineSearch(Point p, Point[] chesslist)`：根据当前棋子位置，直线搜索连子的数目

10. `public int rowSearch(Point p, Point[] chesslist)`：根据当前棋子位置，垂直搜索连子的数目

11. `public int rightupSearch(Point p, Point[] chesslist)`：根据当前棋子位置，右上搜索连子的数目

12. `public int leftupSearch(Point p, Point[] chesslist)`：根据当前棋子位置，左上搜索连子的数目

13. `public void computerVscomputer(ChessBoard chessboard)`：电脑自动对弈函数实现

## 程序展示
###示例图片1
![棋盘布局](http://b.hiphotos.baidu.com/image/pic/item/cc11728b4710b91219613360c4fdfc0393452288.jpg)
###示例图片2 
![下棋中](http://c.hiphotos.baidu.com/image/pic/item/0e2442a7d933c89523c35ce5d61373f0830200eb.jpg)
###示例图片3
![棋局结束](http://a.hiphotos.baidu.com/image/pic/item/4a36acaf2edda3ccfccd7cbe06e93901213f9207.jpg)

