package chan99k.dfs;

public class Leet_112 {

	/**
	 * @param root 트리의 루트 노드
	 * @param targetSum 찾아야 하는 합계 수
	 * @return root&#xBD80;&#xD130; leet&#xAE4C;&#xC9C0; &#xC21C;&#xD68C;&#xD558;&#xBA70; &#xB354;&#xD558;&#xC5EC; targetSum&#xC744; &#xB9CC;&#xC871;&#xD558;&#xB294; &#xACBD;&#xC6B0;&#xC758; &#xC218;&#xAC00; &#xC788;&#xC73C;&#xBA74; &#xCC38;&#xC744; &#xB9AC;&#xD134;
	 */
	@SuppressWarnings({"ClassEscapesDefinedScope"})
	public boolean hasPathSum(TreeNode root, int targetSum) {
		if (root == null) return false;

		targetSum -= root.value;

		if (targetSum == 0 && root.left == null && root.right == null) { // 합이 일치하고 리프 노드라면,
			return true;
		}

		return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
	}

	private record TreeNode(
		int value,
		TreeNode left,
		TreeNode right
	) {
	}

}
