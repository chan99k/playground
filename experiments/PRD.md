# 프로젝트: 구구킴 기술 블로그 및 리뷰 플랫폼 - 제품 요구사항 문서 (PRD)

**버전: 1.4**
**문서 목적:** 본 문서는 프로젝트의 목표, 기능, 데이터 모델, 기술적 설계를 상세히 정의하여 개발의 방향성과 일관성을 확보하는 것을 목적으로 한다.

---

## 1. 개요 (Overview)

- **프로젝트명:** 구구킴 기술 블로그 및 리뷰 플랫폼
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

### 4.1. 신뢰도 점수 시스템 (`TrustScore` System)

- **목표:** 커뮤니티의 평가를 기반으로 콘텐츠의 신뢰도를 1.0 ~ 5.0 사이의 점수로 계량화한다.
- **계산 대상:** `Story`, `RestaurantReview`
- **계산 시점:** 실시간이 아닌, **주기적인 배치(Batch) 작업**을 통해 계산한다. (예: 매일 자정)
- **계산 알고리즘:**
  1.  **기본 점수 (가중 평균):** 사용자들이 제출한 `Rating`(평점)을 역할별 가중치를 적용하여 평균을 계산한다.
    - **가중치 (설정으로 관리):**
      - `MEMBER`: 2.0
      - `GUEST`: 1.0
      - `AMBASSADOR`: 0.0 (작성자 본인의 평가는 점수 계산에서 제외)
    - **공식:** `BaseScore = Σ(rating * weight) / Σ(weight)`
  2.  **비평 보너스 (Critique Bonus):**
    - `Story` 콘텐츠에 한해, 연결된 `Critique`의 개수에 비례하여 보너스 점수를 부여한다.
    - **공식:** `CritiqueBonus = (Critique 개수) * (보너스 팩터)`
    - **보너스 팩터 (설정으로 관리):** 0.05
  3.  **최종 점수:**
    - **공식:** `FinalTrustScore = BaseScore + CritiqueBonus`
    - **제약:** 계산된 최종 점수가 5.0을 초과할 경우, 5.0으로 조정한다.

---

## 5. 기술 아키텍처 및 설계 원칙 (Technical Architecture & Design Principles)

### 5.1. API 설계 (API Design)

- **기술 결정 보류:** API 기술 스택으로 **RESTful API**와 **GraphQL** 중 어떤 것을 채택할지는 **보류**한다.
- **결정 기준:** 향후 프론트엔드 요구사항, 데이터 통신 효율성, 개발 생산성을 종합적으로 고려하여 최종 결정한다.

### 5.2. 인증 및 인가 (Authentication & Authorization)

- **핵심 기술:** **JWT(JSON Web Token)** 기반의 Stateless 인증 시스템을 채택한다. (Access/Refresh Token 방식)
- **API 형식:** 인증/인가 관련 엔드포인트(로그인, 토큰 재발급 등)는 **OAuth2 표준 호환성**을 위해 **RESTful API 형식**을 따른다.
- **비밀번호 저장:** 사용자의 비밀번호는 **BCrypt**와 같은 강력한 단방향 해시 알고리즘을 사용하여 암호화 후 저장한다.
- **구현 추상화:** 세부적인 구현(Spring Security 설정 등)은 추후 구체화한다.

### 5.3. 외부 서비스 연동 (External Service Integration)

- **기본 원칙:** 특정 벤더에 대한 종속성을 피하기 위해, 모든 외부 서비스 연동은 **인터페이스(Interface)를 통한 추상화 계층**을 도입한다.

- **5.3.1. 이미지 저장소 (Image Storage)**
  - **인터페이스:** `ImageStorageService`
  - **개발/로컬 환경:** S3 호환 API를 제공하는 오픈소스 **MinIO**를 사용하여 개발 효율성을 극대화하고 비용을 절감한다.
  - **프로덕션 환경:** 추상화 계층 덕분에 향후 AWS S3, Google Cloud Storage 등 주요 클라우드 스토리지로 손쉽게 전환할 수 있다. (Spring Profile을 통한 전환)

