package chan99k.trees;

public class Leet_124 {
	private int maxResult = Integer.MIN_VALUE;

	// 부모에게 리턴할 때는 좌우 경로 중 하나를 반환하고, 최대값 계산을 위한 변수를 따로 관리하기
	public int maxPathSum(TreeNode root) {
		subPathSum(root);

		return maxResult;
	}

	private int subPathSum(TreeNode node) {
		if (node == null)
			return 0;

		int left = Math.max(0, subPathSum(node.left));
		int right = Math.max(0, subPathSum(node.right)); // 음수 경로는 끊어주기

		int localMax = node.val + left + right;
		maxResult = Math.max(maxResult, localMax);

		return node.val + Math.max(left, right); // 부모에게는 단일 경로만 반환해주기
	}

	private record TreeNode(TreeNode left, TreeNode right, int val) {

	}
}
