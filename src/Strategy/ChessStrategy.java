package Strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;

import BaseChess.ChessBoard;
import BaseChess.Point;

/**
 * 
 * @author Hao
 *
 */
public class ChessStrategy extends JFrame {

	/**
	 * @param args
	 */

	/**
	 * 白棋子的策略
	 * 
	 * @param chessList
	 * @return the next while point
	 */
	public Point WhiteNextStep(Point[] chessList) {
		/**
		 * 白子策略
		 */

		ArrayList<ArrayList<Point>> NextPoint = NextCandidatedPoints(chessList);
		ArrayList<Point> blackList = NextPoint.get(0);
		ArrayList<Point> whiteList = NextPoint.get(1);

		int blackLongnum = findTheLongestChessNums(blackList.get(0), chessList);
		int whiteLongnum = findTheLongestChessNums(whiteList.get(0), chessList);

		// System.out.println("Current white " + "blackLongnum :" + blackLongnum
		// + " whiteLongnum " + whiteLongnum
		// + " backlist nums " + blackList.size() + " whitelist nums " +
		// whiteList.size());

		if (whiteLongnum < blackLongnum) {
			// 白的的连子数比黑子要少，就从黑子的候选结点中选择评价函数最高的结点
			ArrayList<Point> candicatedPoint = PointEvalution(blackList, chessList, blackLongnum);
			if (candicatedPoint.size() == 1)
				return candicatedPoint.get(0);
			else {
				// 从候选结点中随机选取
				int size = candicatedPoint.size();
				Random rand = new Random();
				int Index = rand.nextInt(size);
				return candicatedPoint.get(Index);
			}
		} else {
			// 白子连子数比黑子多，则从自己的候选结点中选择
			ArrayList<Point> candicatedPoint = PointEvalution(whiteList, chessList, whiteLongnum);
			if (candicatedPoint.size() == 1)
				return candicatedPoint.get(0);
			else {
				// 从候选结点中随机选取
				int size = candicatedPoint.size();
				Random rand = new Random();
				int Index = rand.nextInt(size);
				return candicatedPoint.get(Index);
			}
		}
	}

	/**
	 * 黑棋子的策略
	 * 
	 * @param chesslistB
	 * @return the next black point
	 */
	public Point BlackNextStep(Point[] chesslistB) {

		/**
		 * 黑子策略
		 */

		ArrayList<ArrayList<Point>> NextPoint = NextCandidatedPoints(chesslistB);
		ArrayList<Point> blackList = NextPoint.get(0);
		ArrayList<Point> whiteList = NextPoint.get(1);

		int blackLongnum = findTheLongestChessNums(blackList.get(0), chesslistB);
		int whiteLongnum = findTheLongestChessNums(whiteList.get(0), chesslistB);

		// System.out.println("Current black " + "blackLongnum :" + blackLongnum
		// + " whiteLongnum " + whiteLongnum
		// + " backlist nums " + blackList.size() + " whitelist nums " +
		// whiteList.size());
		if (blackLongnum < whiteLongnum) {
			// 连子数比白子要少，就从白字的候选结点中选择评价函数最高的结点
			ArrayList<Point> candicatedPoint = PointEvalution(whiteList, chesslistB, whiteLongnum);
			if (candicatedPoint.size() == 1)
				return candicatedPoint.get(0);
			else {
				// 从候选结点中随机选取
				int size = candicatedPoint.size();
				Random rand = new Random();
				int Index = rand.nextInt(size);
				return candicatedPoint.get(Index);
			}
		} else {
			// 连子数比白子多，则从自己的候选结点中选择
			ArrayList<Point> candicatedPoint = PointEvalution(blackList, chesslistB, blackLongnum);
			if (candicatedPoint.size() == 1)
				return candicatedPoint.get(0);
			else {
				// 从候选结点中随机选取
				int size = candicatedPoint.size();
				Random rand = new Random();
				int Index = rand.nextInt(size);
				return candicatedPoint.get(Index);
			}
		}

	}

	// 找到棋子位置P最大的连续棋子数目
	public int findTheLongestChessNums(Point p, Point[] chesslist) {

		int longLength = 0;

		// 1. 横向寻找
		int longLineLength = lineSearch(p, chesslist);

		// 2. 纵向寻找
		int longColLength = rowSearch(p, chesslist);

		// 3. 右上角寻找
		int LongRightUpLength = rightupSearch(p, chesslist);

		// 4. 左上角寻找
		int LongLeftUpLength = leftupSearch(p, chesslist);

		// get the max
		if (longLineLength >= longColLength)
			longLength = longLineLength;
		else
			longLength = longColLength;

		if (LongRightUpLength >= longLength)
			longLength = LongRightUpLength;

		if (LongLeftUpLength >= longLength)
			longLength = LongLeftUpLength;

		return longLength;
	}

