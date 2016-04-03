package Strategy;

import java.io.IOException;

import BaseChess.Point;

public class Test {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Point[] chesslistB = null;
		Point[] chesslistW;
		Point NextStep;

		int stepx, stepy;
		ChessStrategy f = new ChessStrategy();// 创建主框架
		f.setVisible(true);// 显示主框架

		// System.in代表标准输入，就是键盘输入
		// Scanner sc = new Scanner(System.in);sc.hasNext()
		/**
		*  
		*/

		while (true) {
			/**
			 * 首先黑子先落子
			 */
			NextStep = f.BlackNextStep(chesslistB);
			stepx = NextStep.getX();
			stepy = NextStep.getY();

			chesslistB = f.getChessBoard().BlackAddChess(stepx, stepy);

			/**
			 * 白子落
			 */
			NextStep = f.WhiteNextStep(chesslistB);
			stepx = NextStep.getX();
			stepy = NextStep.getY();
			chesslistW = f.getChessBoard().WhiteAddChess(stepx, stepy);

		}

	}

}
