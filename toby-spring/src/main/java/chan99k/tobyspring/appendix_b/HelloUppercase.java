package chan99k.tobyspring.appendix_b;

/**
 * 직접 만든 프록시 객체
 * 다이나믹 프록시를 사용하면 사용할 일 없다.
 * 인터페이스에 정의된 메서드의 대부분은 실제론 기능을 확장하지 않음에도 불구하고 무한정 재정의해야 한다는 불편함이 있다.
 */
public class HelloUppercase implements Hello {
	Hello hello;

	public HelloUppercase(Hello hello) {
		this.hello = hello;
	}

	@Override
	public String sayHello(String name) {
		return hello.sayHello(name).toUpperCase(); // 위임과 부가기능 적용
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}
}
