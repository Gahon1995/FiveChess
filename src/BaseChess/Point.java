package BaseChess;

import java.awt.Color;

/**
 * 
 * @author Hao
 * 
 *         class for the basechess
 */

public class Point {

	private int x;// �����е�x����
	private int y;// �����е�y����
	private Color color;// ��ɫ
	public static final int DIAMETER = 30;// ֱ��

	public Point(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	// �õ�������X������
	public int getX() {
		return x;
	}

	// �õ������е�Y������
	public int getY() {
		return y;
	}

	// ������ӵ���ɫ
	public Color getColor() {
		return color;
	}
}
