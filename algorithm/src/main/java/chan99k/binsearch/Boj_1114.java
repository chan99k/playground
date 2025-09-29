package chan99k.binsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 정렬된 값이 중요하다기 보다는, 특정 조건에 따라 다른 쪽을 탐색에서 제외할 수 있다는 게 중요한 컨셉
// 최초로 trasition 이 발생한는 부분을 찾는 문제가 많이 있다
// 조건을 만족하는 값 중에 제일 00 한 값을 찾아라
// 입력이 아니라 출력이 기준이 되는 경우가 많다
public class Boj_1114 {
	private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		// 통나무 길이, 자를 수 있는 위치 개수, 최대 자르기 횟수
		int[] lkc = getInput();
		int[] location = getInput();
		Arrays.sort(location);
		// 이분탐색 범위: 최소 1, 최대 통나무 전체 길이
		int l = 1, r = lkc[0], answer = r;

		// 가능한 최대 조각 길이 중 최솟값을 찾는 이분탐색
		while (l <= r) {
			int m = l + (r - l) / 2;
			// 길이 m 이하로 모든 조각을 만들 수 있는지 확인
			if (cond(location, m, lkc[0], lkc[2])) { // 가능하면 더 작은 길이 시도
				answer = m; // 가능한 답 저장
				r = m - 1;
			} else { // 불가능하면 더 큰 길이를 목표로 시도
				l = m + 1;
			}
		}
		int firstCut = findFirstCut(location, answer, lkc[0], lkc[2]);

		System.out.println(answer + " " + firstCut);
	}

	private static boolean cond(int[] location, int targetLen, int L, int C) {
		int curPos = 0, cutCount = 0; // 현재 위치와 사용한 자르기 횟수

		for (int i = 0; i < location.length; i++) {
			// 다음 자를 수 있는 위치까지의 거리가 targetLen을 초과하면
			if (location[i] - curPos > targetLen) {
				// 첫 번째 위치까지도 targetLen을 초과하면 불가능
				if (i == 0)
					return false;

				// 이전 위치에서 자르기 (가장 긴 조각이 targetLen 이하가 되도록)
				cutCount++;
				if (cutCount > C)
					return false; // 자르기 횟수 초과

				curPos = location[i - 1]; // 자른 위치로 이동
				i--; // 현재 위치를 다시 확인 (새로운 조각 시작점에서)
			}
		}

		// 마지막 조각 (curPos부터 L까지)이 targetLen을 초과하는지 확인
		if (L - curPos > targetLen) {
			cutCount++; // 마지막에 한 번 더 자르기 필요
			return cutCount <= C; // 총 자르기 횟수가 허용 범위 내인지
		}

		return true; // 가장 긴 조각이 targetLen 이하로 만들 수 있음
	}

	private static int[] getInput() throws IOException {
		return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
	}

	private static int findFirstCut(int[] location, int targetLen, int L, int C) {
		// 뒤에서부터 자르는 전략으로 첫 번째 자르는 위치 찾기
		int curPos = L, cutCount = 0;
		int firstCut = 0;

		// 뒤에서부터 탐색
		for (int i = location.length - 1; i >= 0; i--) {
			if (curPos - location[i] > targetLen) {
				cutCount++;
				firstCut = location[i + 1]; // 마지막으로 자른 위치가 첫 번째 자르기
				curPos = location[i + 1];
				if (cutCount == C) break;
			}
		}

		// 앞쪽에서 자르기가 필요한 경우
		if (curPos > targetLen && cutCount < C) {
			firstCut = location[0];
		}

		return firstCut;
	}

}