package me.chan99k.datafetchers


import me.chan99k.exceptions.NotFoundException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class MovieIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `movie integration test`() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "query" : "query { movie(movieId: 101) { title releaseDate } }"
                          
                        }
                    """.trimIndent()
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "data" : {
                        "movie" : {
                          "title" : "Inception", "releaseDate" : "2010-07-16" 
                        }
                      }
                    }
                """.trimIndent()
                )
            )
    }

    @Test
    fun `movie 없는 영화 조회`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/graphql").contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                {
                   "query" : "query { movie(movieId: 201) { title releaseDate } }"
                }
            """.trimIndent()
                )
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.errors[0].extensions.class").value(NotFoundException::class.java.name)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].extensions.errorCode").value("1002"))
    }

    @Test
    fun `movies integration test`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/graphql").contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                    "query" : "query { movies { title releaseDate }}"
                    }
                """.trimIndent())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                        {
                          "data" : {
                            "movies" : [
                                {"title" : "Inception", "releaseDate" : "2010-07-16" },
                                {"title" : "Interstellar", "releaseDate" : "2014-11-07" },
                                {"title" : "Django Unchained", "releaseDate" : "2012-12-25" }
                            ]
                          }
                        }
                    """.trimIndent()
                )
            )
    }
}