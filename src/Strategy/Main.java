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

	// 重新开始，退出，和悔棋菜单项
	public Main() {
		setTitle("单机版五子棋");// 设置标题
		chessBoard = new ChessBoard();

		Container contentPane = getContentPane();
		contentPane.add(chessBoard);
		chessBoard.setOpaque(true);

		// 创建和添加菜单
		// 初始化菜单栏
		menuBar = new JMenuBar();
		sysMenu = new JMenu("系统");
		// 初始化菜单

		// 初始化菜单项
		startMenuItem = new JMenuItem("重新开始");
		exitMenuItem = new JMenuItem("退出");
		backMenuItem = new JMenuItem("悔棋");

		// 将三个菜单项添加到菜单上
		sysMenu.add(startMenuItem);
		sysMenu.add(exitMenuItem);
		sysMenu.add(backMenuItem);
		// 初始化按钮事件监听器内部类
		MyItemListener lis = new MyItemListener();
		// 将三个菜单注册到事件监听器上
		startMenuItem.addActionListener(lis);
		backMenuItem.addActionListener(lis);
		exitMenuItem.addActionListener(lis);

		menuBar.add(sysMenu);// 将系统菜单添加到菜单栏上

		setJMenuBar(menuBar);// 将menuBar设置为菜单栏

		toolbar = new JPanel();// 工具面板实例化
		// 三个按钮初始化
		ppButton = new JButton("人人对弈");
		pcButton = new JButton("人机对弈");
		ccButton = new JButton("机机对弈");
		// 将工具面板按钮用FlowLayout布局
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 将三个按钮添加到工具面板
		toolbar.add(ppButton);
		toolbar.add(pcButton);
		toolbar.add(ccButton);
		// 将三个按钮注册监听事件
		ppButton.addActionListener(lis);
		pcButton.addActionListener(lis);
		ccButton.addActionListener(lis);
		// 将工具面板布局到界面”南方“也就是下方
		add(toolbar, BorderLayout.SOUTH);
		add(chessBoard);// 将面板对象添加到窗体上
		// 设置界面关闭事件
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setSize(800,800);
		pack();// 自适应大小

	}

	public ChessBoard getChessBoard() {
		return this.chessBoard;
	}

	private class MyItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();// 获得事件源
			if (obj == startMenuItem) {
				// 重新开始
				// JFiveFrame.this内部类引用外部类
				System.out.println("重新开始");
				chessBoard.restartGame();
			} else if (obj == exitMenuItem)
				System.exit(0);
			else if (obj == backMenuItem) {
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
				// 机机对弈
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
		ChessStrategy f = new ChessStrategy();// 创建主框架
		Main m = new Main();
		m.setVisible(true);// 显示主框架

	}

}
