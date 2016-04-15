package BaseChess;

import java.awt.Color;

/**
 * 
 * @author Hao
 * 
 *         class for the chess point
 */

public class Point {

	private int x;// ���ӵ�x����
	private int y;// ���ӵ�y����
	private Color color;// ������ɫ
	public static final int DIAMETER = 30;// ��ͼ����ֱ��

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
