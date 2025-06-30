package chan99k.dfs;

public class Leet_111 {
	private record TreeNode(int val, TreeNode left, TreeNode right) {
	}

	@SuppressWarnings("ClassEscapesDefinedScope")
	public int minDepth(TreeNode root) {
		if (root == null)
			return 0;

		return dfs(root, 0);
	}

	private int dfs(TreeNode curr, int depth) {
		if (curr == null) {
			return Integer.MAX_VALUE; // null인 노드는 Integer.MAX_VALUE로 처리해서 최소값 계산에 영향을 주지 않도록
		}
		if (curr.left == null && curr.right == null) { // 리프 노드에 도달하면 1을 반환
			return 1;
		}

		int left = dfs(curr.left, depth + 1);
		int right = dfs(curr.right, depth + 1);

		return Math.min(left, right) + 1;
	}

}
