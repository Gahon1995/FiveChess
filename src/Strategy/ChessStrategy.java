package Strategy;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import BaseChess.ChessBoard;
import BaseChess.Point;

public class ChessStrategy extends JFrame {

	/**
	 * @param args
	 */
	private ChessBoard chessBoard;

	private JPanel toolbar;
	private JButton startButton, backButton, exitButton;

	private JMenuBar menuBar;
	private JMenu sysMenu;
	private JMenuItem startMenuItem, exitMenuItem, backMenuItem;

	// ���¿�ʼ���˳����ͻ���˵���
	public ChessStrategy() {
		setTitle("������������");// ���ñ���
		chessBoard = new ChessBoard();

		Container contentPane = getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true);

		// ��������Ӳ˵�
		menuBar = new JMenuBar();// ��ʼ���˵���
		sysMenu = new JMenu("ϵͳ");// ��ʼ���˵�
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
		this.startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);
		menuBar.add(sysMenu);// ��ϵͳ�˵���ӵ��˵�����
		setJMenuBar(menuBar);// ��menuBar����Ϊ�˵���

		toolbar = new JPanel();// �������ʵ����
		// ������ť��ʼ��
		startButton = new JButton("���¿�ʼ");
		exitButton = new JButton("�˳�");
		backButton = new JButton("����");
		// ��������尴ť��FlowLayout����
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// ��������ť��ӵ��������
		toolbar.add(startButton);
		toolbar.add(exitButton);
		toolbar.add(backButton);
		// ��������ťע������¼�
		startButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		backButton.addActionListener(lis);
		// ��������岼�ֵ����桱�Ϸ���Ҳ�����·�
		add(toolbar, BorderLayout.SOUTH);
		add(chessBoard);// ����������ӵ�������
		// ���ý���ر��¼�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setSize(800,800);
		pack();// ����Ӧ��С

	}

	private class MyItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();// ����¼�Դ
			if (obj == ChessStrategy.this.startMenuItem || obj == startButton) {
				// ���¿�ʼ
				// JFiveFrame.this�ڲ��������ⲿ��
				System.out.println("���¿�ʼ");
				chessBoard.restartGame();
			} else if (obj == exitMenuItem || obj == exitButton)
				System.exit(0);
			else if (obj == backMenuItem || obj == backButton) {
				System.out.println("����...");
				chessBoard.goback();
			}
		}
	}

	public ChessBoard getChessBoard() {
		return chessBoard;
	}

	/**
	 * �����ӵĲ���
	 * 
	 * @param chessList
	 * @return
	 */
	public Point WhiteNextStep(Point[] chessList) {
		/**
		 * ���Ӳ���
		 */
		return null;// �޸ķ�����һ�������
	}

	/**
	 * �����ӵĲ���
	 * 
	 * @param chesslistB
	 * @return
	 */
	public Point BlackNextStep(Point[] chesslistB) {
		// TODO Auto-generated method stub
		/**
		 * ���Ӳ���
		 */
		return null;// �޸ķ�����һ�������
	}

}
