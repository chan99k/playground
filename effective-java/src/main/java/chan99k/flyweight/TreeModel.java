package chan99k.flyweight;

import java.awt.*;

import javax.swing.text.Position;

/**
 * ConcreteFlyweight: 본질적(공유) 상태를 저장한다.
 */
public class TreeModel implements TreeType {
	// === 본질적 상태 (Intrinsic State) ===
	private final Mesh mesh;       // 3D 모델
	private final Texture bark;    // 껍질 텍스처
	private final Texture leaves;  // 잎사귀 텍스처

	public TreeModel() {
		this.mesh = new Mesh("standard_tree.obj");
		this.bark = new Texture("bark.png");
		this.leaves = new Texture("leaves.png");
		System.out.println("TreeModel 생성 완료 (메모리 할당)");
	}

	@Override
	public void draw(Canvas canvas, Position position, double height, double thickness) {
		// 외재적 상태를 받아 나무를 그리는 로직
		System.out.println("좌표 " + position + "에 나무를 그립니다.");

	}

	private record Mesh(String value) {
	}

	private record Texture(String value) {

	}
}
