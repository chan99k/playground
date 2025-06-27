package me.chan99k.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import me.chan99k.entities.User
import me.chan99k.repositories.UserRepository
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader
class UserByIdDataLoader(
    val userRepository: UserRepository,
) : MappedBatchLoader<Long, User> {
    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, User>> {
        return CompletableFuture.supplyAsync {
            userRepository.findAllById(keys).associateBy {
                it.id!!
            }
        }
    }
}