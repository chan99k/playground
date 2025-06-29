package chan99k.generic;

import java.util.Arrays;
import java.util.Collection;

public class CovariantPractice {
	static class MyArrayList<T> {
		Object[] element = new Object[5];
		int index = 0;

		// 외부로부터 리스트를 받아와 매개변수의 모든 요소를 내부 배열에 추가하여 인스턴스화 하는 생성자
		public MyArrayList(Collection<? extends T> in) {
			for(T elem : in) {
				element[index++] = elem;
			}
		}
	}
	public static void main(String[] args) {
		// MyArrayList의 제네릭 T 타입은 Number
		MyArrayList<Number> list;

		// MyArrayList 생성하기 : Number 의 하위 타입인 Integer 로 생성이 가능하다.
		Collection<Integer> col = Arrays.asList(1, 2, 3, 4, 5);
		list = new MyArrayList<>(col);

		// MyArrayList 출력
		System.out.println(list); // [1, 2, 3, 4, 5]
	}
}
