package org.revo.Service;

import org.revo.Domain.User;

import java.util.Optional;

/**
 * Created by ashraf on 17/04/17.
 */
public interface UserService {
    Optional<User> findByEmail(String email);

    long count();

    void save(User user);

    void encodeThenSave(User user);

    void activate(Long id);
}
