package Strategy;

import java.io.IOException;

import BaseChess.Point;

public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Point[] chesslist = null;
		Point NextStep;

		int stepx, stepy;
		ChessStrategy f = new ChessStrategy();// 创建主框架
		f.setVisible(true);// 显示主框架

		// ****************************** 人人对弈

		// ****************************** 人机对弈

		// ****************************** 机机对弈
		chesslist = f.getChessBoard().getChesslist();

		boolean flag = true;
		while (true) {
			/**
			 * 首先黑子先落子
			 */
			if (flag) {
				chesslist = f.getChessBoard().BlackAddChess(f.getChessBoard().ROWS / 2, f.getChessBoard().COLS / 2);
				flag = false;
			} else {

				NextStep = f.BlackNextStep(chesslist);
				stepx = NextStep.getX();
				stepy = NextStep.getY();

				chesslist = f.getChessBoard().BlackAddChess(stepx, stepy);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("thread error");
				}
			}
			/**
			 * 白子落
			 */
			NextStep = f.WhiteNextStep(chesslist);
			stepx = NextStep.getX();
			stepy = NextStep.getY();
			chesslist = f.getChessBoard().WhiteAddChess(stepx, stepy);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("thread error");
			}

		}

	}

}
