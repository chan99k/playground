package chan99k.singleton.synclazyinit;

public class SynchronizedLazySingletonDemo {
	static int nNum = 0;

	public static void main(String[] args) {
		Runnable task = () -> {
			try {
				nNum++;
				SynchronizedLazySingleton instance = SynchronizedLazySingleton.getInstance(
					nNum + "번째 SynchronizedLazySingleton");
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
