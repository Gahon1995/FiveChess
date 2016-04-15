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
		ChessStrategy f = new ChessStrategy();// ���������
		f.setVisible(true);// ��ʾ�����

		// ****************************** ���˶���

		// ****************************** �˻�����

		// ****************************** ��������
		chesslist = f.getChessBoard().getChesslist();

		boolean flag = true;
		while (true) {
			/**
			 * ���Ⱥ���������
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
			 * ������
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