	// 能否找到需要的结点
	public boolean findNeedPoint(int xIndex, int yIndex, Color c, Point[] chesslist) {

		for (Point p : chesslist)
			if (p != null && xIndex == p.getX() && yIndex == p.getY() && c == p.getColor())
				return true;

		return false;
	}

	// **************对当前结点的评价函数 ,并返回评价较高的函数**********
	public ArrayList<Point> PointEvalution(ArrayList<Point> plist, Point[] chesslist, int longnum) {

		// 1. 简单的选择使自己连子次数最多的位置
		ArrayList<Point> resultPointList = new ArrayList<Point>();
		HashMap<Point, Integer> map = new HashMap<Point, Integer>();
		int maxtime = -1;
		for (Point p : plist) {
			int value = numsofthelongchess(p, chesslist, longnum);
			if (value > maxtime)
				maxtime = value;
			map.put(p, value);
		}

		for (Map.Entry<Point, Integer> m : map.entrySet()) {
			if (m.getValue() == maxtime)
				resultPointList.add(m.getKey());
		}
		return resultPointList;
	}

	// 得到黑白棋子的候选位置,即首先选出使自己连子数目最多位置的结点，同时考虑黑白结点
	public ArrayList<ArrayList<Point>> NextCandidatedPoints(Point[] chesslist) {

		HashMap<Point, Integer> blackpointLongnum = new HashMap<Point, Integer>();
		HashMap<Point, Integer> whitepointLongnum = new HashMap<Point, Integer>();

		for (int x = 0; x <= ChessBoard.ROWS; x++) {
			for (int y = 0; y <= ChessBoard.COLS; y++) {
				if (!IsHasChess(x, y, chesslist)) {
					Point blackp = new Point(x, y, Color.black);
					Point whitep = new Point(x, y, Color.white);

					int blackLongnum = findTheLongestChessNums(blackp, chesslist);
					int whiteLongnum = findTheLongestChessNums(whitep, chesslist);

					blackpointLongnum.put(blackp, blackLongnum);
					whitepointLongnum.put(whitep, whiteLongnum);
				}
			}
		}

		// Sort for all white and black chess
		List<Map.Entry<Point, Integer>> blackPointlist = new ArrayList<Map.Entry<Point, Integer>>(
				blackpointLongnum.entrySet());
		List<Map.Entry<Point, Integer>> whitePointlist = new ArrayList<Map.Entry<Point, Integer>>(
				whitepointLongnum.entrySet());

		Collections.sort(blackPointlist, new Comparator<Map.Entry<Point, Integer>>() {

			@Override
			public int compare(Entry<Point, Integer> o1, Entry<Point, Integer> o2) {

				if (o1.getValue() < o2.getValue())
					return 1;

				if (o1.getValue() > o2.getValue())
					return -1;

				return 0;
			}
		});

		Collections.sort(whitePointlist, new Comparator<Map.Entry<Point, Integer>>() {

			@Override
			public int compare(Entry<Point, Integer> o1, Entry<Point, Integer> o2) {

				if (o1.getValue() < o2.getValue())
					return 1;

				if (o1.getValue() > o2.getValue())
					return -1;

				return 0;
			}
		});

		// get the maxNum for black and white
		ArrayList<ArrayList<Point>> resultList = new ArrayList<ArrayList<Point>>();
		int blackLongnum = -1;
		int whiteLongnum = -1;

		if (blackPointlist.size() != 0)
			blackLongnum = blackPointlist.get(0).getValue();

		if (whitePointlist.size() != 0)
			whiteLongnum = whitePointlist.get(0).getValue();

		System.out.println(
				"Search the white and black num is " + " blackNum " + blackLongnum + " whitenum " + whiteLongnum);
		System.out.println("Print the result ");

		// for (Map.Entry<Point, Integer> map : blackPointlist) {
		// System.out.println("map " + map.getKey().getX() + " " +
		// map.getKey().getY() + " length " + map.getValue());
		// }

		ArrayList<Point> blackList = new ArrayList<Point>();
		if (blackLongnum != -1) {
			for (Map.Entry<Point, Integer> map : blackPointlist) {
				if (map.getValue() == blackLongnum)
					blackList.add(map.getKey());
				else
					break;
			}
		}
		resultList.add(blackList);

		ArrayList<Point> whiteList = new ArrayList<Point>();
		if (whiteLongnum != -1) {
			for (Map.Entry<Point, Integer> map : whitePointlist) {
				if (map.getValue() == whiteLongnum)
					whiteList.add(map.getKey());
				else
					break;
			}
		}
		resultList.add(whiteList);

		return resultList;
	}

