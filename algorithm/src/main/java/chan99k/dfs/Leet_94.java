package chan99k.dfs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ClassEscapesDefinedScope")
public class Leet_94 {

	public List<Integer> inorderTraversalWithStack(TreeNode root) {
		List<Integer> result = new ArrayList<>();
		Deque<TreeNode> stack = new ArrayDeque<>();

		TreeNode curr = root;

		while (curr != null || !stack.isEmpty()) {
			// 왼쪽 노드들을 스택에 쭉 쌓는다
			while (curr != null) {
				stack.push(curr);
				curr = curr.left;
			}

			// 왼쪽이 null이면 꺼내서 현재 노드 처리
			curr = stack.pop();
			result.add(curr.val);

			// 오른쪽으로 이동
			curr = curr.right;
		}

		return result;
	}

	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> result = new LinkedList<>();

		traverse(root.left, result);
		result.add(root.val);
		traverse(root.right, result);

		return result;
	}

	private void traverse(TreeNode curr, List<Integer> result) {
		if (curr == null) {
			return;
		}
		traverse(curr.left, result);
		result.add(curr.val);
		traverse(curr.right, result);
	}

	private record TreeNode(int val, TreeNode left, TreeNode right) {
	}
}
