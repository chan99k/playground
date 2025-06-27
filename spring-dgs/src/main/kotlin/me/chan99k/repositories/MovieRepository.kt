package me.chan99k.repositories

import me.chan99k.entities.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {

    fun findByDirectorId(directorId: Long): List<Movie>

    fun findAllByDirectorIdIn(directorIds: MutableCollection<Long>): MutableList<Movie>

}