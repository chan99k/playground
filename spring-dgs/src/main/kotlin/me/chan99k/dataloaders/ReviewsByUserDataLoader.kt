package me.chan99k.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import me.chan99k.entities.Review
import me.chan99k.repositories.ReviewRepository
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "reviewsByUserDataLoader")
class ReviewsByUserDataLoader(
    val reviewRepository: ReviewRepository,
) : MappedBatchLoader<Long, List<Review>> {
    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, List<Review>>>? {
        return CompletableFuture.supplyAsync {
            reviewRepository.findAllByUserIdIn(keys).groupBy { it.user?.id!! }
        }
    }
}