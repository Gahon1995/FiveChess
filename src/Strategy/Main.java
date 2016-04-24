package Strategy;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import BaseChess.ChessBoard;
import BaseChess.Point;

/**
 * 
 * @author Hao
 * 
 *         The Main Function of the Five Chess
 */

public class Main extends JFrame {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */

	private ChessBoard chessBoard;

	private JPanel toolbar;
	private JButton ppButton, pcButton, ccButton;

	private JMenuBar menuBar;
	private JMenu sysMenu;
	private JMenuItem startMenuItem, exitMenuItem, backMenuItem;

	// ���¿�ʼ���˳����ͻ���˵���
	public Main() {
		setTitle("������������");// ���ñ���
		chessBoard = new ChessBoard();

		Container contentPane = getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true);

		// ��������Ӳ˵�
		// ��ʼ���˵���
		menuBar = new JMenuBar();
		sysMenu = new JMenu("ϵͳ");
		// ��ʼ���˵�

		// ��ʼ���˵���
		startMenuItem = new JMenuItem("���¿�ʼ");
		exitMenuItem = new JMenuItem("�˳�");
		backMenuItem = new JMenuItem("����");

		// �������˵�����ӵ��˵���
		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);
		// ��ʼ����ť�¼��������ڲ���
		MyItemListener lis = new MyItemListener();
		// �������˵�ע�ᵽ�¼���������
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);

		menuBar.add(sysMenu);// ��ϵͳ�˵���ӵ��˵�����

		setJMenuBar(menuBar);// ��menuBar����Ϊ�˵���

		toolbar = new JPanel();// �������ʵ����
		// ������ť��ʼ��
		ppButton = new JButton("���˶���");
		pcButton = new JButton("�˻�����");
		ccButton = new JButton("��������");
		// ��������尴ť��FlowLayout����
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ��������ť��ӵ��������
		toolbar.add(ppButton);
		toolbar.add(pcButton);
		toolbar.add(ccButton);
		// ��������ťע������¼�
		ppButton.addActionListener(lis);
		pcButton.addActionListener(lis);
		ccButton.addActionListener(lis);
		// ��������岼�ֵ����桱�Ϸ���Ҳ�����·�
		add(toolbar, BorderLayout.SOUTH);
		add(chessBoard);// ����������ӵ�������
		// ���ý���ر��¼�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setSize(800,800);
		pack();// ����Ӧ��С

	}

	public ChessBoard getChessBoard() {
		return this.chessBoard;
	}

	private class MyItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();// ����¼�Դ
			if (obj == startMenuItem) {
				// ���¿�ʼ
				// JFiveFrame.this�ڲ��������ⲿ��
				System.out.println("���¿�ʼ");
				chessBoard.restartGame();
			} else if (obj == exitMenuItem)
				System.exit(0);
			else if (obj == backMenuItem) {
				System.out.println("����...");
				chessBoard.goback();
			} else if (obj == ppButton) {
				System.out.println("pp Button");
				chessBoard.SetMode(0);
			} else if (obj == pcButton) {
				chessBoard.SetMode(1);
				System.out.println("pc Button");
				Thread t = new Thread(chessBoard);
				t.start();
			} else if (obj == ccButton) {
				// ��������
				System.out.println("cc Button");
				chessBoard.SetMode(2);
				Thread t = new Thread(chessBoard);
				t.start();
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Point[] chesslist = null;
		Point NextStep;

		int stepx, stepy;
		ChessStrategy f = new ChessStrategy();// ���������
		Main m = new Main();
		m.setVisible(true);// ��ʾ�����

	}

}
