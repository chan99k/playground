package me.chan99k.datafetchers

import com.chan99k.springdgs.DgsConstants
import com.chan99k.springdgs.types.AddReviewInput
import com.netflix.graphql.dgs.*
import jakarta.annotation.PreDestroy
import me.chan99k.dataloaders.ReviewsByMovieIdDataLoader
import me.chan99k.entities.Movie
import me.chan99k.entities.Review
import me.chan99k.entities.User
import me.chan99k.repositories.ReviewRepository
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.concurrent.CompletableFuture

@DgsComponent
class ReviewDataFetcher(
    private val userDataFetcher: UserDataFetcher,
    private val movieDataFetcher: MovieDataFetcher,
    private val reviewRepository: ReviewRepository,
) {
    val tempDir: Path = Files.createTempDirectory("review_images")

    @PreDestroy
    fun cleanUp() {
        Files.walk(tempDir)
            .map { it.toFile() }
            .forEach { it.delete() }
    }

    @DgsMutation
    fun addReview(
        @InputArgument input: AddReviewInput
    ): Review {
        val user: User = userDataFetcher.user(input.userId)
        val movie = movieDataFetcher.movie(input.movieId)
        val imageFileUrl = input.imageFile
            ?.let { imageFile ->
                // 파일명 생성
                val fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(imageFile.originalFilename!!)
                val targetLocation = tempDir.resolve(fileName)

                // 파일 저장
                Files.copy(imageFile.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

                targetLocation.toString()
            }

        val review = Review(
            rating = input.rating,
            comment = input.comment,
            user = user,
            movie = movie,
            imageFileUrl = imageFileUrl
        )

        reviewRepository.save(review)
        reviewSink.tryEmitNext(review)

        return review
    }

    private val reviewSink = Sinks
        .many()
        .multicast()
        .onBackpressureBuffer<Review>(Queues.SMALL_BUFFER_SIZE, false)

    @DgsSubscription
    fun newReview(
        @InputArgument movieId: Long,
    ): Flux<Review> {
        return reviewSink.asFlux()
            .filter { it.movie?.id == movieId }
    }

    @DgsData(
        parentType = DgsConstants.MOVIE.TYPE_NAME,
        field = DgsConstants.MOVIE.Reviews,
    )
    fun getReviewByMovie(
        dfe: DgsDataFetchingEnvironment,
    ): CompletableFuture<List<Review>> {
        val movie = dfe.getSourceOrThrow<Movie>()
        val dataLoader = dfe.getDataLoader<Long, List<Review>>(ReviewsByMovieIdDataLoader::class.java)

        return dataLoader.load(movie.id)
    }

    @DgsData(
        parentType = DgsConstants.USER.TYPE_NAME,
        field = DgsConstants.USER.Reviews,
    )
    fun getReviewByUser(
        dfe: DgsDataFetchingEnvironment,
    ): CompletableFuture<List<Review>> {
        val user = dfe.getSourceOrThrow<User>()
        val dataLoader = dfe.getDataLoader<Long, List<Review>>(ReviewsByMovieIdDataLoader::class.java)

        return dataLoader.load(user.id!!)
    }
}