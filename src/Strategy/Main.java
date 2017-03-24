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
		setTitle("单机版五子棋");

		// 1. 创建棋盘布局

		chessBoard = new ChessBoard();
		Container contentPane = getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true);

		// 2. 创建和添加菜单

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		sysMenu = new JMenu("系统");

		startMenuItem = new JMenuItem("重新开始");
		exitMenuItem = new JMenuItem("退出");
		backMenuItem = new JMenuItem("悔棋");

		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);

		MyItemListener lis = new MyItemListener();

		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);

		menuBar.add(sysMenu);// 将系统菜单添加到菜单栏上

		setJMenuBar(menuBar);// 将menuBar设置为菜单栏

		// 3. 创建底部按钮布局

		toolbar = new JPanel();
		toolbar.setBackground(Color.WHITE);

		ppButton = new JButton("人人对弈");
		pcButton = new JButton("人机对弈");
		ccButton = new JButton("电脑对弈");

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

		// 自适应窗口大小
		pack();

	}

	public ChessBoard getChessBoard() {
		return this.chessBoard;
	}

	// 监听事件
	private class MyItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			if (obj == startMenuItem) {
				// JFiveFrame.this内部类引用外部类
				System.out.println("重新开始");
				chessBoard.restartGame();
			} else if (obj == exitMenuItem) {
				System.exit(0);
			} else if (obj == backMenuItem) {
				System.out.println("悔棋...");
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

		// 显示主框架

		Main m = new Main();
		m.setVisible(true);

	}

}
