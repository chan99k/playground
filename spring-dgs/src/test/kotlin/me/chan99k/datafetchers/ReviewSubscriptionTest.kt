package me.chan99k.datafetchers


import me.chan99k.repositories.ReviewRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration
import com.netflix.graphql.dgs.test.EnableDgsTest
import graphql.ExecutionResult
import me.chan99k.entities.Movie
import me.chan99k.entities.Review
import me.chan99k.entities.User

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@SpringBootTest(
    classes = [
        DgsExtendedScalarsAutoConfiguration::class,
        ReviewDataFetcher::class,
    ]
)
@EnableDgsTest
class ReviewSubscriptionTest {
    @MockitoBean
    lateinit var reviewRepository: ReviewRepository

    @MockitoBean
    lateinit var userDataFetcher: UserDataFetcher

    @MockitoBean
    lateinit var movieDataFetcher: MovieDataFetcher

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @Test
    fun `new review subscription test`() {
        val executionResult = dgsQueryExecutor.execute(
            """
                subscription {
                    newReview(movieId: 101) {
                        id
                    }
                }
            """.trimIndent()
        )
        val publisher = executionResult.getData<Publisher<ExecutionResult>>()
        val result = mutableListOf<Review>()
        val latch = CountDownLatch(2)

        publisher.subscribe(object : Subscriber<ExecutionResult> {
            override fun onSubscribe(p0: Subscription) {
                p0.request(2)
            }

            override fun onError(throwable: Throwable?) {
                println("Error : ${throwable?.message}")
            }

            override fun onComplete() {
                println("Completed")
            }

            override fun onNext(item: ExecutionResult) {
                val data = item.getData<Map<String, Any>>()

                result.add(ObjectMapper().convertValue(data["newReview"], Review::class.java))
            }
        })

        addReview()
        addReview()
        latch.await(3, TimeUnit.SECONDS)

        assertThat(result.size).isEqualTo(2)
    }

    private fun addReview() {
        Mockito.`when`(movieDataFetcher.movie(101)).thenAnswer { Movie(101) }
        Mockito.`when`(userDataFetcher.user(101)).thenAnswer { User(101) }

        dgsQueryExecutor.execute(
            """
                mutation AddReview {
                    addReview(input: {movieId: 101 userId: 101 rating: 5 comment: "sex"}) {
                        id
                    }
                }
            """.trimIndent()
        )

    }

}