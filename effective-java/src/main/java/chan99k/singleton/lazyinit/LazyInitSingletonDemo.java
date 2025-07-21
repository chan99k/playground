package chan99k.singleton.lazyinit;

public class LazyInitSingletonDemo {
	static int nNum = 0;

	public static void main(String[] args) {
		Runnable task = () -> {
			try {
				nNum++;
				LazyInitSingleton instance = LazyInitSingleton.getInstance(nNum + "번째 LazyInitSingleton");
				System.out.println("This is the " + instance.getName() + " !!!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		System.out.println("싱글턴 패턴으로 생성 객체를 제한하려고 했는데, 반복문의 실행 속도가 너무 빨라서 스레드별로 최대 10개만큼 각자 생성해버리는 문제가 발생한다.");
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(task);
			t.start();
		}
	}
}
