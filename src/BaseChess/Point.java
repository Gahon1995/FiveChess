package BaseChess;

import java.awt.Color;

/**
 * 
 * @author Hao
 * 
 *         class for the chess point
 */

public class Point {

	// X: X坐标
	// Y: Y坐标
	// color: 棋子颜色
	// DIAMETER: 棋子直径
	private int x;
	private int y;
	private Color color;
	public static final int DIAMETER = 30;

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
