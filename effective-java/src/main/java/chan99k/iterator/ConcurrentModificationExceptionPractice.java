package chan99k.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcurrentModificationExceptionPractice {

	public static void main(String[] args) {
		List<String> fruits = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
		// 예외가 발생할것 같았지만, iterator.hasNext() 가 호출되지 않아 modCount 변경을 모른다.
		// Banana 를 제거한뒤 it의 커서는 2를 가리키고, list의 크기가 2로 줄었기 때문에 hasNext()가 false가 되어 루프를 빠져나온다.
		for (String fruit : fruits) {
			if (fruit.equals("Banana")) {
				fruits.remove(fruit);
			}
		}

		List<String> list = new ArrayList<>();

		list.add("str1");
		list.add("str2");
		list.add("str3");

		for (String str : list) {
			if ("str1".equals(str)) {
				list.remove(str); // 컬렉션을 직접 수정하여 modCount가 변경됨
			}
		}
	}
}
