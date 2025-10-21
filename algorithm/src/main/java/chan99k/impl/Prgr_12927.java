package chan99k.impl;

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

}
