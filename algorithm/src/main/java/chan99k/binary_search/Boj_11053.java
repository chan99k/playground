package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.
 * <p>
 * 예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.
 */
public class Boj_11053 {

	private int N;
	private int[] A;

	private void getInput() throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		A = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
	}

	private void printAnswer() {
		List<Integer> lis = new ArrayList<>();
		for (int num : A) {
			int idx = Collections.binarySearch(lis, num);
			if (idx < 0) {
				idx = -(idx + 1);
			}
			if (lis.size() == idx) {
				lis.add(num);
			} else {
				lis.set(idx, num);
			}
		}
		System.out.println(lis.size());
	}

	public static void main(String[] args) throws IOException {
		var sol = new Boj_11053();
		sol.getInput();
		sol.printAnswer();
	}

}
