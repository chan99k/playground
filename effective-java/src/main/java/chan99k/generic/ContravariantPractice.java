package chan99k.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ContravariantPractice {
    static class MyArrayList<T> {
        Object[] element = new Object[5];
        int index = 0;


        public MyArrayList() {
        }

        // 요소 추가 메서드
        public void add(T item) {
            if (index < element.length) {
                element[index++] = item;
            }
        }

        // 하한 경계 와일드카드(? super T)를 사용한 반공변성 예제
        // 이 메서드는 T 타입이나 T의 상위 타입의 컬렉션에 요소를 추가할 수 있음
        // 즉, 소비자(consumer) 역할을 하는 매개변수에 적합
        public void addElementsTo(Collection<? super T> out) {
            for (int i = 0; i < index; i++) {
                // 안전한 형변환: element 배열에는 T 타입만 저장되었음
                @SuppressWarnings("unchecked")
                T item = (T) element[i];
                out.add(item);
            }
        }

        @Override
        public String toString() {
            return Arrays.toString(Arrays.copyOf(element, index)); // 실제 사용된 요소만 출력
        }
    }

    public static void main(String[] args) {
        // Integer 타입의 MyArrayList 생성
        MyArrayList<Integer> intList = new MyArrayList<>();
        intList.add(1);
        intList.add(2);
        intList.add(3);
        
        System.out.println("원본 Integer 리스트: " + intList);

        // Integer의 상위 타입인 Number 컬렉션에 요소 추가 (반공변성)
        List<Number> numberList = new LinkedList<>();
        intList.addElementsTo(numberList);
        System.out.println("Number 리스트로 복사: " + numberList);

        // Integer의 상위 타입인 Object 컬렉션에 요소 추가 (반공변성)
        List<Object> objectList = new LinkedList<>();
        intList.addElementsTo(objectList);
        System.out.println("Object 리스트로 복사: " + objectList);

        // 다음 코드는 컴파일 오류가 발생함 (String은 Integer의 상위 타입이 아님)
        // List<String> stringList = new LinkedList<>();
        // intList.addElementsTo(stringList); // 컴파일 오류!

        // 반공변성 활용 예: 정렬 메서드
        // Comparable<? super T>는 T 또는 T의 상위 타입에 대한 비교 가능성을 의미
        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9);
        System.out.println("정렬 전: " + numbers);
        java.util.Collections.sort(numbers); // Comparable<? super Integer>를 사용
        System.out.println("정렬 후: " + numbers);
    }
}