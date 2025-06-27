package me.chan99k.datafetchers

import com.chan99k.springdgs.DgsConstants
import com.chan99k.springdgs.types.AddUserInput
import com.netflix.graphql.dgs.*
import me.chan99k.dataloaders.UserByIdDataLoader
import me.chan99k.entities.Review
import me.chan99k.entities.User
import me.chan99k.repositories.UserRepository
import java.util.concurrent.CompletableFuture

@DgsComponent
class UserDataFetcher(
    private val userRepository: UserRepository,
) {

    @DgsQuery
    fun user(
        @InputArgument userId: Long,
    ): User {
        return userRepository.findById(userId).orElseThrow { Exception("User Not Found") }
    }

    @DgsMutation
    fun addUser(@InputArgument input: AddUserInput): User {
        val user = User(
            username = input.username,
            email = input.email,
        )
        return userRepository.save(user)
    }

    @DgsData(
        parentType = DgsConstants.REVIEW.TYPE_NAME,
        field = DgsConstants.REVIEW.User,
    )
    fun getUserByReview(
        dfe: DgsDataFetchingEnvironment,
    ): CompletableFuture<User> {
        val review = dfe.getSourceOrThrow<Review>()
        val dataLoader = dfe.getDataLoader<Long, User>(UserByIdDataLoader::class.java)

        return dataLoader.load(review.user?.id!!)
    }
}