- **5.3.2. 지도 서비스 (Map Service)**
  - **인터페이스:** `MapService`
  - **표준 데이터 객체(DTO):** `Place` 객체를 정의하여, 여러 지도 서비스(Naver, Kakao 등)의 각기 다른 응답 형식을 표준화한다.
  - **데이터 저장 방식:** `RestaurantReview` 모델에는 주소, 좌표 등의 모든 정보를 저장하는 대신, 지도 서비스의 고유 식별자인 **`mapPlaceId`** 만을 저장한다. 이를 통해 데이터 중복을 방지하고 정보의 최신성을 보장한다.

---

## Appendix A. 아키텍처에 대한 생각

### 도메인 모델의 순수성과 영속성 매핑 전략

Ports and Adapters 아키텍처의 핵심은 **도메인(Domain) 계층이 외부 기술에 의존하지 않고 순수한 비즈니스 로직을 유지**하는 것입니다. 이 원칙을 어떻게 실용적으로 적용할지에 대한 논의와 결정을 아래에 기록합니다.

#### 1. 최종 결정: 생산성과 유연성을 고려한 하이브리드 전략

우리는 아키텍처의 순수성을 맹목적으로 추구하기보다, **개발 생산성과 장기적인 유연성 사이의 균형**을 맞추는 실용적인 하이브리드 전략을 채택합니다.

- **기본 원칙:** **생산성을 우선**하되, 기술적 특성으로 인해 분리가 반드시 필요한 경우에만 엄격한 분리 전략을 사용한다.

#### 2. 기본 영속성 계층 (JPA): 도메인 엔티티 직접 사용

- **전략:** 주 영속성 기술인 JPA의 경우, **도메인 엔티티를 영속성 엔티티로 직접 사용**합니다. 즉, `@Entity`와 같은 JPA 어노테이션을 도메인 모델 클래스에 직접 적용합니다.
- **근거:**
  - **생산성:** 이 방식은 별도의 영속성 모델과 매퍼를 작성하는 데 드는 비용을 줄여 개발 속도를 크게 향상시킵니다. 이는 Spring Data JPA를 사용하는 가장 일반적이고 효율적인 방법입니다.
  - **실용적 타협:** 어노테이션은 코드의 동작 자체를 바꾸는 것이 아닌, 프레임워크가 해석하는 **메타데이터**입니다. 도메인 객체 내의 비즈니스 로직은 여전히 순수하게 유지되므로, 이는 허용 가능한 실용적인 타협점으로 간주합니다.

#### 3. 보조/특수 영속성 계층 (MongoDB 등): 영속성 모델 분리

- **전략:** `orm.xml`과 같은 외부 메타데이터 분리 기능을 지원하지 않는 기술(예: MongoDB)을 사용할 경우, **별도의 영속성 모델과 Mapper를 사용하는 엄격한 분리 전략**을 적용합니다.
- **근거:**
  - **근본적인 패러다임 차이:** 관계형 DB와 도큐먼트 DB는 데이터 모델링 방식이 근본적으로 다릅니다. 각 기술에 최적화된 모델을 `adapter/persistence` 계층에 별도로 둠으로써, 도메인 모델의 복잡성을 방지하고 각 기술의 장점을 최대한 활용할 수 있습니다.
  - **명확한 책임 분리:** 이 방식은 특정 기술에 대한 모든 종속성을 해당 어댑터 내로 완벽하게 캡슐화하여, 도메인 계층의 순수성을 보장합니다.
  - **유연성 및 유지보수성:** 향후 MongoDB를 다른 기술로 교체해야 할 경우, 해당 어댑터만 수정하거나 교체하면 되므로 **도메인 및 애플리케이션 계층은 전혀 영향을 받지 않습니다.**

#### 4. 요약: 우리의 영속성 아키텍처 원칙

