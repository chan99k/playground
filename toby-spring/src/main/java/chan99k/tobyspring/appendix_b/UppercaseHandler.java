package chan99k.tobyspring.appendix_b;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
	Object target;

	public UppercaseHandler(Hello target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = method.invoke(target, args);
		// 메서드 이름이 sayHello, sayHi, sayThankYou 중 하나일 때만 변환
		if (result instanceof String && isTargetMethod(method.getName())) {
			return ((String)result).toUpperCase();
		}
		return result;
	}

	private boolean isTargetMethod(String methodName) {
		return methodName.equals("sayHello")
			|| methodName.equals("sayHi")
			|| methodName.equals("sayThankYou");
	}
}
