package chan99k.springdgs.entities

import jakarta.persistence.*

@Entity
class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

    @Column(nullable = false)
    val rating: Int? = null,

    @Column
    val comment: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id")
    val movie: Movie? = null,

    @Column(nullable = true)
    val imageFileUrl: String? = null
) {
}