package me.chan99k.datafetchers

import com.chan99k.springdgs.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import me.chan99k.dataloaders.DirectorByIdDataLoader
import me.chan99k.entities.Director
import me.chan99k.entities.Movie
import me.chan99k.repositories.DirectorRepository
import java.util.concurrent.CompletableFuture

@DgsComponent
class DirectorDataFetcher(
    private val directorRepository: DirectorRepository,
) {
    @DgsData(
        parentType = DgsConstants.MOVIE.TYPE_NAME,
        field = DgsConstants.MOVIE.Director,
    )
    fun getDirectorByMovie(
        dfe: DgsDataFetchingEnvironment
    ): CompletableFuture<Director> {
        // val movie = dfe.getSource<Movie>() ?: throw Exception("source is null")
        // return directorRepository.findById(movie.director?.id!!).get()
        val movie = dfe.getSourceOrThrow<Movie>()
        val dataLoader = dfe.getDataLoader<Long, Director>(DirectorByIdDataLoader::class.java)

        return dataLoader.load(movie.director?.id!!)
    }
}