package me.chan99k.dataloaders

import me.chan99k.repositories.MovieRepository
import com.netflix.graphql.dgs.DgsDataLoader
import me.chan99k.entities.Movie
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "moviesByDirectorDataLoader")
class MoviesByDirectorDataLoader(
    val movieRepository: MovieRepository,
) : MappedBatchLoader<Long, List<Movie>> {
    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, List<Movie>>> {
        return CompletableFuture.supplyAsync {
            movieRepository.findAllByDirectorIdIn(keys).groupBy { it.director?.id!! }.toMap()
        }
    }
}