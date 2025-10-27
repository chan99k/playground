package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.
 * <p>
 * 예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 30, 50} 이고, 길이는 4이다.
 */
public class Boj_12015 {
	/**
	 * 첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다.
	 * <p>
	 * 둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000,000)
	 */
	public static void main(String[] args) {
		var br = new BufferedReader(new InputStreamReader(System.in));
		var sc = new Scanner(System.in);
		int N = sc.nextInt();
		sc.nextLine();
		int[] A = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		Boj_12015 sol = new Boj_12015();
		sol.invoke(A);
	}

	/**
	 * 첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.
	 */
	private void invoke(int[] A) {
		int[] lis = new int[A.length];
		int size = 0;
		for (int num : A) {
			int idx = Arrays.binarySearch(lis, 0, size, num);
			if (idx < 0) {
				idx = -(idx + 1);
			}

			lis[idx] = num;

			if (idx == size) {
				size++;
			}
		}
		System.out.println(size);
	}

}

// public static int lowerBound(List<Integer> list, int target) {
// 	int left = 0;
// 	int right = list.size();  // 마지막 인덱스 + 1 (exclusive)
//
// 	while (left < right) {
// 		int mid = (left + right) / 2;
//
// 		if (list.get(mid) < target) {
// 			left = mid + 1;
// 		} else {
// 			right = mid; // target 이상이면 mid 포함 가능성 있으니 줄임
// 		}
// 	}
//
// 	return left;
// }
