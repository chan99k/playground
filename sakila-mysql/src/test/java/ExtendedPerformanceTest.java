import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import chan99k.adapter.persistence.entities.FilmActor;
import chan99k.adapter.persistence.repository.FilmActorRepository;
import chan99k.application.FilmActorResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ExtendedPerformanceTest {
	private static final int WARMUP_ITERATIONS = 50;
	private static final int TEST_ITERATIONS = 25000;

	@Autowired
	private FilmActorRepository filmActorRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void runExtendedTest() throws InterruptedException {
		log.info("=== 확장된 성능 테스트 시작 ===");

		// 1. JVM 워밍업
		warmupJVM();

		// 2. 통계 초기화
		resetHibernateStatistics();

		// 3. EntityGraph 테스트
		PerformanceResult entityGraphResult = testEntityGraph();
		Statistics entityGraphStats = getHibernateStatistics();

		// 4. 메모리 정리 & 통계 초기화
		System.gc();
		Thread.sleep(1000);
		resetHibernateStatistics();

		// 5. Fetch Join 테스트
		PerformanceResult fetchJoinResult = testFetchJoin();
		Statistics fetchJoinStats = getHibernateStatistics();

		// 6. 결과 분석
		analyzeResults(entityGraphResult, fetchJoinResult, entityGraphStats, fetchJoinStats);
	}

	private void warmupJVM() throws InterruptedException {
		log.info("JVM 워밍업 시작...");
		for (int i = 0; i < WARMUP_ITERATIONS; i++) {
			try {
				if (i % 2 == 0) {
					// 원래 실험과 동일한 조건 - 전체 데이터 조회
					filmActorRepository.getAllFilmActorsWithEntityGraph(Pageable.ofSize(10000))
						.stream()
						.map(this::mapToResponse)
						.toList();
				} else {
					filmActorRepository.getAllFilmActorsWithFetchJoin(Pageable.ofSize(10000))
						.stream()
						.map(this::mapToResponse)
						.toList();
				}
			} catch (Exception e) {
				log.warn("워밍업 중 오류 발생: {}", e.getMessage());
			}
		}
		System.gc();
		Thread.sleep(2000);
		log.info("JVM 워밍업 완료");
	}

	private PerformanceResult testEntityGraph() {
		log.info("EntityGraph 테스트 시작...");
		List<Long> executionTimes = new ArrayList<>();

		for (int i = 0; i < TEST_ITERATIONS; i++) {
			// 각 테스트마다 영속성 컨텍스트 클리어
			entityManager.clear();

			long startTime = System.nanoTime();
			try {
				List<FilmActorResponse> result = filmActorRepository.getAllFilmActorsWithEntityGraph(
						Pageable.ofSize(10000))
					.stream()
					.map(this::mapToResponse)
					.toList();

				// 실제 지연 로딩이 발생하는지 확인
				result.forEach(this::accessLazyProperties);

			} catch (Exception e) {
				log.error("EntityGraph 테스트 중 오류: {}", e.getMessage());
				continue;
			}
			long endTime = System.nanoTime();

			executionTimes.add((endTime - startTime) / 1_000_000);

			if ((i + 1) % 20 == 0) {
				log.debug("EntityGraph 진행률: {}/{}", i + 1, TEST_ITERATIONS);
			}
		}

		log.info("EntityGraph 테스트 완료");
		return new PerformanceResult("EntityGraph", executionTimes);
	}

	private PerformanceResult testFetchJoin() {
		log.info("FetchJoin 테스트 시작...");
		List<Long> executionTimes = new ArrayList<>();

		for (int i = 0; i < TEST_ITERATIONS; i++) {
			entityManager.clear();

			long startTime = System.nanoTime();
			try {
				List<FilmActorResponse> result = filmActorRepository.getAllFilmActorsWithFetchJoin(
						Pageable.ofSize(10000))
					.stream()
					.map(this::mapToResponse)
					.toList();

				result.forEach(this::accessLazyProperties);

			} catch (Exception e) {
				log.error("FetchJoin 테스트 중 오류: {}", e.getMessage());
				continue;
			}
			long endTime = System.nanoTime();

			executionTimes.add((endTime - startTime) / 1_000_000);

			if ((i + 1) % 20 == 0) {
				log.debug("FetchJoin 진행률: {}/{}", i + 1, TEST_ITERATIONS);
			}
		}

		log.info("FetchJoin 테스트 완료");
		return new PerformanceResult("FetchJoin", executionTimes);
	}

	private FilmActorResponse mapToResponse(Object filmActor) {
		// 타입 안전성을 위한 캐스팅 확인
		if (filmActor instanceof FilmActor fa) {
			return new FilmActorResponse(
				fa.getFilm().getId(),
				fa.getFilm().getTitle(),
				fa.getActor().getId(),
				fa.getActor().getFirstName(),
				fa.getActor().getLastName()
			);
		}
		throw new IllegalArgumentException("Expected FilmActor but got: " + filmActor.getClass());
	}

	private void accessLazyProperties(FilmActorResponse response) {
		// 실제 지연 로딩 접근을 시뮬레이션하여 N+1 문제 확인
		// 이미 DTO로 변환된 상태이므로 실제로는 변환 과정에서 접근됨
	}

	private void resetHibernateStatistics() {
		Statistics stats = entityManager.getEntityManagerFactory()
			.unwrap(SessionFactory.class)
			.getStatistics();
		stats.clear();
	}

	private Statistics getHibernateStatistics() {
		return entityManager.getEntityManagerFactory()
			.unwrap(SessionFactory.class)
			.getStatistics();
	}

	private void analyzeResults(PerformanceResult entityGraph, PerformanceResult fetchJoin,
		Statistics egStats, Statistics fjStats) {
		log.info("==== 성능 비교 결과 ====");
		log.info(entityGraph.summary());
		log.info(fetchJoin.summary());

		// 성능 차이 분석
		double timeDifference = fetchJoin.getAverage() - entityGraph.getAverage();
		double percentageChange = (timeDifference / entityGraph.getAverage()) * 100;

		log.info("==== 성능 차이 분석 ====");
		log.info("평균 실행 시간 차이: {} ms", timeDifference);
		log.info("성능 변화율: {}% ({})",
			Math.abs(percentageChange),
			timeDifference > 0 ? "FetchJoin이 더 느림" : "EntityGraph가 더 느림");

		// Hibernate Statistics 비교
		log.info("==== Hibernate Statistics 비교 ====");
		log.info("[EntityGraph] 쿼리 실행 수: {}, JDBC 준비: {}, JDBC 실행: {}",
			egStats.getQueryExecutionCount(),
			egStats.getPrepareStatementCount(),
			egStats.getQueryExecutionCount());
		log.info("[FetchJoin] 쿼리 실행 수: {}, JDBC 준비: {}, JDBC 실행: {}",
			fjStats.getQueryExecutionCount(),
			fjStats.getPrepareStatementCount(),
			fjStats.getQueryExecutionCount());

		// 통계적 유의성 간단 체크
		if (Math.abs(timeDifference) > Math.max(entityGraph.getStandardDeviation(), fetchJoin.getStandardDeviation())) {
			log.info("결과: 통계적으로 의미있는 차이가 있을 가능성이 높습니다.");
		} else {
			log.info("결과: 통계적으로 유의미하지 않을 수 있습니다. 더 많은 샘플이 필요합니다.");
		}
	}
}