1.  **생산성 우선의 실용주의:** 우리는 아키텍처 원칙을 존중하되, 개발 생산성을 저해하는 과도한 설계는 지양한다.
2.  **JPA는 직접 매핑:** 주 데이터베이스 기술인 JPA는 도메인 엔티티에 어노테이션을 직접 사용하여 생산성을 극대화한다.
3.  **그 외 기술은 분리:** 패러다임이 다르거나 외부 설정 분리를 지원하지 않는 보조 기술(MongoDB 등)은 영속성 모델과 매퍼를 통해 명확히 분리하여 도메인을 보호하고 유연성을 확보한다.

---

## Appendix B. ID 생성 전략: UUID v7 채택

### 1. 배경 (Background)

- 핵심 도메인 엔티티(`User`, `Story` 등)의 Primary Key로 UUID를 사용하기로 결정했습니다.
- 표준 `java.util.UUID.randomUUID()`로 생성되는 UUID v4는 무작위성에 기반하여, 생성 시간 순서로 정렬되지 않습니다.
- 이는 데이터베이스에서 PK 인덱스 페이지의 분할(page split)을 유발하여 대량의 데이터 입력 시 쓰기 성능 저하의 원인이 될 수 있습니다.

### 2. 결정 (Decision)

- 데이터베이스 인덱스 성능 최적화를 위해, 시간 순으로 정렬 가능한 **UUID v7**을 ID 생성 표준으로 채택합니다.
- UUID v7은 ID의 앞부분에 48비트의 Unix 타임스탬프를 포함하므로, 생성된 ID가 시간 순서를 따릅니다. 이는 데이터가 삽입될 때 인덱스의 특정 영역에 순차적으로 쌓이게 하여, 불필요한 인덱스 재구성을 최소화하고 쓰기 성능을 향상시킵니다.

### 3. 구현 전략 (Implementation Strategy)

1.  **`Uuid7Generator` 유틸리티 구현:** IETF 표준 초안을 준수하는 `Uuid7Generator` 클래스를 프로젝트의 유틸리티 패키지(예: `util`)에 구현합니다. 이 클래스는 정적 `generate()` 메소드를 통해 UUID v7 객체를 생성하는 책임을 가집니다.
2.  **서비스 계층에서의 적용:** 도메인 엔티티가 새로 생성되는 서비스 계층(예: `UserService`, `StoryService`)에서 `Uuid7Generator.generate()`를 호출하여 ID를 생성하고, 엔티티에 할당합니다.
3.  **도메인 모델의 역할 유지:** 도메인 클래스 자체는 ID의 타입으로 `java.util.UUID`를 그대로 유지합니다. ID를 '어떻게' 생성할 것인지에 대한 구체적인 구현은 도메인 외부(서비스 계층, 유틸리티)에 위치시켜 도메인의 순수성을 유지합니다.

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

- **`AbstractWriteOnceEntity` (불변 엔티티용):**
  - **필드:** `createdAt`, `createdBy`
  - **상속 대상:** `Rating`, `Quote` 와 같이 생성 후 내용이 변경되지 않는 엔티티.

### 3. 트레이드오프 (Trade-offs)

- **장점 (Pros):**
  - **의도의 명확성:** `extends AbstractWriteOnceEntity` 선언만으로 해당 엔티티가 불변임을 즉시 알 수 있다.
  - **설계적 안정성:** 수정 관련 필드가 없어 실수로 수정 로직을 추가하는 것을 방지한다.
  - **스키마 최적화:** 데이터베이스에 불필요한 `last_modified` 관련 컬럼이 생성되지 않는다.

- **단점 (Cons):**
  - **계층 구조 복잡성 증가:** 관리해야 할 추상 클래스가 늘어나 프로젝트의 복잡도가 약간 증가한다.

### 4. 성능 및 캐시 전략과의 연관성

불변 엔티티는 **JPA 2차 캐시(Second-Level Cache)에 가장 이상적인 후보**이다. 데이터가 절대 변하지 않으므로 캐시를 만료시키거나 갱신할 필요가 없어, 데이터베이스 I/O를 획기적으로 줄이고 읽기 성능을 극대화할 수 있다. 이는 `Rating`이나 `Quote`처럼 조회가 빈번한 데이터에 매우 효과적인 최적화 전략이 될 수 있다.