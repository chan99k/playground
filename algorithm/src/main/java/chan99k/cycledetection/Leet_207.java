package chan99k.cycledetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Leet_207 {

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();

		// 그래프 구성
		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[0], new ArrayList<>());
			graph.get(pre[0]).add(pre[1]);
		}

		boolean[] visiting = new boolean[numCourses]; // 현재 DFS 경로
		boolean[] visited = new boolean[numCourses];  // 이미 처리된 노드

		// 모든 노드 검사
		for (int i = 0; i < numCourses; i++) {
			if (isCyclic(graph, i, visiting, visited)) {
				return false; // cycle 존재 → 모든 과목 이수 불가
			}
		}

		return true;
	}

	private boolean isCyclic(Map<Integer, List<Integer>> graph, int curr, boolean[] visiting, boolean[] visited) {
		if (visiting[curr]) {
			return true;   // 현재 경로에 이미 있음 → cycle 발견
		}

		if (visited[curr]) {
			return false;   // 이미 처리 완료된 노드
		}

		if (graph.containsKey(curr)) { // 교과목 리스트에 있다면
			visiting[curr] = true;
			for (int next : graph.get(curr)) { // 선수 과목에 대한 사이클 판별을 재귀적으로 수행
				if (isCyclic(graph, next, visiting, visited)) {
					return true;
				}
			}
			visiting[curr] = false;
		}

		visited[curr] = true; // 탐색 완료
		return false;
	}

	public static void main(String[] args) {
		var sol = new Leet_207();
		System.out.println("DFS 방식:");
		System.out.println(sol.canFinish(2, new int[][] {{1, 0}})); // true
		System.out.println(sol.canFinish(2, new int[][] {{1, 0}, {0, 1}})); // false
		
		System.out.println("위상 정렬 방식:");
		System.out.println(sol.canFinishTopological(2, new int[][] {{1, 0}})); // true
		System.out.println(sol.canFinishTopological(2, new int[][] {{1, 0}, {0, 1}})); // false
	}

	/**
	 *   1. 진입 차수 계산: 각 노드로 들어오는 간선의 개수를 계산
	 * <p>
	 *   2. 큐 초기화: 진입 차수가 0인 노드들을 큐에 추가
	 * <p>
	 *   3. 처리: 큐에서 노드를 하나씩 꺼내어 인접 노드들의 진입 차수를 감소시킴
	 * <p>
	 *   4. 결과: 모든 노드를 처리했다면 사이클이 없음을 의미
	 * <p>
	 * 시간 복잡도는 O(V + E)로 DFS 방식과 동일하지만, 위상 정렬은 실제 이수 순서도 제공할 수 있어 더 직관적이다.
	 */
	/* 위상 정렬 */
	public boolean canFinishTopological(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		int[] indegree = new int[numCourses];
		
		// 그래프 구성 및 진입 차수 계산
		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[1], new ArrayList<>());
			graph.get(pre[1]).add(pre[0]);
			indegree[pre[0]]++;
		}
		
		// 진입 차수가 0인 노드들을 큐에 추가
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				queue.offer(i);
			}
		}
		
		int processed = 0;
		while (!queue.isEmpty()) {
			int curr = queue.poll();
			processed++;
			
			// 인접한 노드들의 진입 차수 감소
			if (graph.containsKey(curr)) {
				for (int next : graph.get(curr)) {
					indegree[next]--;
					if (indegree[next] == 0) {
						queue.offer(next);
					}
				}
			}
		}
		
		return processed == numCourses; // 모든 노드를 처리했다면 사이클 없음
	}

	/* Union Find */
	/**
	 *  Note: Union Find는 무방향 그래프의 사이클 탐지에 적합하므로 이 문제에는 부적절
	 * <p>
	 * 왜 Union Find가 부적절한가?
	 * <p>
	 *   - [1,0]과 [0,1]이 있을 때, Union Find로는 단순히 0과 1이 연결되어 있다고 판단
	 * <p>
	 *   - 하지만 실제로는 1 → 0과 0 → 1의 방향이 중요함 (순환 의존성)
	 * <p>
	 *  Union Find는 무방향 그래프에서 Minimum Spanning Tree나 Connected Components 문제에 주로 사용
 	 */

	// 방향 그래프에서는 DFS나 위상 정렬을 사용해야 함
	static class UnionFind {
		private final int[] parent;
		private final int[] rank;
		
		public UnionFind(int n) {
			parent = new int[n];
			rank = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
			}
		}
		
		public int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]); // 경로 압축
			}
			return parent[x];
		}
		
		public boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			
			if (rootX == rootY) {
				return false; // 이미 같은 집합 → 사이클 발견
			}
			
			// Union by rank
			if (rank[rootX] < rank[rootY]) {
				parent[rootX] = rootY;
			} else if (rank[rootX] > rank[rootY]) {
				parent[rootY] = rootX;
			} else {
				parent[rootY] = rootX;
				rank[rootX]++;
			}
			return true;
		}
	}

	/* Floyd Cycle Detection */


	/**
	 * 	 Note: Floyd Cycle Detection은 연결 리스트의 사이클 탐지에 특화됨
	 * <p>
	 * 	 이 문제는 일반적인 방향 그래프이므로 직접 적용 불가
	 * <p>
	 * 	 Floyd 알고리즘의 핵심 아이디어: 느린 포인터(1칸)와 빠른 포인터(2칸) -> 토끼와 거북이 알고리즘
	 */
	static class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { val = x; }
	}
	
	public boolean hasCycleInLinkedList(ListNode head) {
		if (head == null || head.next == null) return false;
		
		ListNode slow = head;     // 느린 포인터 (1칸씩)
		ListNode fast = head;     // 빠른 포인터 (2칸씩)
		
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			
			if (slow == fast) {   // 만나면 사이클 존재
				return true;
			}
		}
		
		return false;
	}
	
	/* 기타 사이클 탐지 알고리즘들 */

	/**
	 * 5. Tarjan's Strongly Connected Components (SCC) Algorithm
	 * - 강연결 성분을 찾아 사이클 탐지
	 * - 시간복잡도: O(V + E)
	 * - DFS 기반이지만 더 정교한 정보 제공 (SCC 개수, 각 SCC의 노드들)
	 */
	public boolean canFinishTarjan(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[1], new ArrayList<>());
			graph.get(pre[1]).add(pre[0]);
		}
		
		int[] disc = new int[numCourses];    // 발견 시간
		int[] low = new int[numCourses];     // 백엣지로 도달 가능한 최소 발견 시간
		boolean[] onStack = new boolean[numCourses];
		boolean[] visited = new boolean[numCourses];
		int[] time = {0};
		
		for (int i = 0; i < numCourses; i++) {
			if (!visited[i] && tarjanDFS(graph, i, disc, low, onStack, visited, time)) {
				return false; // 크기가 1보다 큰 SCC 발견 → 사이클 존재
			}
		}
		return true;
	}
	
	private boolean tarjanDFS(Map<Integer, List<Integer>> graph, int u, int[] disc, int[] low, 
			boolean[] onStack, boolean[] visited, int[] time) {
		disc[u] = low[u] = ++time[0];
		visited[u] = true;
		onStack[u] = true;
		
		if (graph.containsKey(u)) {
			for (int v : graph.get(u)) {
				if (!visited[v]) {
					if (tarjanDFS(graph, v, disc, low, onStack, visited, time)) {
						return true;
					}
					low[u] = Math.min(low[u], low[v]);
				} else if (onStack[v]) {
					low[u] = Math.min(low[u], disc[v]);
				}
			}
		}
		
		// SCC의 루트인 경우
		if (low[u] == disc[u]) {
			int sccSize = 0;
			int w;
			do {
				w = u; // 간단한 구현을 위해 스택 대신 단순화
				onStack[w] = false;
				sccSize++;
			} while (w != u);
			
			if (sccSize > 1) return true; // 크기가 1보다 큰 SCC → 사이클
		}
		
		return false;
	}

	/**
	 * 6. Johnson's Algorithm (변형)
	 * - 모든 elementary cycles 찾기
	 * - 시간복잡도: O((V + E)(C + 1)) where C는 사이클 개수
	 * - 복잡하지만 모든 사이클 정보 제공
	 */
	public boolean canFinishJohnson(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[1], new ArrayList<>());
			graph.get(pre[1]).add(pre[0]);
		}
		
		// 간단화된 Johnson's 알고리즘 - 사이클 존재 여부만 확인
		boolean[] blocked = new boolean[numCourses];
		Map<Integer, List<Integer>> blockedMap = new HashMap<>();
		List<List<Integer>> allCycles = new ArrayList<>();
		
		for (int start = 0; start < numCourses; start++) {
			if (johnsonDFS(graph, start, start, new ArrayList<>(), blocked, blockedMap, allCycles)) {
				return false; // 사이클 발견
			}
			// 블록 해제
			for (int i = start; i < numCourses; i++) {
				blocked[i] = false;
			}
			blockedMap.clear();
		}
		return true;
	}
	
	private boolean johnsonDFS(Map<Integer, List<Integer>> graph, int v, int start, 
			List<Integer> path, boolean[] blocked, Map<Integer, List<Integer>> blockedMap, 
			List<List<Integer>> allCycles) {
		
		boolean foundCycle = false;
		path.add(v);
		blocked[v] = true;
		
		if (graph.containsKey(v)) {
			for (int w : graph.get(v)) {
				if (w == start) {
					// 사이클 발견
					allCycles.add(new ArrayList<>(path));
					foundCycle = true;
				} else if (!blocked[w]) {
					if (johnsonDFS(graph, w, start, path, blocked, blockedMap, allCycles)) {
						foundCycle = true;
					}
				}
			}
		}
		
		if (foundCycle) {
			unblock(v, blocked, blockedMap);
		} else {
			if (graph.containsKey(v)) {
				for (int w : graph.get(v)) {
					blockedMap.putIfAbsent(w, new ArrayList<>());
					if (!blockedMap.get(w).contains(v)) {
						blockedMap.get(w).add(v);
					}
				}
			}
		}
		
		path.remove(path.size() - 1);
		return foundCycle;
	}
	
	private void unblock(int u, boolean[] blocked, Map<Integer, List<Integer>> blockedMap) {
		blocked[u] = false;
		if (blockedMap.containsKey(u)) {
			List<Integer> list = new ArrayList<>(blockedMap.get(u));
			blockedMap.get(u).clear();
			for (int w : list) {
				if (blocked[w]) {
					unblock(w, blocked, blockedMap);
				}
			}
		}
	}
	
	/**
	 * 7. Matrix-based approach (인접행렬 거듭제곱)
	 * - 인접행렬을 V번 거듭제곱하여 사이클 탐지
	 * - 시간복잡도: O(V^4) - 비효율적
	 * - 작은 그래프에서만 실용적
	 */
	public boolean canFinishMatrix(int numCourses, int[][] prerequisites) {
		// 인접 행렬 초기화
		boolean[][] adj = new boolean[numCourses][numCourses];
		for (int[] pre : prerequisites) {
			adj[pre[1]][pre[0]] = true; // pre[1] → pre[0]
		}
		
		// Floyd-Warshall을 이용한 도달 가능성 계산
		boolean[][] reach = new boolean[numCourses][numCourses];
		for (int i = 0; i < numCourses; i++) {
			System.arraycopy(adj[i], 0, reach[i], 0, numCourses);
		}
		
		// k를 거쳐서 i에서 j로 갈 수 있는지 확인
		for (int k = 0; k < numCourses; k++) {
			for (int i = 0; i < numCourses; i++) {
				for (int j = 0; j < numCourses; j++) {
					reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
				}
			}
		}
		
		// 자기 자신으로 돌아오는 경로가 있다면 사이클 존재
		for (int i = 0; i < numCourses; i++) {
			if (reach[i][i]) {
				return false; // 사이클 발견
			}
		}
		
		return true;
	}
	
	/**
	 * 7-2. Matrix 거듭제곱을 이용한 방법 (더 직접적)
	 * - 인접행렬을 numCourses번 거듭제곱하여 사이클 탐지
	 */
	public boolean canFinishMatrixPower(int numCourses, int[][] prerequisites) {
		// 인접 행렬 구성
		int[][] matrix = new int[numCourses][numCourses];
		for (int[] pre : prerequisites) {
			matrix[pre[1]][pre[0]] = 1;
		}
		
		// matrix^k에서 대각선 원소가 0이 아니면 길이 k인 사이클 존재
		int[][] result = matrixPower(matrix, numCourses);
		
		for (int i = 0; i < numCourses; i++) {
			if (result[i][i] > 0) {
				return false; // 사이클 발견
			}
		}
		
		return true;
	}
	
	private int[][] matrixMultiply(int[][] a, int[][] b) {
		int n = a.length;
		int[][] result = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		return result;
	}
	
	private int[][] matrixPower(int[][] matrix, int power) {
		int n = matrix.length;
		int[][] result = new int[n][n];
		// 단위 행렬로 초기화
		for (int i = 0; i < n; i++) {
			result[i][i] = 1;
		}
		
		int[][] base = new int[n][n];
		for (int i = 0; i < n; i++) {
			System.arraycopy(matrix[i], 0, base[i], 0, n);
		}
		
		while (power > 0) {
			if (power % 2 == 1) {
				result = matrixMultiply(result, base);
			}
			base = matrixMultiply(base, base);
			power /= 2;
		}
		
		return result;
	}
	
	/**
	 * 8. 3-coloring DFS (White-Gray-Black)
	 * - 현재 구현된 DFS 방식과 유사하지만 3가지 색상 사용
	 * - White: 미방문, Gray: 방문 중, Black: 완료
	 * - 시간복잡도: O(V + E)
	 */
	public boolean canFinish3Color(int numCourses, int[][] prerequisites) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int[] pre : prerequisites) {
			graph.putIfAbsent(pre[1], new ArrayList<>());
			graph.get(pre[1]).add(pre[0]);
		}
		
		int[] color = new int[numCourses]; // 0: White, 1: Gray, 2: Black
		
		for (int i = 0; i < numCourses; i++) {
			if (color[i] == 0 && hasCycle3Color(graph, i, color)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean hasCycle3Color(Map<Integer, List<Integer>> graph, int node, int[] color) {
		color[node] = 1; // Gray
		
		if (graph.containsKey(node)) {
			for (int neighbor : graph.get(node)) {
				if (color[neighbor] == 1) return true;  // Back edge (사이클)
				if (color[neighbor] == 0 && hasCycle3Color(graph, neighbor, color)) {
					return true;
				}
			}
		}
		
		color[node] = 2; // Black
		return false;
	}
}
