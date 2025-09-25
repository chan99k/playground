package chan99k.cycledetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Leet_210 {
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		int[] indegree = new int[numCourses];

		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[0], new ArrayList<>());
			graph.get(pre[0]).add(pre[1]);
			indegree[pre[1]]++; // 진입 차수 계산
		}

		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}

		int processed = 0;
		List<Integer> result = new ArrayList<>();
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			result.add(curr);
			processed++;

			if (graph.containsKey(curr)) {
				for (int next : graph.get(curr)) {
					indegree[next]--;
					if (indegree[next] == 0) {
						queue.offer(next);
					}
				}
			}
		}

		if (processed == numCourses) {
			return new int[] {};
		} else {
			return result.stream().mapToInt(Integer::intValue).toArray();
		}
	}
}
