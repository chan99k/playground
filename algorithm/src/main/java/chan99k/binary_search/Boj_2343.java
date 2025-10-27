package chan99k.binary_search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 강토는 자신의 기타 강의 동영상을 블루레이로 만들어 판매하려고 한다. 블루레이에는 총 N개의 강의가 들어가는데, 블루레이를 녹화할 때, 강의의 순서가 바뀌면 안 된다. 순서가 뒤바뀌는 경우에는 강의의 흐름이 끊겨, 학생들이 대혼란에 빠질 수 있기 때문이다. 즉, i번 강의와 j번 강의를 같은 블루레이에 녹화하려면 i와 j 사이의 모든 강의도 같은 블루레이에 녹화해야 한다.
 * <p>
 * 강토는 이 블루레이가 얼마나 팔릴지 아직 알 수 없기 때문에, 블루레이의 개수를 가급적 줄이려고 한다. 오랜 고민 끝에 강토는 M개의 블루레이에 모든 기타 강의 동영상을 녹화하기로 했다. 이때, 블루레이의 크기(녹화 가능한 길이)를 최소로 하려고 한다. 단, M개의 블루레이는 모두 같은 크기이어야 한다.
 * <p>
 * 강토의 각 강의의 길이가 분 단위(자연수)로 주어진다. 이때, 가능한 블루레이의 크기 중 최소를 구하는 프로그램을 작성하시오.
 */
public class Boj_2343 {

	/**
	 * 첫째 줄에 강의의 수 N (1 ≤ N ≤ 100,000)과 M (1 ≤ M ≤ N)이 주어진다. 다음 줄에는 강토의 기타 강의의 길이가 강의 순서대로 분 단위로(자연수)로 주어진다. 각 강의의 길이는 10,000분을 넘지 않는다.
	 */
	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));

		int[] NM = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
		int N = NM[0];
		int M = NM[1];
		var lectures = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();

		invoke(M, lectures);
	}

	/**
	 * 첫째 줄에 가능한 블루레이 크기중 최소를 출력한다.
	 */
	private static void invoke(int M, int[] lectures) {
		// “이분 탐색의 대상이 배열 인덱스가 아니라, 정답 후보 값(용량)이다“
		int left = Arrays.stream(lectures).max().getAsInt(); // 블루레이 용량의 최솟값
		int right = Arrays.stream(lectures).sum(); // 블루레이 용량의 최댓값
		int mid = (left + right) / 2;
		int answer = Integer.MAX_VALUE;

		while (left <= right) {
			int count = 1; // 초기 1개부터 시작
			int sum = 0;

			for (int lecture : lectures) {
				if (sum + lecture > mid) {
					count++;
					sum = lecture;
				} else {
					sum += lecture;
				}
			}

			if (count > M) {
				left = mid + 1;
			} else {
				answer = mid;
				right = mid - 1;
			}
			mid = (left + right) / 2;
		}

		System.out.println(answer);

	}
}
