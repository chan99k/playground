package chan99k.javabeans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class JavaBeanMain {

    public static void main(String[] args) {
        try {
            // PersonBean 클래스의 정보를 분석한다.
            // BeanInfo 요청 시, 먼저 스레드 그룹별 캐시를 확인하여 있으면 즉시 반환하고,
            // 없으면 새로 생성한 뒤 캐시에 저장하여 다음 요청부터는 빠르게 응답할 수 있도록 하는 캐싱을 통한 성능 최적화 로직을 사용한다.
            BeanInfo beanInfo = Introspector.getBeanInfo(PersonBean.class);

            System.out.println("--- JavaBean Properties ---");
            // 프로퍼티 정보 출력
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                System.out.println("Property: " + pd.getName());
                System.out.println("  Read Method: " + (pd.getReadMethod() != null ? pd.getReadMethod().getName() : "null"));
                System.out.println("  Write Method: " + (pd.getWriteMethod() != null ? pd.getWriteMethod().getName() : "null"));
                System.out.println("  Property Type: " + pd.getPropertyType().getName());
                System.out.println();
            }

        } catch (IntrospectionException e) {
            System.err.println("JavaBean analysis failed: " + e.getMessage());
        }
    }
}
