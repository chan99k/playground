package me.chan99k.repositories

import me.chan99k.entities.Director
import org.springframework.data.jpa.repository.JpaRepository

interface DirectorRepository : JpaRepository<Director, Long> {
}