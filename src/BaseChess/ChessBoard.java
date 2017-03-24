package BaseChess;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Strategy.ChessStrategy;

/**
 * 
 * @author Hao
 *
 *         class for the chess board of 18 * 18
 */

public class ChessBoard extends JPanel implements MouseListener, Runnable {

	public static final int MARGIN = 25;// 边距
	public static final int GRID_SPAN = 35;// 网格间距
	public static final int ROWS = 18;// 棋盘行数
	public static final int COLS = 18;// 棋盘列数

	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];
	int chessCount;

	// 设定五子棋模式
	// 0:人人模式
	// 1:人机模式
	// 2:机机模式
	private int Mode = 0;

	boolean isBlack = true;// 默认开始是黑棋先
	boolean gameOver = false;
	int xIndex, yIndex;// 当前刚下棋子的索引

	Image img;
	// Image shadows;
	Color colortemp;

	public ChessBoard() {

		img = Toolkit.getDefaultToolkit().getImage("background.jpg");
		// shadows = Toolkit.getDefaultToolkit().getImage("shadows.jpg");
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				// 将鼠标点击的坐标位置转成网格索引
				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

				if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver || findChess(x1, y1))
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				// 设置成默认状态
				else
					setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
		});
	}

	// ************************ 在棋盘上画棋子
	public void paintChess(Graphics g) {
		for (int i = 0; i < chessCount; i++) {

			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor());// 设置颜色
			// g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
			// Point.DIAMETER, Point.DIAMETER);
			// g.drawImage(shadows, xPos-Point.DIAMETER/2,
			// yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER, null);
			colortemp = chessList[i].getColor();
			if (colortemp == Color.black) {

				RadialGradientPaint paint = new RadialGradientPaint(xPos - Point.DIAMETER / 2 + 25,
						yPos - Point.DIAMETER / 2 + 10, 20, new float[] { 0f, 1f },
						new Color[] { Color.WHITE, Color.BLACK });

				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

			} else if (colortemp == Color.white) {

				RadialGradientPaint paint = new RadialGradientPaint(xPos - Point.DIAMETER / 2 + 25,
						yPos - Point.DIAMETER / 2 + 10, 70, new float[] { 0f, 1f },
						new Color[] { Color.WHITE, Color.BLACK });

				((Graphics2D) g).setPaint(paint);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
						RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);
			}

			Ellipse2D e = new Ellipse2D.Float(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, 34, 35);
			((Graphics2D) g).fill(e);

			// 最后一个棋子画上红矩形框
			if (i == chessCount - 1) {// 如果是最后一个棋子
				g.setColor(Color.red);
				g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, 34, 35);
			}
		}
	}

	// 绘制
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		int imgWidth = img.getWidth(this);
		int imgHeight = img.getHeight(this);
		int FWidth = getWidth();
		int FHeight = getHeight();
		int x = (FWidth - imgWidth) / 2;
		int y = (FHeight - imgHeight) / 2;
		g.drawImage(img, 0, 0, FWidth, FHeight, this);

		// 画横线
		for (int i = 0; i <= ROWS; i++) {
			// g.drawString(Integer.toString(i), 8, MARGIN + i * GRID_SPAN);
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {// 画竖线
			// g.drawString(Integer.toString(i), MARGIN + i * GRID_SPAN -
			// 3,MARGIN - 8);
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
		}

		paintChess(g);
	}

	// ***************** 增加棋子
	public Point[] addChess(int x, int y) {

		if (gameOver) {
			String msg = String.format("游戏结束，胜负已定！");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		}

		String colorName = isBlack ? "黑棋" : "白棋";

		if (x < 0 || x > ROWS || y < 0 || y > COLS)
			return null;

		// 如果x，y位置已经有棋子存在，不能下
		if (findChess(x, y)) {
			String msg = String.format("该位置已经落子！");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		}

		Point ch = new Point(x, y, isBlack ? Color.black : Color.white);

		chessList[chessCount++] = ch;
		xIndex = x;
		yIndex = y;
		// 通知系统重新绘制
		repaint();

		if (isWin()) {
			System.out.println("Win Win Win Win Win Win");
			String msg = String.format("恭喜，%s赢了！", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		} else {
			isBlack = !isBlack; // 黑白子反转
			return chessList;
		}
		return null;

	}

	// ********************** 增加黑色棋子
	public Point[] BlackAddChess(int x, int y) {
		if (!isBlack) {
			String msg = String.format("黑子已经落子，轮到白子");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		} else
			return addChess(x, y);

	}

	// ********************** 增加白色棋子
	public Point[] WhiteAddChess(int x, int y) {
		if (isBlack) {
			String msg = String.format("白子已经落子，轮到黑子");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		} else
			return addChess(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {// 鼠标在组件上按下时调用

		if (GetMode() == 0) {
			// 游戏结束时，不再能下
			if (gameOver)
				return;

			String colorName = isBlack ? "黑棋" : "白棋";

			// 将鼠标点击的坐标位置转换成网格索引
			xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

			// 落在棋盘外不能下
			if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
				return;

			// 如果x，y位置已经有棋子存在，不能下
			if (findChess(xIndex, yIndex))
				return;

			// 可以进行时的处理
			Point ch = new Point(xIndex, yIndex, isBlack ? Color.black : Color.white);
			chessList[chessCount++] = ch;
			repaint();// 通知系统重新绘制

			// 如果胜出则给出提示信息，不能继续下棋

			if (isWin()) {
				String msg = String.format("恭喜，%s赢了！", colorName);
				JOptionPane.showMessageDialog(this, msg);
				gameOver = true;
			}
			isBlack = !isBlack;
		}

		// 人机模式,黑子表示人先行，白子表示电脑,电脑下时，不监听事件
		if (this.Mode == 1) {
			if (isBlack) {
				if (gameOver)
					return;

				// 将鼠标点击的坐标位置转换成网格索引
				xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

				// 落在棋盘外不能下
				if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
					return;

				// 如果x，y位置已经有棋子存在，不能下
				if (findChess(xIndex, yIndex))
					return;

				// 可以进行时的处理
				Point ch = new Point(xIndex, yIndex, Color.black);
				chessList[chessCount++] = ch;
				repaint();// 通知系统重新绘制

				// 如果胜出则给出提示信息，不能继续下棋

				if (isWin()) {
					String msg = String.format("恭喜，%s赢了！", "黑棋");
					JOptionPane.showMessageDialog(this, msg);
					gameOver = true;
				}
				isBlack = !isBlack;
			}

			return;
		}

		if (this.Mode == 2)
			return;
	}

	// 在棋子数组中查找是否有索引为x，y的棋子存在
	private boolean findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y)
				return true;
		}
		return false;
	}

	// ******************** 根据刚下棋子的位置，判断棋盘的输赢，
	private boolean isWin() {
		int continueCount = 1;// 连续棋子的个数

		// --------------横向寻找-----> 横向向西寻找
		for (int x = xIndex - 1; x >= 0; x--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// -------------横向寻找------> 横向向东寻找
		for (int x = xIndex + 1; x <= COLS; x++) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// *****************纵向寻找 ******************
		// 向上搜索
		for (int y = yIndex - 1; y >= 0; y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 纵向向下寻找
		for (int y = yIndex + 1; y <= ROWS; y++) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(xIndex, y, c) != null)
				continueCount++;
			else
				break;

		}
		if (continueCount >= 5)
			return true;
		else
			continueCount = 1;

		// ******************* 右上斜向搜索 **************************
		// 东北寻找
		for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 西南寻找
		for (int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= ROWS; x--, y++) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		if (continueCount >= 5)
			return true;
		else
			continueCount = 1;

		// **************** 左上斜上搜索 ****************************
		// 西北寻找
		for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null)
				continueCount++;
			else
				break;
		}
		// 东南寻找
		for (int x = xIndex + 1, y = yIndex + 1; x <= COLS && y <= ROWS; x++, y++) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null)
				continueCount++;
			else
				break;
		}
		if (continueCount >= 5)
			return true;
		else
			continueCount = 1;

		return false;
	}

	// ********************* 根据坐标得到棋子
	private Point getChess(int xIndex, int yIndex, Color color) {
		for (Point p : chessList) {
			if (p != null && p.getX() == xIndex && p.getY() == yIndex && p.getColor() == color)
				return p;
		}
		return null;
	}

	// ********************* 重新比赛
	public void restartGame() {
		// 清除棋子
		for (int i = 0; i < chessList.length; i++) {
			chessList[i] = null;
		}
		// 恢复游戏相关的变量值
		isBlack = true;
		gameOver = false; // 游戏是否结束
		chessCount = 0; // 当前棋盘棋子个数
		repaint();
	}

	// ****************** 悔棋
	public void goback() {
		if (chessCount == 0)
			return;
		chessList[chessCount - 1] = null;
		chessCount--;
		if (chessCount > 0) {
			xIndex = chessList[chessCount - 1].getX();
			yIndex = chessList[chessCount - 1].getY();
		}
		isBlack = !isBlack;
		repaint();
	}

	// ********************* 棋子外圈矩形Dimension
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	public Point[] getChesslist() {
		return this.chessList;
	}

	public int GetMode() {
		return this.Mode;
	}

	public void SetMode(int x) {
		this.Mode = x;
		restartGame();
	}

	// 覆盖mouseListener的方法
	@Override
	public void mouseClicked(MouseEvent e) {
		// 鼠标按键在组件上单击时调用
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 鼠标进入到组件上时调用
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 鼠标离开组件时调用
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 鼠标按钮在组件上释放时调用
	}

	// 当模式为人机和电脑之间对弈时，需要开启新的线程
	@Override
	public void run() {
		// 机机模式
		if (this.Mode == 2) {
			ChessStrategy chessStrategy = new ChessStrategy();
			chessStrategy.computerVscomputer(this);
		}
		// 人机模式
		if (this.Mode == 1) {
			ChessStrategy chessStrategy = new ChessStrategy();
			Point t;
			while (!this.gameOver && this.Mode == 1) {
				System.out.println("5");
				if (!this.isBlack) {
					t = chessStrategy.WhiteNextStep(this.getChesslist());
					WhiteAddChess(t.getX(), t.getY());
				}
			}
		}

	}
}
