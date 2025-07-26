package chan99k.tobyspring.appendix_b.factorybean;

import org.springframework.beans.factory.FactoryBean;

public class MessageFactoryBean implements FactoryBean<Message> {
	public void setText(String text) {
		this.text = text;
	}

	String text;

	@Override
	public Message getObject() throws Exception {
		return Message.getMessage(this.text);
	}

	@Override
	public Class<? extends Message> getObjectType() {
		return Message.class;
	}

	/**
	 * 이 팩토리빈은 매번 요청할 때마다 새로운 오브젝트를 만드므로 false로 설정한다. <br>
	 * 이것은 팩토리 빈의 동작 방식에 관한 설정이고 만들어진 빈 오브젝트는 싱글톤으로 스프링이 관리해줄 수 있다.
	 */
	public boolean isSingleton() {
		return false;
	}
}
