package me.chan99k.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import me.chan99k.entities.Director
import me.chan99k.repositories.DirectorRepository
import org.dataloader.BatchLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor


@DgsDataLoader(name = "directorById", caching = true, batching = true, maxBatchSize = 5)
class DirectorByIdDataLoader(
    private val directorRepository: DirectorRepository,
    @Qualifier("DataLoaderThreadPool")
    private val executor: Executor,
) : BatchLoader<Long, Director> {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun load(keys: MutableList<Long>?): CompletionStage<List<Director>> {
        logger.info("director dataLoader")
        return CompletableFuture.supplyAsync(
            {
                logger.info("Executing in thread: ${Thread.currentThread().name}")
                directorRepository.findAllById(keys!!)
            }, executor
        )
    }
}