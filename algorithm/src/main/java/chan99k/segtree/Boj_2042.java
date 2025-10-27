package chan99k.segtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 세그먼트 트리는 합, 최댓값, 최솟값 등 "구간의 정보"를 저장하는 이진트리 자료구조입니다.
 * 루트 노드 (Root): 전체 구간 (1 ~ $N$)의 합을 저장합니다.
 * <p>
 * 내부 노드 (Internal Node): 특정 구간의 합을 저장합니다.
 * <p>
 * 이 노드의 자식들은 해당 구간을 절반으로 나눈 구간의 합을 각각 저장합니다. (예: 부모가 [1, 8]의 합을 저장하면, 자식은 [1, 4]와 [5, 8]의 합을 저장)
 * <p>
 * 리프 노드 (Leaf Node): arr[i]와 같이 실제 데이터 값 하나를 저장합니다.
 */
public class Boj_2042 {
	static long[] arr; // 원본 데이터
	static long[] tree; // 세그먼트 트리

	/**
	 * 1. init (트리 초기화) 배열 arr의 값들을 기반으로 세그먼트 트리를 구축합니다.
	 * <p>
	 * 재귀적으로 구간을 반씩 나눠가며 자식 노드를 채웁니다.
	 * <p>
	 * 리프 노드(Base Case): 구간의 시작과 끝이 같아지면 (start == end), 해당 노드에 arr[start] 값을 저장합니다.
	 * <p>
	 * 내부 노드: tree[node] = init(왼쪽 자식) + init(오른쪽 자식)으로 자신의 값을 정합니다.
	 * 시간 복잡도: $O(N)$
	 * @param node 현재 노드
	 * @param start 시작 구간
	 * @param end 구간 끝
	 * @return 왼쪽 오른쪽 자식 재귀 호출
	 */
	static long init(int node, int start, int end) {
		if (start == end) {
			return tree[node] = arr[start];
		}

		int mid = start + (end - start) / 2;

		// 자식 노드의 합을 현재 노드에 저장
		// 왼쪽 자식 : node *2 , [start, mid]
		// 오른쪽 자식 : node *2 +1 , [mid+1, end]
		return tree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
	}

	/**
	 * 2. query (구간 합 구하기)
	 * [left, right] 구간의 합을 구합니다. 현재 노드가 담당하는 구간 [start, end]와의 관계에 따라 3가지 경우로 나뉩니다.
	 * <p>
	 * [Case 1] 범위가 완전히 겹치지 않을 때 (left > end 또는 right < start)
	 * <p>
	 * 현재 노드의 구간은 우리가 찾는 구간 합에 기여하지 않습니다.
	 * <p>
	 * 0을 반환합니다. (합의 항등원)
	 * <p>
	 * [Case 2] 범위가 완전히 포함될 때 (left <= start && end <= right)
	 * <p>
	 * 현재 노드가 저장한 값(tree[node])이 우리가 찾는 구간 합의 일부입니다.
	 * <p>
	 * 이 값(tree[node])을 반환하고 더 이상 자식 노드로 내려가지 않습니다.
	 * <p>
	 * [Case 3] 범위가 일부만 겹칠 때 (그 외)
	 * <p>
	 * 현재 구간을 반으로 나눠, 왼쪽 자식과 오른쪽 자식에게 각각 query를 재귀적으로 호출합니다.
	 * <p>
	 * 두 자식에게서 반환된 값을 더해서 반환합니다.
	 * @param node 현재 노드 인덱스
	 * @param left 검색하고자 하는 구간의 시작점
	 * @param right 검색하고자 하는 구간의 종점
	 * @return 오른쪽 왼쪽 자식에 대한 쿼리 재귀 호출
	 */
	static long query(int node, int start, int end, int left, int right) {
		if (left > end || right < start) { // 찾는 구간과 현재 구간이 완전히 겹치지 않는다면
			return 0; // 합에 영향을 주지 않는 0 반환
		}

		// 범위가 완전히 포함될 때
		if (left <= start && end <= right) {
			return tree[node];
		}
		// 범위가 일부만 겹칠 때
		int mid = start + (end - start) / 2;
		return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
	}

	/**
	 *  update (값 변경하기)$b$번째 인덱스의 값을 $c$로 변경합니다.
	 * <p>
	 *  $O(N)$이 걸리는 init을 다시 호출하는 대신, $b$번째 인덱스와 관련된 노드들만 갱신합니다.
	 * <p>
	 *  **변화량(diff)**을 이용하는 것이 편리합니다.
	 * <p>
	 *  diff = (새 값 c) - (기존 값 arr[b])트리를 탐색하며 $b$번째 인덱스를 포함하는 노드(start <= b <= end)를 모두 찾습니다.
	 * <p>
	 *  해당 노드들을 만나면 tree[node] += diff를 수행하여 값을 갱신합니다.$b$번째 인덱스를 포함하지 않는 노드는 탐색을 중단합니다.
	 * <p>
	 *  이 경로는 루트에서 리프까지 단 하나의 경로이므로 $O(\log N)$만 걸립니다.
	 * @param node 현재 노드 인덱스
	 * @param index 변경하고자 하는 인덱스
	 * @param diff 변화량
	 */
	static void update(int node, int start, int end, int index, long diff) {
		if (index < start || index > end) { // 현재 노드의 범위 밖일 때
			return;
		}
		// 현재 노드 범위 안에 있을 때
		tree[node] += diff;

		// 리프노드가 아니면 자식 노드들도 갱신하러 감
		if (start != end) {
			int mid = start + (end - start) / 2;
			update(node * 2, start, mid, index, diff);
			update(node * 2 + 1, mid + 1, end, index, diff);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		arr = new long[N + 1];
		for (int i = 1; i <= N; i++) {
			arr[i] = Long.parseLong(br.readLine());
		}

		tree = new long[N * 4];

		init(1, 1, N);

		var sb = new StringBuilder();
		int totalQueries = M + K;

		for (int i = 0; i < totalQueries; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			if (a == 1) {
				long c = Long.parseLong(st.nextToken());
				// b번째 인덱스의 값을 c 로 변경
				long diff = c - arr[b]; // 변화량 계산
				arr[b] = c;
				update(1, 1, N, b, diff); // 세그먼트 트리 갱신
			} else if (a == 2) {
				int c = Integer.parseInt(st.nextToken());
				// b 부터 c 까지의 구간 합을 쿼리
				sb.append(query(1, 1, N, b, c)).append('\n');
			}
		}
		System.out.println(sb.toString());
		br.close();
	}
}
