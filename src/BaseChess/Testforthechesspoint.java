package BaseChess;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Testforthechesspoint {

	public static void main(String[] args) {

		Point[] points = new Point[18 * 18];

		System.out.println("First the length of the arrays  " + points.length);

		Point p = new Point(1, 1, Color.black);
		points[0] = p;

		System.out.println("add the element , the length of the arrays   " + points.length);

		// ***** 避免检索null的相关元素
		for (Point temp : points) {
			if (temp != null)
				System.out.println(temp.getX() + " " + temp.getY());
		}

		ArrayList<ArrayList<Point>> result = testReturnArraylist();

		System.out.println("**********************");
		int count = 0;
		for (ArrayList<Point> al : result) {
			for (Point temp : al) {
				count++;
				System.out.println(count + "  " + temp.getX() + " " + temp.getY());
			}
		}

		Random rand = new Random();

		for (int i = 0; i < 10; i++) {
			System.out.println(i + "  " + rand.nextInt(10));
		}

	}

	public static ArrayList<ArrayList<Point>> testReturnArraylist() {

		ArrayList<ArrayList<Point>> result = new ArrayList<ArrayList<Point>>();
		ArrayList<Point> firstlist = new ArrayList<Point>();
		firstlist.add(new Point(1, 1, Color.black));
		firstlist.add(new Point(2, 2, Color.black));
		result.add(firstlist);
		ArrayList<Point> secondlist = new ArrayList<Point>();
		secondlist.add(new Point(1, 1, Color.white));
		secondlist.add(new Point(2, 2, Color.white));
		result.add(secondlist);

		return result;
	}

}
