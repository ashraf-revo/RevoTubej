package org.revo.Repository;

import org.revo.Domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by ashraf on 17/04/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User set enable=true ,type='110' where id=?1 and type='000' ")
    void activate(Long id);
}
