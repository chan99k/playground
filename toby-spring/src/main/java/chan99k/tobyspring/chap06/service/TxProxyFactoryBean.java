package chan99k.tobyspring.chap06.service;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object> { // 오브젝트 타입을 지정할 수도 있지만 범용적으로 사용하기 위해 Object로 지정
	Object target;
	PlatformTransactionManager transactionManager;
	String pattern;
	Class<?> serviceInterface; // 다이나믹 프록시를 생성할 때 필요.

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	@Override
	public Object getObject() throws Exception {
		TransactionHandler transactionHandler = new TransactionHandler();
		transactionHandler.setTarget(target);
		transactionHandler.setTransactionManager(transactionManager);
		transactionHandler.setPattern(pattern);
		return Proxy.newProxyInstance(
			getClass().getClassLoader(),
			new Class[] {serviceInterface},
			transactionHandler
		);
	}

	@Override
	public Class<?> getObjectType() {
		return serviceInterface; // 펙터리 빈이 생성하는 오브젝트의 타입은 DI 받은 인터페이스 타입에 따라 달라진다.
		// 따라서 다양한 타입의 프록시 오브젝트 생성에 재사용할 수 있다.
	}

	public boolean isSingleton() {
		return false; // getObject() 가 매번 같은 오브젝트를 리턴하지 않는다는 의미
	}
}
