# JPA N+1 실험 w. Sakila Dataset

## Introduction
JPA에서 N+1 문제가 발생한다는 것은 누구나 알 만큼 널리 알려져 있습니다. 해당 문제에 대한 해결책 또한 귀에 박히도록 들어 알고는 있었는데요, 다만 그런 이론적 해결책들이 실제로는 어떤 성능적 차이를 보이는지 궁금해졌습니다. 그래서 이번 글에서는 MySQL 에서 제공하는 오픈소스 데이터셋인 Sakila 를 활용하여 실험을 해 보려고 합니다.

### 실험 환경
- **데이터베이스**: MySQL 8.0 (Docker)
- **프레임워크**: Spring Boot 3.5.5, Hibernate 6.6.26
- **Java**: 21
- **테스트 데이터**: FilmActor 엔티티 (총 5,462개 레코드)

#### 대상 엔티티 관계

```
FilmActor (5,462개)
├── Film (N:1) - 약 1,000개 고유 영화
└── Actor (N:1) - 약 200개 고유 배우
```

#### 테스트 시나리오
1. **N+1 문제 재현**: 순수한 지연 로딩으로 전체 데이터 조회
2. **EntityGraph**: 선언적 즉시 로딩 (내부적으론 Fetch Join)
3. **JPQL Fetch Join**: 명시적 JOIN 쿼리
4. **BatchSize**: 배치 사이즈 설정별 성능 비교

---

먼저 이번 실험에 사용한 FilmActor 와 그 연관 엔티티는 다음과 같습니다.

#### FilmActor.java
```java
@Table(name = "film_actor")
public class FilmActor {
 @EmbeddedId
 private FilmActorId id;

 @MapsId("actorId")
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 @JoinColumn(name = "actor_id", nullable = false)
 private Actor actor;

 @MapsId("filmId")
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 @JoinColumn(name = "film_id", nullable = false)
 private Film film;

 @ColumnDefault("CURRENT_TIMESTAMP")
 @Column(name = "last_update", nullable = false)
 private Instant lastUpdate;

}
```

#### Actor.java
```java
@Entity
@Table(name = "actor")
public class Actor {
 @Id
 @Column(name = "actor_id", columnDefinition = "smallint UNSIGNED not null")
 private Integer id;

 @Column(name = "first_name", nullable = false, length = 45)
 private String firstName;

 @Column(name = "last_name", nullable = false, length = 45)
 private String lastName;
}
```

#### Film.java
```java
@Entity
@Table(name = "film")
public class Film {
 @Id
 @Column(name = "film_id", columnDefinition = "smallint UNSIGNED not null")
 private Integer id;

 @Column(name = "title", nullable = false, length = 128)
 private String title;
}
```


---

## 1. N + 1 재현하기
### 문제 상황 코드
먼저, FilmActor 를 데이터베이스에 쿼리한 뒤, 지연 로딩이 설정된 영화 및 영화 배우 정보를 함께 조회하는 쿼리를 작성해보겠습니다.

#### 코드 예시
```java
List<FilmActorResponse> result = filmActors.stream()
 .map(filmActor -> new FilmActorResponse(
  filmActor.getFilm().getId(),
  filmActor.getFilm().getTitle(),        // N+1 발생!
  filmActor.getActor().getId(),
  filmActor.getActor().getFirstName(),   // N+1 발생!
  filmActor.getActor().getLastName()
 ))
 .toList();
```

