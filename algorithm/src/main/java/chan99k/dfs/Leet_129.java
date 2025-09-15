package chan99k.dfs;

public class Leet_129 {

	private int sum = 0;

	public int sumNumbers(TreeNode root) {
		if (root == null) {
			return 0;
		}

		dfs(root, 0);

		return sum;
	}

	private void dfs(TreeNode node, int currentSum) {
		if (node == null) {
			return;
		}

		int nextSum = currentSum * 10 + node.val;

		if (node.left == null && node.right == null) {
			sum += nextSum;
			return;
		}

		dfs(node.left, nextSum);
		dfs(node.right, nextSum);
	}

	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}
