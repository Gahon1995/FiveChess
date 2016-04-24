package Strategy;

import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 * 
 * @author Hao
 * 
 *         The Main Function of the Five Chess
 */

public class Main extends JFrame {

	private ChessBoard chessBoard;

	private JPanel toolbar;
	private JButton ppButton, pcButton, ccButton;

	private JMenuBar menuBar;
	private JMenu sysMenu;
	private JMenuItem startMenuItem, exitMenuItem, backMenuItem;

	public Main() {
		setTitle("������������");

		// 1. �������̲���

		chessBoard = new ChessBoard();
		Container contentPane = getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true);

		// 2. ��������Ӳ˵�

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		sysMenu = new JMenu("ϵͳ");

		startMenuItem = new JMenuItem("���¿�ʼ");
		exitMenuItem = new JMenuItem("�˳�");
		backMenuItem = new JMenuItem("����");

		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);

		MyItemListener lis = new MyItemListener();

		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);

		menuBar.add(sysMenu);// ��ϵͳ�˵���ӵ��˵�����

		setJMenuBar(menuBar);// ��menuBar����Ϊ�˵���

		// 3. �����ײ���ť����

		toolbar = new JPanel();
		toolbar.setBackground(Color.WHITE);

		ppButton = new JButton("���˶���");
		pcButton = new JButton("�˻�����");
		ccButton = new JButton("��������");

		toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));

		toolbar.add(ppButton);
		toolbar.add(pcButton);
		toolbar.add(ccButton);

		ppButton.addActionListener(lis);
		pcButton.addActionListener(lis);
		ccButton.addActionListener(lis);

		add(toolbar, BorderLayout.SOUTH);
		add(chessBoard);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ����Ӧ���ڴ�С
		pack();

	}

	public ChessBoard getChessBoard() {
		return this.chessBoard;
	}

	// �����¼�
	private class MyItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == startMenuItem) {
				// JFiveFrame.this�ڲ��������ⲿ��
				System.out.println("���¿�ʼ");
				chessBoard.restartGame();
			} else if (obj == exitMenuItem) {
				System.exit(0);
			} else if (obj == backMenuItem) {
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
				System.out.println("cc Button");
				chessBoard.SetMode(2);
				Thread t = new Thread(chessBoard);
				t.start();
			}
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		// ��ʾ�����

		Main m = new Main();
		m.setVisible(true);

	}

}
