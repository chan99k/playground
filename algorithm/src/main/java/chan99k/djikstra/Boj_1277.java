package chan99k.djikstra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

import org.jetbrains.annotations.NotNull;

public class Boj_1277 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] nw = Arrays.stream(br.readLine().split(" "))
			.mapToInt(Integer::parseInt).toArray();
		int N = nw[0], W = nw[1];

		double M = Double.parseDouble(br.readLine());

		Point[] plants = new Point[N + 1];
		for (int i = 1; i <= N; i++) {
			int[] xy = Arrays.stream(br.readLine().split(" "))
				.mapToInt(Integer::parseInt).toArray();
			plants[i] = new Point(xy[0], xy[1]);
		}

		boolean[][] connected = new boolean[N + 1][N + 1];
		for (int i = 0; i < W; i++) {
			int[] fd = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			int from = fd[0], dest = fd[1];
			connected[from][dest] = true;
			connected[dest][from] = true;
		}

		double result = dijkstra(N, M, plants, connected);

		System.out.println((int)(result * 1000));
	}

	static double dijkstra(int N, double M, Point[] plants, boolean[][] connected) {
		double[] dist = new double[N + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[1] = 0;
		boolean[] visited = new boolean[N + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.offer(new Edge(1, 0));

		while (!pq.isEmpty()) {
			var curr = pq.poll();
			int currPlant = curr.to;

			if (visited[currPlant])
				continue;
			visited[currPlant] = true;

			if (currPlant == N)
				return dist[N];

			for (int nextPlant = 1; nextPlant <= N; nextPlant++) {
				if (visited[nextPlant])
					continue;

				double distance = plants[currPlant].distantTo(plants[nextPlant]);

				if (distance > M)
					continue;

				double edgeCost = connected[currPlant][nextPlant] ? 0 : distance;
				double newDist = dist[currPlant] + edgeCost;

				if (newDist < dist[nextPlant]) {
					dist[nextPlant] = newDist;
					pq.offer(new Edge(nextPlant, newDist));
				}

			}
		}

		return dist[N];
	}

	static class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		double distantTo(Point other) {
			long dx = this.x - other.x;
			long dy = this.y - other.y;
			return Math.sqrt(dx * dx + dy * dy);
		}
	}

	static class Edge implements Comparable<Edge> {
		int to;
		double cost;

		Edge(int to, double cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(@NotNull Edge o) {
			return Double.compare(this.cost, o.cost);
		}
	}
}
