package chan99k.singleton.eagerinit;

public class EagerInitDemo {
	static int nNum = 0;

	public static void main(String[] args) {
		Runnable task = () -> {
			try {
				nNum++;
				EagerInitSingleton instance = EagerInitSingleton.getInstance(nNum + "번째 LazyInitSingleton");
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
