package chan99k.djikstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Boj_4485 {
	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static final int[] dx = {-1, 0, 1, 0};
	static final int[] dy = {0, -1, 0, 1};

	public static void main(String[] args) throws IOException {
		var br = new BufferedReader(new InputStreamReader(System.in));
		int tc = 0;
		while (true) {
			int N = Integer.parseInt(br.readLine());
			if (N == 0) {
				break;
			}
			tc++;
			int[][] graph = new int[N][N];
			int[][] dist = new int[N][N];
			for (int i = 0; i < N; i++) {
				graph[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				Arrays.fill(dist[i], Integer.MAX_VALUE);
			}
			boolean[][] visited = new boolean[N][N];

			dijkstra(N, graph, dist, visited);

			System.out.printf("Problem %d: %d\n", tc, dist[N - 1][N - 1]);
		}
	}

	private static void dijkstra(int N, int[][] graph, int[][] dist, boolean[][] visited) {
		PriorityQueue<Point> pq = new PriorityQueue<>(Comparator.comparingInt((p) -> dist[p.x][p.y]));
		pq.offer(new Point(0, 0));
		dist[0][0] = graph[0][0];

		while (!pq.isEmpty()) {
			var curr = pq.poll();
			if (visited[curr.x][curr.y])
				continue;
			visited[curr.x][curr.y] = true;

			for (int k = 0; k < 4; k++) {
				int nx = dx[k] + curr.x;
				int ny = dy[k] + curr.y;
				if (0 <= nx && nx < N && 0 <= ny && ny < N && !visited[nx][ny]) {
					int nc = graph[nx][ny] + dist[curr.x][curr.y];
					if (nc < dist[nx][ny]) {
						dist[nx][ny] = nc;
						pq.offer(new Point(nx, ny));
					}
				}
			}
		}
	}
}
