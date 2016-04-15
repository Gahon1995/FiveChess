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

/**
 * 
 * @author Hao
 *
 *         class for t he chess board of 18 * 18
 */

public class ChessBoard extends JPanel implements MouseListener {

	public static final int MARGIN = 25;// �߾�
	public static final int GRID_SPAN = 35;// ������
	public static final int ROWS = 18;// ��������
	public static final int COLS = 18;// ��������

	Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];// ��ʼÿ������Ԫ��Ϊnull
	boolean isBlack = true;// Ĭ�Ͽ�ʼ�Ǻ�����
	boolean gameOver = false;// ��Ϸ�Ƿ����
	int chessCount;// ��ǰ�������ӵĸ���
	int xIndex, yIndex;// ��ǰ�������ӵ�����

	Image img;
	Image shadows;
	Color colortemp;

	// ************************** ������
	public ChessBoard() {

		// setBackground(Color.GRAY);//���ñ���ɫΪ��ɫ
		img = Toolkit.getDefaultToolkit().getImage("board.jpg");
		shadows = Toolkit.getDefaultToolkit().getImage("shadows.jpg");
		addMouseListener(this);
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				// �������������λ��ת����������
				int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
				// ��Ϸ�Ѿ�����������
				// ���������ⲻ����
				// x��yλ���Ѿ������Ӵ��ڣ�������
				if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver || findChess(x1, y1))
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				// ���ó�Ĭ��״̬
				else
					setCursor(new Cursor(Cursor.HAND_CURSOR));

			}
		});
	}

	// ************************ �������ϻ�����
	public void paintChess(Graphics g) {
		for (int i = 0; i < chessCount; i++) {
			// ���񽻲��x��y����
			int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
			int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
			g.setColor(chessList[i].getColor());// ������ɫ
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
			// ������һ�����ӵĺ���ο�

			if (i == chessCount - 1) {// ��������һ������
				g.setColor(Color.red);
				g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, 34, 35);
			}
		}
	}

	// ����
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);// ������

		int imgWidth = img.getWidth(this);
		int imgHeight = img.getHeight(this);// ���ͼƬ�Ŀ����߶�
		int FWidth = getWidth();
		int FHeight = getHeight();// ��ô��ڵĿ����߶�
		int x = (FWidth - imgWidth) / 2;
		int y = (FHeight - imgHeight) / 2;
		g.drawImage(img, x, y, null);

		for (int i = 0; i <= ROWS; i++) {// ������
			g.drawString(Integer.toString(i), 8, MARGIN + i * GRID_SPAN);
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i <= COLS; i++) {// ������

			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);
			g.drawString(Integer.toString(i), MARGIN + i * GRID_SPAN - 3, MARGIN - 8);
		}

		paintChess(g);
	}

	// ***************** ��������
	public Point[] addChess(int x, int y) {
		// ��Ϸ����ʱ����������
		if (gameOver) {
			String msg = String.format("��Ϸ������ʤ���Ѷ���");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		}

		String colorName = isBlack ? "����" : "����";
		// ���������ⲻ����
		if (x < 0 || x > ROWS || y < 0 || y > COLS)
			return null;

		// ���x��yλ���Ѿ������Ӵ��ڣ�������
		if (findChess(x, y)) {
			String msg = String.format("��λ���Ѿ����ӣ�");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		}

		// ���Խ���ʱ�Ĵ���
		Point ch = new Point(x, y, isBlack ? Color.black : Color.white);

		chessList[chessCount++] = ch;
		xIndex = x;
		yIndex = y;
		repaint();// ֪ͨϵͳ���»���

		// ���ʤ���������ʾ��Ϣ�����ܼ�������

		if (isWin()) {
			System.out.println("Win Win Win Win Win Win");
			String msg = String.format("��ϲ��%sӮ�ˣ�", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		} else {
			isBlack = !isBlack; // �ڰ��ӷ�ת
			return chessList;
		}
		return null;

	}

	// ********************** ���Ӻ�ɫ����
	public Point[] BlackAddChess(int x, int y) {
		if (!isBlack) {
			String msg = String.format("�����Ѿ����ӣ��ֵ�����");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		} else
			return addChess(x, y);

	}

	// ********************** ���Ӱ�ɫ����
	public Point[] WhiteAddChess(int x, int y) {
		if (isBlack) {
			String msg = String.format("�����Ѿ����ӣ��ֵ�����");
			JOptionPane.showMessageDialog(this, msg);
			return null;
		} else
			return addChess(x, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {// ���������ϰ���ʱ����

		// ��Ϸ����ʱ����������
		if (gameOver)
			return;

		String colorName = isBlack ? "����" : "����";

		// �������������λ��ת������������
		xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
		yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

		// ���������ⲻ����
		if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
			return;

		// ���x��yλ���Ѿ������Ӵ��ڣ�������
		if (findChess(xIndex, yIndex))
			return;

		// ���Խ���ʱ�Ĵ���
		Point ch = new Point(xIndex, yIndex, isBlack ? Color.black : Color.white);
		chessList[chessCount++] = ch;
		repaint();// ֪ͨϵͳ���»���

		// ���ʤ���������ʾ��Ϣ�����ܼ�������

		if (isWin()) {
			String msg = String.format("��ϲ��%sӮ�ˣ�", colorName);
			JOptionPane.showMessageDialog(this, msg);
			gameOver = true;
		}
		isBlack = !isBlack;
	}

	// ����mouseListener�ķ���
	@Override
	public void mouseClicked(MouseEvent e) {
		// ��갴��������ϵ���ʱ����
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// �����뵽�����ʱ����
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// ����뿪���ʱ����
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// ��갴ť��������ͷ�ʱ����
	}

	// �����������в����Ƿ�������Ϊx��y�����Ӵ���
	private boolean findChess(int x, int y) {
		for (Point c : chessList) {
			if (c != null && c.getX() == x && c.getY() == y)
				return true;
		}
		return false;
	}

	// ******************** ���ݸ������ӵ�λ�ã��ж����̵���Ӯ��
	private boolean isWin() {
		int continueCount = 1;// �������ӵĸ���

		// --------------����Ѱ��-----> ��������Ѱ��
		for (int x = xIndex - 1; x >= 0; x--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// -------------����Ѱ��------> ������Ѱ��
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

		// *****************����Ѱ�� ******************
		// ��������
		for (int y = yIndex - 1; y >= 0; y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ��������Ѱ��
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

		// ******************* ����б������ **************************
		// ������һ�������������б��
		// ����Ѱ��
		for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// ����Ѱ��
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

		// **************** ����б������ ****************************
		// ������һ�������������б��
		// ����Ѱ��
		for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
			Color c = isBlack ? Color.black : Color.white;
			if (getChess(x, y, c) != null)
				continueCount++;
			else
				break;
		}
		// ����Ѱ��
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

	// ********************* ��������õ�����
	private Point getChess(int xIndex, int yIndex, Color color) {
		for (Point p : chessList) {
			if (p != null && p.getX() == xIndex && p.getY() == yIndex && p.getColor() == color)
				return p;
		}
		return null;
	}

	// ********************* ���±���
	public void restartGame() {
		// �������
		for (int i = 0; i < chessList.length; i++) {
			chessList[i] = null;
		}
		// �ָ���Ϸ��صı���ֵ
		isBlack = true;
		gameOver = false; // ��Ϸ�Ƿ����
		chessCount = 0; // ��ǰ�������Ӹ���
		repaint();
	}

	// ****************** ����
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

	// ********************* ������Ȧ����Dimension
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2 + GRID_SPAN * ROWS);
	}

	public Point[] getChesslist() {
		return this.chessList;
	}

}
