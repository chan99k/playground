package chan99k.experiments.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.util.Assert;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "restaurant_reviews")
public class RestaurantReview extends Content {

	@Column(nullable = false, length = 100)
	private String restaurantName;

	@Column(nullable = false)
	private String mapPlaceId;

	@Column(length = 500)
	private String locationMemo;

	@Lob
	@Column(nullable = false)
	private String reviewText;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "restaurant_review_photos", joinColumns = @JoinColumn(name = "review_id"))
	@Column(name = "photo_url")
	private List<String> photoUrls = new ArrayList<>();

	protected RestaurantReview() {
		super();
	}

	// --- Business Methods ---

	/**
	 * 새로운 RestaurantReview를 생성합니다.
	 * 필수 값(식당 이름, 지도 ID, 리뷰 본문)들이 누락되지 않도록 생성자에서 강제합니다.
	 *
	 * @param id             리뷰의 고유 ID
	 * @param author         작성자
	 * @param restaurantName 식당 이름
	 * @param mapPlaceId     지도 서비스의 장소 ID
	 * @param reviewText     리뷰 본문
	 */
	public RestaurantReview(UUID id, User author, String restaurantName, String mapPlaceId, String reviewText) {
		super(id, author);
		Assert.hasText(restaurantName, "Restaurant name must not be empty");
		Assert.hasText(mapPlaceId, "Map place ID must not be empty");
		Assert.hasText(reviewText, "Review text must not be empty");

		this.restaurantName = restaurantName;
		this.mapPlaceId = mapPlaceId;
		this.reviewText = reviewText;
	}

	/**
	 * RestaurantReview의 내용을 수정합니다.
	 * 이 메서드는 하나의 완전한 비즈니스 트랜잭션을 나타냅니다.
	 *
	 * @param newRestaurantName 새로운 식당 이름
	 * @param newLocationMemo   새로운 위치 메모
	 * @param newReviewText     새로운 리뷰 본문
	 * @param newPhotoUrls      새로운 사진 URL 목록
	 */
	public void update(String newRestaurantName, String newLocationMemo, String newReviewText,
		List<String> newPhotoUrls) {
		Assert.hasText(newRestaurantName, "Restaurant name must not be empty");
		Assert.hasText(newReviewText, "Review text must not be empty");

		this.restaurantName = newRestaurantName;
		this.locationMemo = newLocationMemo;
		this.reviewText = newReviewText;
		this.photoUrls = newPhotoUrls != null ? newPhotoUrls : new ArrayList<>();
	}
}