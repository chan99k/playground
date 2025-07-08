package chan99k.experiments.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant_reviews")
public class RestaurantReview extends Content { // Content 상속

	// id, author, createdAt, updatedAt 필드는 부모 클래스로 이동하여 제거

	@Column(nullable = false)
	private String restaurantName;

	@Column(nullable = false)
	private String mapPlaceId;

	private String locationMemo;

	@Lob
	@Column(nullable = false)
	private String reviewText;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "restaurant_review_photos", joinColumns = @JoinColumn(name = "restaurant_review_id"))
	@Column(name = "photo_url", nullable = false)
	private List<String> photoUrls;

	protected RestaurantReview() {
		super();
	}
}