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
		ChessStrategy f = new ChessStrategy();// ���������
		f.setVisible(true);// ��ʾ�����

		// System.in�����׼���룬���Ǽ�������
		// Scanner sc = new Scanner(System.in);sc.hasNext()
		/**
		*  
		*/

		while (true) {
			/**
			 * ���Ⱥ���������
			 */
			NextStep = f.BlackNextStep(chesslistB);
			stepx = NextStep.getX();
			stepy = NextStep.getY();

			chesslistB = f.getChessBoard().BlackAddChess(stepx, stepy);

			/**
			 * ������
			 */
			NextStep = f.WhiteNextStep(chesslistB);
			stepx = NextStep.getX();
			stepy = NextStep.getY();
			chesslistW = f.getChessBoard().WhiteAddChess(stepx, stepy);

		}

	}

}
