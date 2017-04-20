package org.revo.Repository;

import org.revo.Domain.Media;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ashraf on 15/04/17.
 */
public interface MediaRepository extends CrudRepository<Media, Long> {
    Media findTopByOrderByIdDesc();
}