### 성능 측정 결과
**시도 1 (콜드 스타트):**
```log
N+1 처리 시간: 1028ms, 결과 개수: 5462
```
```log
2025-08-27T23:06:30.560+09:00  INFO 72639 --- [sakila] [nio-8080-exec-2] i.StatisticalLoggingSessionEventListener : Session Metrics {
    2556375 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    42935801 nanoseconds spent preparing 1198 JDBC statements;
    720374978 nanoseconds spent executing 1198 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    83083 nanoseconds spent executing 1 pre-partial-flushes;
    4917 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

**시도 2:**
```log
N+1 처리 시간: 663ms, 결과 개수: 5462
```
```log
025-08-27T23:07:44.390+09:00  INFO 72639 --- [sakila] [nio-8080-exec-4] i.StatisticalLoggingSessionEventListener : Session Metrics {
    5188958 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    28479333 nanoseconds spent preparing 1198 JDBC statements;
    501947154 nanoseconds spent executing 1198 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    10209 nanoseconds spent executing 1 pre-partial-flushes;
    3042 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

**시도 3 :**
```log
N+1 처리 시간: 501ms, 결과 개수: 5462
```
```log
2025-08-27T23:08:34.114+09:00  INFO 72639 --- [sakila] [nio-8080-exec-8] i.StatisticalLoggingSessionEventListener : Session Metrics {
    1840917 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    25767832 nanoseconds spent preparing 1198 JDBC statements;
    381965621 nanoseconds spent executing 1198 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    15750 nanoseconds spent executing 1 pre-partial-flushes;
    2334 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

### Hibernate Session Metrics 분석
세 번의 실행 모두에서 공통적으로 **1,198개의 JDBC 쿼리**가 실행되었습니다
| 시도 | 총 처리시간 | JDBC 준비시간 | JDBC 실행시간 | 쿼리 수 |
|:-:|:-:|:-:|:-:|:-:|
| 1회차 | 1,028ms | 42.9ms | 720.4ms | 1,198개 |
| 2회차 | 663ms | 28.5ms | 501.9ms | 1,198개 |
| 3회차 | 501ms | 25.8ms | 382.0ms | 1,198개 |
### SQL 실행 로그 예시
실제로 실행되는 쿼리들을 살펴보면 다음과 같은 패턴을 확인할 수 있습니다:

```sql
-- 각 FilmActor마다 개별적으로 Film을 조회*
select
    f1_0.film_id,
    f1_0.description,
    f1_0.language_id,
    f1_0.last_update,
    f1_0.length,
    f1_0.original_language_id,
    f1_0.rating,
    f1_0.release_year,
    f1_0.rental_duration,
    f1_0.rental_rate,
    f1_0.replacement_cost,
    f1_0.special_features,
    f1_0.title 
from
    film f1_0 
where
    f1_0.film_id=? -- 개별 ID로 하나씩 조회*
```


#### 쿼리 구성 분석
1,198개 쿼리의 구성:
* **FilmActor 전체 조회**: 1개
* **개별 Film 조회**: ~1,000개 (중복 제거된 고유 영화)
* **개별 Actor 조회**: ~200개 (중복 제거된 고유 배우)


이론적 최대치인 5,462개보다 훨씬 적은 쿼리가 실행된 이유는 JPA 영속성 컨텍스트의 1차 캐시 덕분에 동일한 Film이나 Actor는 추가 쿼리 없이 캐시에서 조회할 수 있었기 때문으로 확인됩니다. 아래와 같이 캐시 히트가 발생하는 로그를 확인할 수 있습니다.

```log
2025-08-27T23:26:25.725+09:00 TRACE 73278 --- [sakila] [nio-8080-exec-2] o.h.e.internal.DefaultLoadEventListener  : Loading entity: [chan99k.sakila.adapter.persistence.entities.Language#1]
2025-08-27T23:26:25.725+09:00 TRACE 73278 --- [sakila] [nio-8080-exec-2] o.h.e.internal.DefaultLoadEventListener  : Entity proxy found in session cache
```


---
## 2. EntityGraph 사용

### 구현 방법
`@EntityGraph` 어노테이션을 사용하여 를 사용하여 선언적으로 연관관계를 즉시 로딩할 수 있습니다.


```java
@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    
    @EntityGraph(attributePaths = {"film", "actor"})
    @Query("SELECT fa FROM FilmActor fa ORDER BY fa.film.id")
    List<FilmActor> findAllWithEntityGraph();
}
```

### 성능 측정 결과

**시도 1 (콜드 스타트):**
```log
EntityGraph 처리 시간: 186ms, 결과 개수: 5462
```
```log
2025-09-01T23:17:30.946+09:00  INFO 66628 --- [sakila] [nio-8080-exec-4] i.StatisticalLoggingSessionEventListener : Session Metrics {
      11074291 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      177917 nanoseconds spent preparing 1 JDBC statements;
      69583250 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      2625 nanoseconds spent executing 1 pre-partial-flushes;
      3583 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

**시도 2:**
```log
EntityGraph 처리 시간: 204ms, 결과 개수: 5462
```
```log
2025-09-01T23:18:03.126+09:00  INFO 66628 --- [sakila] [nio-8080-exec-5] i.StatisticalLoggingSessionEventListener : Session Metrics {
      6035625 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      350083 nanoseconds spent preparing 1 JDBC statements;
      74597208 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      19667 nanoseconds spent executing 1 pre-partial-flushes;
      3084 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

**시도 3 :**
```log
 EntityGraph 처리 시간: 171ms, 결과 개수: 5462
```
```log
2025-09-01T23:18:34.478+09:00  INFO 66628 --- [sakila] [io-8080-exec-10] i.StatisticalLoggingSessionEventListener : Session Metrics {
      7127625 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      226625 nanoseconds spent preparing 1 JDBC statements;
      73370875 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      7000 nanoseconds spent executing 1 pre-partial-flushes;
      4459 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

### Hibernate Session Metrics 분석

| 시도 | 총 처리시간 | JDBC 준비시간 | JDBC 실행시간 | 쿼리 수 |
|:-:|:-:|:-:|:-:|:-:|
| 1회차 | 186ms | 0.18ms | 69.6ms | 1개 |
| 2회차 | 204ms | 0.35ms | 74.6ms | 1개 |
| 3회차 | 171ms | 0.23ms | 73.4ms | 1개 |
| **평균** | **187ms** | **0.25ms** | **72.5ms** | **1개** |

### SQL 실행 로그 예시

**아래와 같이, EntityGraph는 단일 쿼리로 모든 연관 엔티티를 한번에 로딩합니다.**

```sql
-- EntityGraph가 생성하는 JOIN 쿼리
SELECT fa, f, a 
FROM FilmActor fa 
LEFT JOIN FETCH fa.film f 
LEFT JOIN FETCH fa.actor a 
ORDER BY fa.film.id
```

#### 쿼리 구성 분석

**EntityGraph의 핵심 특징:**
- **단일 쿼리**: 1개의 JOIN 쿼리로 모든 데이터 로딩
- **N+1 문제 완전 해결**: 추가 쿼리 없음
- **선언적 접근**: 어노테이션만으로 최적화 적용


---

## 3. Fetch Join 사용

### 구현 방법

JPQL에서 `JOIN FETCH`를 명시적으로 사용하여 연관 엔티티를 즉시 로딩합니다.

```java
@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    
    @Query("SELECT fa FROM FilmActor fa JOIN FETCH fa.film JOIN FETCH fa.actor ORDER BY fa.film.id")
    List<FilmActor> findAllWithFetchJoin();
}
```

### 성능 측정 결과

**시도 1 (콜드 스타트):**
```log
FetchJoin 처리 시간: 268ms, 결과 개수: 5462
```
```log
  2025-09-01T23:19:22.842+09:00  INFO 66688 --- [sakila] [nio-8080-exec-2] i.StatisticalLoggingSessionEventListener : Session Metrics {
      1550375 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      3293834 nanoseconds spent preparing 1 JDBC statements;
      63100167 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      68833 nanoseconds spent executing 1 pre-partial-flushes;
      4583 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

**시도 2:**
```log
FetchJoin 처리 시간: 207ms, 결과 개수: 5462
```
```log
  2025-09-01T23:19:49.783+09:00  INFO 66688 --- [sakila] [nio-8080-exec-4] i.StatisticalLoggingSessionEventListener : Session Metrics {
      3752500 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      704250 nanoseconds spent preparing 1 JDBC statements;
      78845833 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      17083 nanoseconds spent executing 1 pre-partial-flushes;
      5042 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

**시도 3 :**
```log
FetchJoin 처리 시간: 233ms, 결과 개수: 5462
```
```log
  2025-09-01T23:20:31.766+09:00  INFO 66688 --- [sakila] [nio-8080-exec-8] i.StatisticalLoggingSessionEventListener : Session Metrics {
      4761084 nanoseconds spent acquiring 1 JDBC connections;
      0 nanoseconds spent releasing 0 JDBC connections;
      354542 nanoseconds spent preparing 1 JDBC statements;
      69505709 nanoseconds spent executing 1 JDBC statements;
      0 nanoseconds spent executing 0 JDBC batches;
      0 nanoseconds spent performing 0 L2C puts;
      0 nanoseconds spent performing 0 L2C hits;
      0 nanoseconds spent performing 0 L2C misses;
      0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
      7333 nanoseconds spent executing 1 pre-partial-flushes;
      1334 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
  }
```

### Hibernate Session Metrics 분석

| 시도 | 총 처리시간 | JDBC 준비시간 | JDBC 실행시간 | 쿼리 수 |
|:-:|:-:|:-:|:-:|:-:|
| 1회차 | 268ms | 3.29ms | 63.1ms | 1개 |
| 2회차 | 207ms | 0.70ms | 78.8ms | 1개 |
| 3회차 | 233ms | 0.35ms | 69.5ms | 1개 |
| **평균** | **236ms** | **1.45ms** | **70.5ms** | **1개** |

### SQL 실행 로그 예시

**아래 SQL을 확인하면 명확히 알수 있듯이, Fetch Join도 단일 쿼리로 모든 연관 엔티티를 한번에 로딩합니다.**

```sql
-- Fetch Join이 생성하는 명시적 JOIN 쿼리
SELECT fa.actor_id, fa.film_id, fa.last_update,
       f.film_id, f.title, f.description, ...,
       a.actor_id, a.first_name, a.last_name, ...
FROM film_actor fa 
INNER JOIN film f ON fa.film_id = f.film_id 
INNER JOIN actor a ON fa.actor_id = a.actor_id 
ORDER BY f.film_id
```

#### 쿼리 구성 분석

**Fetch Join의 핵심 특징:**
- **단일 쿼리**: 1개의 명시적 JOIN 쿼리로 모든 데이터 로딩
- **N+1 문제 완전 해결**: 추가 쿼리 없음
- **명시적 제어**: JPQL에서 직접 JOIN 명시


---

## 4. 성능 비교 및 결론

### 전체 성능 비교표

|       방식        | 평균 처리시간 | 평균 JDBC 실행시간 | 쿼리 수 | 성능 개선률 |
|:---------------:|:-:|:-:|:-:|:-:|
| **N + 1 발생 시**  | 731ms | 535ms | 1,198개 | - |
| **EntityGraph** | 187ms | 72.5ms | 1개 | **74% 개선** |
| **Fetch Join**  | 236ms | 70.5ms | 1개 | **68% 개선** |

### 주요 발견사항

#### 1. 쿼리 실행 횟수
- **N+1 문제**: 1,198개 쿼리 (1 + 약1,000개 Film + 약200개 Actor)
- **EntityGraph/Fetch Join**: 1개 쿼리 (완전 해결)

#### 2. 순수 DB 실행 시간
- **EntityGraph**: 72.5ms
- **Fetch Join**: 70.5ms
- **차이**: 거의 없음 (약 2ms)

#### 3. JPA 처리 시간
- **EntityGraph**: 187ms - 72.5ms = **114.5ms**
- **Fetch Join**: 236ms - 70.5ms = **165.5ms**
- **차이**: Fetch Join이 51ms 더 많은 처리 시간

JPA 처리 시간에서 EntityGraph/Fetch Join 간에 실행 시간 차이가 나는 것을 발견하였습니다. 왜 이런 차이가 발생하는 것일까요?


### 

---

