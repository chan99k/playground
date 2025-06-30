package chan99k.bfs;

import java.util.Deque;
import java.util.LinkedList;

public class Leet_111 {
	private record TreeNode(int val, TreeNode left, TreeNode right) {
	}

	private record Pair(TreeNode node, Integer depth) {
	}

	@SuppressWarnings("ClassEscapesDefinedScope")
	public int minDepth(TreeNode root) {
		if (root == null)
			return 0;

		Deque<Pair> queue = new LinkedList<>();
		queue.offer(new Pair(root, 1));

		while (!queue.isEmpty()) {
			var curr = queue.poll();
			TreeNode node = curr.node;
			Integer depth = curr.depth;

			if (node.left == null && node.right == null) {
				return depth;
			}
			if (node.left != null) {
				queue.offer(new Pair(node.left, depth + 1));
			}
			if (node.right != null) {
				queue.offer(new Pair(node.right, depth + 1));
			}
		}
		return 0;
	}

}
