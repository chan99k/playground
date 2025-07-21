package chan99k.singleton.lazyinit;

/**
 * getInstance() 메서드가 처음 호출될 때 싱글턴 인스턴스를 생성하여,
 * 실제 인스턴스가 필요한 시점까지 생성을 지연시켜 자원 효율성을 높일 수 있다.
 *<br>
 * <p>장점: <br>
 * - 인스턴스가 실제로 필요할 때 생성되므로 지연 로딩이 가능하다.<br>
 * - 초기 리소스 부담을 줄일 수 있다.
 *</p>
 * <p>단점: <br>
 * - 멀티스레드 환경에서 심각한 스레드 안전성 문제를 야기할 수 있다. <br>
 * - 여러 스레드가 동시에 if (instance == null) 조건을 통과하면,
 *   instance = new LazySingleton() 코드가 여러 번 실행되어
 *   여러 개의 인스턴스가 생성될 수 있다. <br>
 *   → 싱글턴의 유일성이 깨지게 된다.
 *  </p>
 */
public class LazyInitSingleton {
	private static LazyInitSingleton INSTANCE;
	private String name;

	private LazyInitSingleton(String name) {
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

	public static LazyInitSingleton getInstance(String name) {
		if (INSTANCE == null) {
			INSTANCE = new LazyInitSingleton(name);
			return INSTANCE;
		}
		return INSTANCE;
	}
}
