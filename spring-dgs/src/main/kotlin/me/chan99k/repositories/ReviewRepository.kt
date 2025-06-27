package me.chan99k.repositories

import me.chan99k.entities.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByMovieId(movieId: Long): List<Review>
    fun findByUserId(userId: Long): List<Review>
    fun findAllByMovieIdIn(movieIds: Collection<Long>): List<Review>
    fun findAllByUserIdIn(userIds: MutableCollection<Long>): MutableList<Review>
}