package me.chan99k.datafetchers

import com.chan99k.springdgs.client.MovieGraphQLQuery
import com.chan99k.springdgs.client.MovieProjectionRoot
import me.chan99k.config.DataLoaderExecutor
import me.chan99k.dataloaders.DirectorByIdDataLoader

import me.chan99k.exceptions.CustomDataFetcherExceptionHandler
import me.chan99k.repositories.DirectorRepository
import me.chan99k.repositories.MovieRepository
import me.chan99k.scalars.EmailScalar

import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import com.netflix.graphql.dgs.test.EnableDgsTest
import me.chan99k.entities.Director
import me.chan99k.entities.Movie
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDate
import java.util.*

@EnableDgsTest
@SpringBootTest(
    classes = [
        MovieDataFetcher::class,
        DgsExtendedScalarsAutoConfiguration::class,
        CustomDataFetcherExceptionHandler::class,
        DirectorDataFetcher::class,
        DirectorByIdDataLoader::class,
        DataLoaderExecutor::class,
        EmailScalar::class,
    ]
)
class MovieDataFetcherTest() {
    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockitoBean
    lateinit var directorRepository: DirectorRepository

    @MockitoBean
    lateinit var movieRepository: MovieRepository

    @Test
    fun movies() {
        Mockito.`when`(movieRepository.findAll()).thenAnswer {
            listOf(
                Movie(id = 1, title = "movie1", releaseDate = LocalDate.now()),
                Movie(id = 2, title = "movie2", releaseDate = LocalDate.now()),
                Movie(id = 3, title = "movie3", releaseDate = LocalDate.now())
            )
        }

        val titles = dgsQueryExecutor.executeAndExtractJsonPath<List<String>>(
            """
                {
                    movies {
                        title
                    }
                    
                }
            """.trimIndent(), "data.movies[*].title"
        )

        assertThat(titles).contains("movie1")
        assertThat(titles.size).isEqualTo(3)
    }

    @Test
    fun movie() {
        Mockito.`when`(movieRepository.findById(1)).thenAnswer {
            Optional.of(Movie(id = 1, title = "movie1", releaseDate = LocalDate.now()))
        }

        val title: String = dgsQueryExecutor.executeAndExtractJsonPath(
            """
                {
                    movie(movieId:1) {
                     title
                    }
                }
            """.trimIndent(), "data.movie.title"
        )

        assertThat(title).isEqualTo("movie1")
    }

    @Test
    fun movieWithException() {
        Mockito.`when`(movieRepository.findById(2)).thenAnswer {
            Optional.ofNullable(null)
        }

        val result = dgsQueryExecutor.execute(
            """
                {
                movie(movieId: 2){
                title
                }
                }
            """.trimIndent()
        )

        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).contains("Movie Not Found")
        assertThat(result.errors[0].extensions["errorCode"]).isEqualTo(1002)
    }

    //-- Query 이용 --//

    @Test
    fun movieWithQueryRequest() {
        Mockito.`when`(movieRepository.findById(1)).thenAnswer {
            Optional.of(Movie(id = 1, title = "movie1", releaseDate = LocalDate.now()))
        }

        // when
        val graphQLQueryRequest = GraphQLQueryRequest(
            MovieGraphQLQuery.Builder()
                .movieId(1)
                .build(),
            MovieProjectionRoot<Nothing, Nothing>().title()
        )

        val title: String =
            dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(), "data.movie.title")

        assertThat(title).isEqualTo("movie1")
    }

    @Test
    fun movieWithDirector() {
        Mockito.`when`(movieRepository.findById(1)).thenAnswer {
            Optional.of(
                Movie(
                    id = 1,
                    title = "movie1",
                    releaseDate = LocalDate.now(),
                    director = Director(1, "director1")
                )
            )
        }

        Mockito.`when`(directorRepository.findAllById(listOf(1))).thenAnswer {
            listOf(Director(id = 1, name = "director1"))
        }

        val graphqlQuery = GraphQLQueryRequest(
            MovieGraphQLQuery.Builder()
                .movieId(1)
                .build(),
            MovieProjectionRoot<Nothing, Nothing>().director().name().id()
        )

        val directorName: String =
            dgsQueryExecutor.executeAndExtractJsonPath(
                graphqlQuery.serialize(),
                "data.movie.director.name"
            )

        assertThat(directorName).isEqualTo("director1")
    }
}