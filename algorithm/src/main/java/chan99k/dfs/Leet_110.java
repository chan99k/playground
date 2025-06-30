package chan99k.dfs;

/**
 * 높이 균형 트리 : 이진 트리의 모든 노드에 대해, 왼쪽 서브트리와 오른쪽 서브트리의 높이 차가 1 이하여야 한다.
 * A height-balanced binary tree is a binary tree
 * 		in which the depth of the two subtrees of every node
 * 				never differs by more than one.
 */
public class Leet_110 {

	@SuppressWarnings("ClassEscapesDefinedScope")
	public boolean isBalanced(TreeNode root) {
		return dfs(root) != -1;
	}

	private int dfs(TreeNode curr) {
		if (curr == null) {
			return 0;
		}
		// 왼쪽, 오른쪽 서브트리의 높이를 재고
		int left = dfs(curr.left);
		int right = dfs(curr.right);
		if (left == -1 || right == -1 || Math.abs(left - right) > 1) { // 양쪽 서브트리의 높이 차이가 1보다 크면 -1을 리턴해서 위로 전파
			return -1;
		}

		return Math.max(left, right) + 1; // 차이가 크지 않다면 해당 서브트리에 있는 리프 노드들의 최대 깊이(높이)를 반환
	}

	private record TreeNode(int val, TreeNode left, TreeNode right){}
}
