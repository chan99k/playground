package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Boj_1072 {
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static int answer = -1;

	public static void main(String[] args) throws Exception {
		int[] xy = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		binSearch(xy, 0, 1_000_000_000);
		System.out.println(answer);
	}

	private static void binSearch(int[] xy, int left, int right) {
		if (left > right)
			return;

		int x = xy[0]; // 게임 횟수
		int y = xy[1]; // 이긴 게임
		int z = Math.toIntExact((long)y * 100 / x);

		int mid = left + (right - left) / 2;
		int cal = Math.toIntExact((long)(y + mid) * 100 / (x + mid));

		if (z != cal) {
			if (answer == -1)
				answer = mid;
			else
				answer = Math.min(answer, mid);
			binSearch(xy, left, mid - 1); // 이미 mid 는 위에서 확인했기 때문에 제외하고 진행
		} else {
			binSearch(xy, mid + 1, right); // 이미 mid 는 위에서 확인했기 때문에 제외하고 진행
		}
	}
}
