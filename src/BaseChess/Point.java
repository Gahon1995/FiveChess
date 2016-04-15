package BaseChess;

import java.awt.Color;

/**
 * 
 * @author Hao
 * 
 *         class for the chess point
 */

public class Point {

	private int x;// 棋子的x坐标
	private int y;// 棋子的y坐标
	private Color color;// 棋子颜色
	public static final int DIAMETER = 30;// 画图棋子直径

	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Color getColor() {
		return this.color;
	}
}
