package chan99k.tobyspring.appendix_b.jdk.proxy;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

import chan99k.tobyspring.appendix_b.UppercaseHandler;

public class DynamicProxyTest {
	@Test
	@DisplayName("JDK 다이내믹 프록시 테스트")
	void simpleProxy() {
		chan99k.tobyspring.appendix_b.Hello proxiedHello = (chan99k.tobyspring.appendix_b.Hello)Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class[] {chan99k.tobyspring.appendix_b.Hello.class},
			new UppercaseHandler(new chan99k.tobyspring.appendix_b.HelloTarget())
		);

		assertThat(proxiedHello.sayHello("somi")).isEqualTo("HELLO SOMI");
		assertThat(proxiedHello.sayHi("somi")).isEqualTo("HI SOMI");
		assertThat(proxiedHello.sayThankYou("somi")).isEqualTo("FUCK YOU SOMI");
	}

	@Test
	@DisplayName("스프링 ProxyFactoryBean 을 활용한 프록시 테스트")
	void proxyFactoryBean() {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(new HelloTarget());
		proxyFactoryBean.addAdvice(new UppercaseAdvice()); // 부가 기능을 담은 어드바이스를 제공한다. 여러개를 추가할 수도 있다
		Hello proxiedHello = (Hello)proxyFactoryBean.getObject(); // FactoryBean 이므로 getObject()로 생성된 프록시를 가져온다

		assertThat(proxiedHello.sayHello("somi")).isEqualTo("HELLO SOMI");
		assertThat(proxiedHello.sayHi("somi")).isEqualTo("HI SOMI");
		assertThat(proxiedHello.sayThankYou("somi")).isEqualTo("FUCK YOU SOMI");
	}

	static class UppercaseAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed(); // 리플렉션의 Method 와 달리, 메소드 실행 시 타깃 오브젝트를 전달할 필요가 없다. MethodInvocation은 메소드 정보와 함께 타깃 오브젝트를 알고 있기 때문이다.
			return ret.toUpperCase(); // 부가 기능 적용
		}
	}

	interface Hello {
		String sayHello(String name);

		String sayHi(String name);

		String sayThankYou(String name);
	}

	static class HelloTarget implements Hello {
		@Override
		public String sayHello(String name) {
			return "Hello " + name;
		}

		@Override
		public String sayHi(String name) {
			return "Hi " + name;
		}

		@Override
		public String sayThankYou(String name) {
			return "fuck you " + name;
		}
	}
}
