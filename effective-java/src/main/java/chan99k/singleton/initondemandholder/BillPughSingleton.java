package chan99k.singleton.initondemandholder;

public class BillPughSingleton {

	private BillPughSingleton() {
	}

	/**
	 * <p>
	 *     Bill Pugh Singleton 구현 방식은 중첩 클래스의 로딩 시점을 이용하여 싱글턴 인스턴스의 지연 초기화와 스레드 안전성을 동시에 보장한다. <br>
	 *     JVM의 클래스 로더 메커니즘과 초기화 순서를 이용한다.
	 * </p>
	 * <p>
	 *     동작 원리 : <br>
	 *     1. `BillPughSingleton` 클래스가 로드될 때 `SingletonHolder` 내부 클래스는 로드되지 않는다. <br>
	 *     2. `getInstance()` 메서드가 처음 호출될 때 비로소 `SingletonHolder` 클래스가 로드되고 초기화된다. <br>
	 *     3. `SingletonHolder` 클래스가 초기화될 때, `INSTANCE` 필드가 초기화되면서 `BillPughSingleton`의 인스턴스가 생성된다. <br>
	 *     4. JVM은 클래스 초기화 과정을 스레드에 안전하게 보장하므로, 여러 스레드가 동시에 `getInstance()`를 호출해도 `INSTANCE`는 한 번만 생성된다.
	 * </p>
	 * <p>
	 *     장점 : <br>
	 *     - 지연 초기화를 지원한다. <br>
	 *     - 스레드 안전성을 보장한다. <br>
	 *     - `synchronized` 키워드를 사용하지 않아 성능 오버헤드가 없다. <br>
	 *     - DCL 방식보다 구현이 간단하고 안정적이다.
	 * </p>
	 * <p>
	 *     단점 : <br>
	 *     - 특별한 단점은 없지만, 리플렉션이나 직렬화를 통해 싱글턴이 깨질 가능성은 여전히 존재한다.
	 * </p>
	 */
	private static class SingletonHolder {
		private static final BillPughSingleton INSTANCE = new BillPughSingleton();
	}

	public static BillPughSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

}
