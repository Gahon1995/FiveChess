package BaseChess;

/**
 * 
 * @author Hao
 *
 *
 *         测试线程类
 */
public class TestforThread extends Thread {

	public static void main(String[] args) {
		testfunction();
	}

	public static void testfunction() {
		System.out.println("Befor the thread the test");
		Thread t = new TestforThread();
		t.start();

		System.out.println("After the thread the test");

	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("the public " + i);
		}
	}

}
