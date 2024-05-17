package GN.study.user.repository;

import GN.study.user.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RefreshToken, String> {
}
