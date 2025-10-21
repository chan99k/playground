package chan99k.tree;

// 약간의 아이디어가 필요한 문제라고 하셨는데, 이걸로 풀림...? 뭔가 다른 비밀이 있나?
public class Leet_222 {
	private class Solution {
		public int countNodes(TreeNode root) {
			if (root == null) {
				return 0;
			}

			return 1 + countNodes(root.left) + countNodes(root.right);
		}
	}

	private record TreeNode(TreeNode left, TreeNode right, int value) {
	}
}
