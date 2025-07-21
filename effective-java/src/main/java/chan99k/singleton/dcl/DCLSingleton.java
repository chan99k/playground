package chan99k.singleton.dcl;

public class DCLSingleton {
	/**
	 * <p>
	 *     volatile 은 해당 변수에 대한 특정 스레드의 변경사항이 다른 스레드에게 즉시 보이도록 보장한다. <br>
	 *     또한 명령어 재배치로 인해 미완성된 객체가 다른 스레드에 노출되는 것을 방지한다. <br>
	 *     예를 들어, new DCLSingleton() 연산은 내부적으로 <p>(1) 메모리 할당, (2) 생성자 호출, (3) instance 참조에 할당 </p> 이라는 여러 단계로 이루어지는데,
	 *     컴파일러나 CPU 최적화로 인해 이 순서가 변경될 수 있다. <br>
	 *     만약 (1)과 (3)이 먼저 실행되고 (2)가 나중에 실행되는 도중 다른 스레드가 instance를 접근하면, null이 아니지만 아직 초기화되지 않은 객체를 사용하게 될 위험이 있다. <br>
	 *     volatile은 이러한 재배치를 금지하여 DCL 패턴의 안정성을 높인다.
	 * </p>
	 */
	private volatile static DCLSingleton INSTANCE;
	private final String name;

	public String getName() {
		return name;
	}

	private DCLSingleton(String name) {
		this.name = name;
	}

	/**
	 * <p></ㅔ>DCL(Double-Checked Locking) 방식 <br>
	 * 인스턴스가 null인지 먼저 확인하고, null인 경우에만 synchronized 블록으로 진입하여 다시 한번 null 여부를 체크한 후 인스턴스를 생성한다. <br>
	 * 이를 통해 초기 생성 시에만 동기화가 이루어지도록 한다. </p>
	 *
	 * <p>
	 *     장점 : <br>
	 *     - 인스턴스가 생성된 이후에는 동기화 블록에 진입하지 않으므로, Thread-Safe Lazy Initialization 방식보다 성능상 이점이 있다. <br>
	 *     - 지연 로딩과 스레드 안전성을 모두 만족시킬수 있다.
	 * </p>
	 * <p>
	 *     단점 : <br>
	 *     - 구현이 다소 복잡하다. <br>
	 *     - 초기 자바 버전에서는 JMM의 한계로 volatile 키워드가 명령어 재배치를 완벽하게 막지 못하여 DCL 패턴이 불안정하게 동작할 수 있다.
	 * </p>
	 */
	public static DCLSingleton getInstance(String name) {
		if (INSTANCE == null) {
			synchronized (DCLSingleton.class) {
				if (INSTANCE == null) {
					INSTANCE = new DCLSingleton(name);
				}
			}
		}
		return INSTANCE;
	}
}
