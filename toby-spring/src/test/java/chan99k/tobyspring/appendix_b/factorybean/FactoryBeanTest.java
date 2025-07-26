package chan99k.tobyspring.appendix_b.factorybean;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration // 설정파일 이름을 지정하지 않으면 "-context.xml"이 디폴트로 사용된다.
class FactoryBeanTest {
	@Autowired
	ApplicationContext context;

	@Test
	@DisplayName("")
	void getMessageFromFactoryBean() {
		Object message = context.getBean("message");
		assertThat(message).isInstanceOf(Message.class);
		assertThat(((Message)message).getText()).isEqualTo("Factory Bean");
	}

	@Test
	@DisplayName("팩토리 빈 자체를 반환한다.")
	void getFactoryBean() {
		Object bean = context.getBean("&message"); // 이름앞에 붙여주면 팩토리 빈 자체를 반환한다.
		assertThat(bean).isInstanceOf(MessageFactoryBean.class);
	}
}