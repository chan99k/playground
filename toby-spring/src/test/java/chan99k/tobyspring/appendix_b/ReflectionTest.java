package chan99k.tobyspring.appendix_b;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReflectionTest {
	@Test
	@DisplayName("메서드 호출")
	void invokeMethodTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		String name = "Spring";
		assertThat(name.length()).isEqualTo(6);

		Method length = String.class.getMethod("length");
		assertThat(length.invoke(name)).isEqualTo(name.length());

		assertThat(name.charAt(0)).isEqualTo('S');

		Method charAt = String.class.getMethod("charAt", int.class);
		assertThat(charAt.invoke(name, 0)).isEqualTo('S');
	}

	@Test
	@DisplayName("인터페이스를 통해 HelloTarget 오브젝트를 사용하는 클라이언트 역할을 하는 테스트")
	void simpleProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("somi")).isEqualTo("Hello somi");
		assertThat(hello.sayHi("somi")).isEqualTo("Hi somi");
		assertThat(hello.sayThankYou("somi")).isEqualTo("fuck you somi");
	}

	@Test
	@DisplayName("HelloUpperCase 프록시 테스트")
	void upperCaseProxy() {
		Hello hello = new HelloUppercase(new HelloTarget());
		assertThat(hello.sayHello("somi")).isEqualTo("HELLO SOMI");
		assertThat(hello.sayHi("somi")).isEqualTo("HI SOMI");
		assertThat(hello.sayThankYou("somi")).isEqualTo("FUCK YOU SOMI");
	}

	@Test
	@DisplayName("다이내믹 프록시 테스트")
	void dynamicUpperProxy() {
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class[] {Hello.class},
			new UppercaseHandler(new HelloTarget())
		);
		assertThat(proxiedHello.sayHello("somi")).isEqualTo("HELLO SOMI");
		assertThat(proxiedHello.sayHi("somi")).isEqualTo("HI SOMI");
		assertThat(proxiedHello.sayThankYou("somi")).isEqualTo("FUCK YOU SOMI");
	}
}
