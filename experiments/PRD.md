# CMS : 구구킴 블로그 및 리뷰 플랫폼 - 제품 요구사항 문서 (PRD)

**버전: 1.4**
**문서 목적:** 본 문서는 프로젝트의 목표, 기능, 데이터 모델, 기술적 설계를 상세히 정의하여 개발의 방향성과 일관성을 확보하는 것을 목적으로 한다.

---

## 1. 개요 (Overview)

- **프로젝트명:** 구구킴 블로그 및 리뷰 플랫폼
- **핵심 가치:** '구구킴'이 생성하는 전문적인 콘텐츠(기술 블로그, 식당 리뷰)에 커뮤니티의 집단지성을 결합하여, 콘텐츠의 **신뢰도(Trust)**를 객관적인 지표로 제공하는 것을 목표로 한다.

---

## 2. 사용자 역할 (User Roles)

시스템에는 세 가지 유형의 사용자 역할이 존재한다.

| 역할 | 설명 | 주요 권한 |
| :--- | :--- | :--- |
| **`AMBASSADOR`** | 시스템의 주 콘텐츠 생성자. | 콘텐츠(Story, RestaurantReview) 생성/수정/삭제. |
| **`MEMBER`** | 회원가입을 통해 인증된 사용자. | 콘텐츠 평가, 비평, 댓글 등 모든 상호작용 가능. |
| **`GUEST`** | 비로그인 익명 사용자. | 콘텐츠 조회, 일부 제한된 상호작용(예: 댓글) 가능. |

---

## 3. 데이터 모델 상세 정의 (Detailed Data Model Definition)

시스템을 구성하는 핵심 데이터 모델과 그 필드는 다음과 같다.

**참고:** 모든 엔티티는 공통적으로 생성/수정 시간 및 주체와 같은 메타데이터 필드(`createdAt`, `lastModifiedAt`, `createdBy`, `lastModifiedBy`)를 추상 클래스로부터 상속받아 관리한다.

### 3.1. User
- **설명:** 시스템 사용자.
- **ID 타입:** `UUID`
- **필드:**
  - `id (UUID)`: Primary Key.
  - `name (String)`: 사용자 이름.
  - `email (String)`: 로그인 ID로 사용되는 이메일 (Unique).
  - `password (String)`: BCrypt로 해시된 비밀번호.
  - `role (UserRole)`: 사용자의 역할 (`AMBASSADOR`, `MEMBER`, `GUEST`).

### 3.2. Story
- **설명:** 기술 블로그 포스트. 수정 가능한 "살아있는 문서" 타입의 콘텐츠.
- **ID 타입:** `UUID`
- **필드:**
  - `id (UUID)`: Primary Key.
  - `author (User)`: 작성자 객체 참조 (FK: User.id).
  - `title (String)`: 글 제목.
  - `summary (String)`: 목록에 노출될 짧은 요약.
  - `markdownContent (String)`: 본문 내용 (Markdown 형식).
  - `thumbnailUrl (String)`: 대표 이미지 URL.

### 3.3. RestaurantReview
- **설명:** 식당 리뷰 콘텐츠. 수정 가능한 "살아있는 문서" 타입의 콘텐츠.
- **ID 타입:** `UUID`
- **필드:**
  - `id (UUID)`: Primary Key.
  - `author (User)`: 작성자 객체 참조 (FK: User.id).
  - `restaurantName (String)`: 식당 이름.
  - `mapPlaceId (String)`: 연동된 지도 서비스의 고유 장소 ID.
  - `locationMemo (String)`: 사용자가 직접 작성한 위치 관련 메모.
  - `reviewText (String)`: 리뷰 본문.
  - `photoUrls (List<String>)`: 업로드된 사진들의 URL 목록.

### 3.4. Comment
- **설명:** `Story` 또는 `RestaurantReview`에 대한 일반 댓글.
- **ID 타입:** `Long`
- **필드:**
  - `id (Long)`: Primary Key.
  - `content (Content)`: 상위 콘텐츠 객체 참조.
  - `author (User)`: 작성자 객체 참조 (FK: User.id).
  - `text (String)`: 댓글 내용.

### 3.5. Quote
- **설명:** `Story` 본문의 특정 텍스트를 인용한 블록. 생성 후 수정되지 않는 "사건의 기록" 타입.
- **ID 타입:** `Long`
- **필드:**
  - `id (Long)`: Primary Key.
  - `story (Story)`: 인용의 출처가 된 Story 객체 참조.
  - `author (User)`: 인용을 생성한 사용자 객체 참조.
  - `originalText (String)`: 인용된 원본 텍스트.
  - `textFragmentUrl (String)`: 해당 텍스트 위치로 바로 이동할 수 있는 URL Fragment.

