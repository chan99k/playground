package chan99k.generic;

import java.util.Arrays;
import java.util.List;

public class NoVariantProblemPractice {
	public static void print(List<Object> arr) {
		for (Object e : arr) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		List<Integer> integers = Arrays.asList(1, 2, 3);
		// print(integers); // ! Error
	}
}
