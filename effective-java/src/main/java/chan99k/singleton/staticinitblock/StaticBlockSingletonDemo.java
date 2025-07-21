package chan99k.singleton.staticinitblock;

public class StaticBlockSingletonDemo {
	static int nNum = 0;

	public static void main(String[] args) {
		Runnable task = () -> {
			try {
				nNum++;
				StaticBlockSingleton instance = StaticBlockSingleton.getInstance(nNum + "번째 객체");
				System.out.println("This is the " + instance.getName() + " !!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(task);
			t.start();
		}
	}
}
