package me.chan99k.datafetchers

import me.chan99k.dataloaders.MoviesByDirectorDataLoader
import me.chan99k.exceptions.NotFoundException
import me.chan99k.repositories.MovieRepository
import com.chan99k.springdgs.DgsConstants
import com.netflix.graphql.dgs.*
import me.chan99k.entities.Director
import me.chan99k.entities.Movie
import java.util.concurrent.CompletableFuture

@DgsComponent
class MovieDataFetcher(
    private val movieRepository: MovieRepository,
) {


    @DgsQuery(field = "movies")
    fun getMovies(): MutableList<Movie> {
        return movieRepository.findAll()
    }

    @DgsQuery
    fun movie(@InputArgument movieId: Long): Movie {
        return movieRepository.findById(movieId).orElseThrow { NotFoundException("Movie Not Found") }
    }

    @DgsData(
        parentType = DgsConstants.DIRECTOR.TYPE_NAME,
        field = DgsConstants.DIRECTOR.Movies,
    )
    fun getMovieByDirector(
        dfe: DgsDataFetchingEnvironment,
    ): CompletableFuture<List<Movie>> {
        val director = dfe.getSourceOrThrow<Director>()
        val dataLoader = dfe.getDataLoader<Long, List<Movie>>(MoviesByDirectorDataLoader::class.java)

        return dataLoader.load(director.id)
    }
}