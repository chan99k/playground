package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Boj_11722 {
	private int N;
	private int[] Ai;

	private void getInput() {
		var br = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(br.readLine());
			Ai = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt)
				.toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printAnswer() {
		List<Integer> lds = new ArrayList<>();

		for (int num : Ai) {
			int idx = Collections.binarySearch(lds, num, Comparator.reverseOrder());
			if (idx < 0) {
				idx = -(idx + 1);
			}
			if (lds.size() == idx) {
				lds.add(num);
			} else {
				lds.set(idx, num);
			}
		}

		System.out.println(lds.size());

	}

	public static void main(String[] args) {
		var sol = new Boj_11722();
		sol.getInput();
		sol.printAnswer();

	}
}
