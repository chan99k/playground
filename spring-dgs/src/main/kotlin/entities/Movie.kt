package chan99k.springdgs.entities

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String? = null,

    @Column(nullable = false)
    val releaseDate: LocalDate? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "director_id")
    val director: Director? = null,

    @OneToMany(mappedBy = "movie")
    val reviews : List<Review> = emptyList(),
) {
}