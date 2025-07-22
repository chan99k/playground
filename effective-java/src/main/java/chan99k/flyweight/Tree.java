package chan99k.flyweight;

import java.awt.*;

import javax.swing.text.Position;

public class Tree {
	// === 외재적 상태 (Extrinsic State) ===
	private final Position position;
	private final double height;
	private final double thickness;

	// === 공유되는 Flyweight 객체 ===
	private final TreeType type;

	public Tree(Position position, double height, double thickness, TreeType type) {
		this.position = position;
		this.height = height;
		this.thickness = thickness;
		this.type = type;
	}

	public void draw(Canvas canvas) {
		// 자신의 외재적 상태를 플라이웨이트 객체에 전달하여 작업을 위임한다.
		type.draw(canvas, position, height, thickness);
	}
}