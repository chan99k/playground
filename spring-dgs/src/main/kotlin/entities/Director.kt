package chan99k.springdgs.entities

import jakarta.persistence.*

@Entity
class Director(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String? = null,

    @OneToMany(mappedBy = "director")
    val movies: List<Movie> = emptyList()
) {
}