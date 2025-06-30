package chan99k.generic;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class VariantPractice {
	public static void main(String[] args) {
		covariantPractice();
		invariantPractice();
	}

	private static void covariantPractice() {

	}

	private static void invariantPractice(){

	}

	static class MyArrayList<T> {
		Object[] element = new Object[5];
		int index = 0;


		public MyArrayList(Collection<T> in) {
			for (T elem : in) {
				element[index++] = elem;
			}
		}


		public void clone(Collection<T> out) {
			for (Object elem : element) {
				out.add((T)elem);
			}
		}

		@Override
		public String toString() {
			return Arrays.toString(element); // 배열 요소들 출력
		}
	}
}