	// 当前位置是否有节点

	public boolean IsHasChess(int xIndex, int yIndex, Point[] chesslist) {

		for (Point p : chesslist) {
			if (p != null && xIndex == p.getX() && yIndex == p.getY())
				return true;
		}
		return false;
	}

	// 给定一个位置，给定最长连子数，判断有几个这样的连子数。
	public int numsofthelongchess(Point p, Point[] chesslist, int longnum) {

		int nums = 0;

		if (lineSearch(p, chesslist) == longnum)
			nums++;

		if (rowSearch(p, chesslist) == longnum)
			nums++;

		if (rightupSearch(p, chesslist) == longnum)
			nums++;

		if (leftupSearch(p, chesslist) == longnum)
			nums++;

		return nums;
	}

	// --------- 四个方向搜索函数
	// 1. 直线搜索
	public int lineSearch(Point p, Point[] chesslist) {

		int longLineLength = 1;
		for (int x = p.getX() - 1; x >= 0; x--) {
			if (findNeedPoint(x, p.getY(), p.getColor(), chesslist))
				longLineLength++;
			else
				break;
		}
		for (int x = p.getX() + 1; x <= ChessBoard.COLS; x++) {
			if (findNeedPoint(x, p.getY(), p.getColor(), chesslist))
				longLineLength++;
			else
				break;
		}

		return longLineLength;
	}

	// 2. 垂直搜索

	public int rowSearch(Point p, Point[] chesslist) {

		int longColLength = 1;
		for (int y = p.getY() - 1; y >= 0; y--) {
			if (findNeedPoint(p.getX(), y, p.getColor(), chesslist))
				longColLength++;
			else
				break;
		}
		for (int y = p.getY() + 1; y <= ChessBoard.ROWS; y++) {
			if (findNeedPoint(p.getX(), y, p.getColor(), chesslist))
				longColLength++;
			else
				break;
		}
		return longColLength;
	}

	// 3. 右上搜索

	public int rightupSearch(Point p, Point[] chesslist) {

		int LongRightUpLength = 1;
		for (int x = p.getX() + 1, y = p.getY() - 1; x <= ChessBoard.COLS && y >= 0; x++, y--) {
			if (findNeedPoint(x, y, p.getColor(), chesslist))
				LongRightUpLength++;
			else
				break;
		}
		for (int x = p.getX() - 1, y = p.getY() + 1; x >= 0 && y <= ChessBoard.ROWS; x--, y++) {
			if (findNeedPoint(x, y, p.getColor(), chesslist))
				LongRightUpLength++;
			else
				break;
		}
		return LongRightUpLength;
	}

	// 4. 左上搜索
	public int leftupSearch(Point p, Point[] chesslist) {

		int LongLeftUpLength = 1;
		for (int x = p.getX() - 1, y = p.getY() - 1; x >= 0 && y >= 0; x--, y--) {
			if (findNeedPoint(x, y, p.getColor(), chesslist))
				LongLeftUpLength++;
			else
				break;
		}
		for (int x = p.getX() + 1, y = p.getY() + 1; x <= ChessBoard.COLS && y <= ChessBoard.ROWS; x++, y++) {
			if (findNeedPoint(x, y, p.getColor(), chesslist))
				LongLeftUpLength++;
			else
				break;
		}
		return LongLeftUpLength;
	}

	public void computerVscomputer(ChessBoard chessboard) {
		boolean flag = true;
		while (true && chessboard.GetMode() == 2) {

			// 黑子落
			Point NextStep;
			int stepx, stepy;
			Point[] chesslist = chessboard.getChesslist();
			if (flag) {
				chesslist = chessboard.BlackAddChess(chessboard.ROWS / 2, chessboard.COLS / 2);
				flag = false;
			} else {

				NextStep = BlackNextStep(chesslist);
				stepx = NextStep.getX();
				stepy = NextStep.getY();

				chesslist = chessboard.BlackAddChess(stepx, stepy);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					System.out.println("thread error");
				}
			}

			// 白子落
			NextStep = WhiteNextStep(chesslist);
			stepx = NextStep.getX();
			stepy = NextStep.getY();
			chesslist = chessboard.WhiteAddChess(stepx, stepy);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("thread error");
			}

		}
	}
}
