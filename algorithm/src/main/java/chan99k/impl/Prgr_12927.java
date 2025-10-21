package chan99k.impl;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Prgr_12927 {
	public long solution(int n, int[] works) {
		PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);

		for (int work : works) {
			pq.offer(work);
		}

		while (n > 0 && !pq.isEmpty()) {
			Integer curr = pq.poll();
			if (curr == 0) {
				continue;
			}
			pq.offer(curr - 1);
			n--;
		}

		long answer = 0;
		while (!pq.isEmpty()) {
			Integer rest = pq.poll();
			answer += (long)rest * rest;
		}
		return answer;
	}

	public long solutionByBinSearch(int n, int[] works) {
		int left = 0;
		int right = Arrays.stream(works).max().getAsInt();
		int target = 0;

		while (left <= right) {
			int mid = (left + right) / 2;

			long need = 0;
			for (int w : works) {
				if (w > mid)
					need += w - mid;
			}

			if (need > n) { // 너무 많이 줄여야 함 → mid를 키움
				left = mid + 1;
			} else { // n으로 충분히 줄일 수 있음 → 더 낮은 mid 시도
				target = mid;
				right = mid - 1;
			}
		}

		// 이제 각 작업을 target 이하로 줄이기
		long remain = n;
		long answer = 0;

		for (int i = 0; i < works.length; i++) {
			if (works[i] > target) {
				remain -= works[i] - target;
				works[i] = target;
			}
		}

		// 아직 n이 남았다면, 일부 작업을 1씩 더 줄임
		Arrays.sort(works);
		for (int i = works.length - 1; remain > 0 && i >= 0; i--, remain--) {
			if (works[i] > 0)
				works[i]--;
		}

		for (int w : works) {
			answer += (long)w * w;
		}

		return answer;
	}
}
