package chan99k.djikstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Boj_2665 {
	private static final int[] dx = {0, 0, 1, -1};
	private static final int[] dy = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		char[][] graph = new char[N][N];
		for (int i = 0; i < N; i++) {
			graph[i] = br.readLine().toCharArray();
		}
		boolean[][] visited = new boolean[N][N];
		int[][] dist = new int[N][N];
		for (int[] d : dist) {
			Arrays.fill(d, Integer.MAX_VALUE);
		}

		dijkstra(graph, dist, visited);

		System.out.println(dist[N - 1][N - 1]);
	}

	private static void dijkstra(char[][] graph, int[][] dist, boolean[][] visited) {
		int N = graph.length;
		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return dist[o1[0]][o1[1]] - dist[o2[0]][o2[1]];
			}
		});
		pq.offer(new int[] {0, 0});
		dist[0][0] = 0;

		while (!pq.isEmpty()) {
			int[] curr = pq.poll();
			if (visited[curr[0]][curr[1]])
				continue;
			visited[curr[0]][curr[1]] = true;

			for (int k = 0; k < 4; k++) {
				int nx = curr[0] + dx[k];
				int ny = curr[1] + dy[k];
				if (0 <= nx && nx < N && 0 <= ny && ny < N && !visited[nx][ny]) {
					int newCost = dist[curr[0]][curr[1]] + (graph[nx][ny] - '1') * -1;
					if (newCost < dist[nx][ny]) {
						dist[nx][ny] = newCost;
						pq.offer(new int[] {nx, ny});
					}
				}
			}
		}
	}
}