### 3.6. Critique
- **설명:** `Quote`에 대한 사용자의 비평 또는 의견.
- **ID 타입:** `Long`
- **필드:**
  - `id (Long)`: Primary Key.
  - `quote (Quote)`: 비평의 대상이 된 Quote 객체 참조.
  - `author (User)`: 비평을 작성한 사용자 객체 참조.
  - `critiqueText (String)`: 비평 내용.

### 3.7. Rating
- **설명:** `Story` 또는 `RestaurantReview`에 대한 사용자의 평점. 생성 후 수정되지 않는 "사건의 기록" 타입.
- **ID 타입:** `Long`
- **필드:**
  - `id (Long)`: Primary Key.
  - `content (Content)`: 평점 대상 콘텐츠 객체 참조.
  - `user (User)`: 평점을 남긴 사용자 객체 참조.
  - `score (double)`: 사용자가 부여한 점수 (1.0 ~ 5.0).

### 3.8. TrustScore
- **설명:** 콘텐츠의 계산된 신뢰도 점수. 시스템에 의해 생성 및 관리됨.
- **ID 타입:** `UUID`
- **필드:**
  - `id (UUID)`: Primary Key.
  - `content (Content)`: 점수 계산 대상 콘텐츠 객체 참조 (Unique).
  - `score (double)`: 계산된 최종 신뢰도 점수.
  - `calculatedAt (Instant)`: 점수가 마지막으로 계산된 시점 (UTC).
  - `algorithmVersion (String)`: 점수 계산에 사용된 알고리즘 버전.

---

## 4. 핵심 기능 상세 명세 (Core Feature Specifications)
(이전 버전과 동일)

---

## 5. 기술 아키텍처 및 설계 원칙 (Technical Architecture & Design Principles)
(이전 버전과 동일)

---

## Appendix A. 아키텍처에 대한 생각
(이전 버전과 동일)

---

## Appendix B. ID 생성 전략: UUID v7 채택
(이전 버전과 동일)

---

## Appendix C. 엔티티 불변성(Immutability) 설계 원칙

### 1. 원칙 (Principle)

모든 엔티티를 동일하게 취급하지 않고, 비즈니스적 의미에 따라 **"살아있는 문서(Living Document)"**와 **"사건의 기록(Record of an Event)"**으로 구분하여 설계한다.

- **살아있는 문서 (Mutable):** 생성 후에도 내용이 수정될 수 있는 엔티티. (예: `Story`, `Critique`)
- **사건의 기록 (Immutable):** 한번 생성되면 그 내용이 절대 변하지 않는, 특정 시점의 사실을 기록한 엔티티. (예: `Rating`, `Quote`)

이 원칙은 도메인의 의도를 코드 수준에서 명확히 표현하여 시스템의 안정성과 예측 가능성을 높이는 것을 목표로 한다.

### 2. 구현 전략 (Implementation Strategy)

엔티티의 성격에 따라 상속받는 추상 클래스를 분리한다.

- **`AbstractEntity` (수정 가능 엔티티용):**
  - **필드:** `createdAt`, `lastModifiedAt`, `createdBy`, `lastModifiedBy`
  - **상속 대상:** `Story`, `RestaurantReview`, `Comment`, `Critique` 등 수정이 필요한 모든 엔티티.

- **`AbstractWriteOnceeEntity` (불변 엔티티용):**
  - **필드:** `createdAt`, `createdBy`
  - **상속 대상:** `Rating`, `Quote` 와 같이 생성 후 내용이 변경되지 않는 엔티티.

### 3. 트레이드오프 (Trade-offs)

- **장점 (Pros):**
  - **의도의 명확성:** `extends AbstractWriteOnceeEntity` 선언만으로 해당 엔티티가 불변임을 즉시 알 수 있다.
  - **설계적 안정성:** 수정 관련 필드가 없어 실수로 수정 로직을 추가하는 것을 방지한다.
  - **스키마 최적화:** 데이터베이스에 불필요한 `last_modified` 관련 컬럼이 생성되지 않는다.

- **단점 (Cons):**
  - **계층 구조 복잡성 증가:** 관리해야 할 추상 클래스가 늘어나 프로젝트의 복잡도가 약간 증가한다.

### 4. 성능 및 캐시 전략과의 연관성

불변 엔티티는 **JPA 2차 캐시(Second-Level Cache)에 가장 이상적인 후보**이다. 데이터가 절대 변하지 않으므로 캐시를 만료시키거나 갱신할 필요가 없어, 데이터베이스 I/O를 획기적으로 줄이고 읽기 성능을 극대화할 수 있다. 이는 `Rating`이나 `Quote`처럼 조회가 빈번한 데이터에 매우 효과적인 최적화 전략이 될 수 있다.