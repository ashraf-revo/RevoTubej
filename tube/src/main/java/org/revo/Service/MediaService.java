package org.revo.Service;

import org.revo.Domain.Media;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ashraf on 15/04/17.
 */
public interface MediaService {

    Media save(MultipartFile file);

    Media save(Media media);

    Iterable<Media> findAll();

    Media findOne(Long id);

    String findOneParsed(Long id);

    Long lastId();
}
