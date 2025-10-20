package chan99k.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.entitiy.user.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findUserEntityByUserId(String userId);
}