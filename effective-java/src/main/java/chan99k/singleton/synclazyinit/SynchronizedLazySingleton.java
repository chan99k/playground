package chan99k.singleton.synclazyinit;

public class SynchronizedLazySingleton {
	private static SynchronizedLazySingleton INSTANCE;
	private String name;

	private SynchronizedLazySingleton(String name) {
		try {
			Thread.sleep(100);
			this.name = name;
		} catch (Exception e) {
			e.getCause();
		}
	}

	public String getName() {
		return name;
	}

	/**
	 * synchronized 키워드를 사용하여 스레드 안전성을 확보한다.
	 * <br>
	 * <p>장점: <br>
	 * - 멀티스레드 환경에서 안전하게 싱글턴 인스턴스를 생성하고 관리할 수 있다.<br>
	 * - 지연 초기화를 지원하여 인스턴스가 실제로 필요할 때 생성된다.
	 * </p>
	 * <p>단점: <br>
	 * - `getInstance()` 메서드가 호출될 때마다 `synchronized` 키워드로 인해
	 *   메서드 전체에 락이 걸리므로 오버헤드, 성능 저하가 발생할 수 있다. <br>
	 * - 특히, 인스턴스가 이미 생성된 후에도 모든 스레드가 메서드 진입 시 락을
	 *   획득하려고 시도하므로 불필요한 오버헤드가 발생한다.
	 * </p>
	 */
	public static synchronized SynchronizedLazySingleton getInstance(String name) {
		if (INSTANCE == null) {
			INSTANCE = new SynchronizedLazySingleton(name);
			return INSTANCE;
		}
		return INSTANCE;
	}

}
