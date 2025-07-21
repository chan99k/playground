package chan99k.singleton.staticinitblock;

/**
 * 장점 :
 * - 클래스 로딩 시점에 인스턴스가 생성되어 스레드 안정성이 보장된다.
 * - 정적 블록을 사용하므로 인스턴스 생성 과정에서 발생하는 예외를 처리할 수 있다.
 * 단점 :
 * - 지연 로딩을 지원하지 않아 사용되지 않을 경우 메모리 누수가 발생한다.
 */
public class StaticBlockSingleton {
	private static final StaticBlockSingleton INSTANCE;
	private final String name;

	static {
		try {
			INSTANCE = new StaticBlockSingleton("스태틱 블록으로 만든 싱글턴 객체");
		} catch (Exception e) {
			throw new RuntimeException("싱글턴 생성중 예외 발생");
		}
	}

	public String getName() {
		return name;
	}

	private StaticBlockSingleton(String name) {
		this.name = name;
	}

	public static StaticBlockSingleton getInstance(String name) {
		return INSTANCE;
	}
}
