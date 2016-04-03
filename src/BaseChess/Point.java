package BaseChess;

import java.awt.Color;

/**
 * 
 * @author Hao
 * 
 *         class for the basechess
 */

public class Point {

	private int x;// 棋盘中的x索引
	private int y;// 棋盘中的y索引
	private Color color;// 颜色
	public static final int DIAMETER = 30;// 直径

	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	// 得到棋盘中X的坐标
	public int getX() {
		return x;
	}

	// 得到棋盘中的Y的坐标
	public int getY() {
		return y;
	}

	// 获得棋子的颜色
	public Color getColor() {
		return color;
	}
}
