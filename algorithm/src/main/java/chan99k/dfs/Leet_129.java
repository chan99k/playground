package chan99k.dfs;
// 트리문제를 풀때는 recursion 으로 푸는 것을 권장 -> 같은 구조의 반복이기 때문 : skecth.io
// 서브문제로 분리해서 반복하기 -> 1. 하위 노드가 있는가, 2. 자식노드한테 뭘 리턴받아야 하는지 3. 나는 뭘 계산해서 위로 넘겨줄것인지
// 리프 노드 입장에서 생각해보면 편하다.
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

		int pathSum = currentSum * 10 + node.val;

		if (node.left == null && node.right == null) {
			sum += pathSum;
			return;
		}

		// return dfs(node.left,pathSum) + dfs(node.right, pathSum);
		dfs(node.left, pathSum);
		dfs(node.right, pathSum);
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
