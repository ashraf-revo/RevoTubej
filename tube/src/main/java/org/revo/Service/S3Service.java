package org.revo.Service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ashraf on 15/04/17.
 */
public interface S3Service {
    String generateTsUrl(String key);

    void saveMedia(String key, MultipartFile file);
}
