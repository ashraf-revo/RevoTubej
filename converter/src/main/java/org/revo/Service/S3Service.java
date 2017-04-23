package org.revo.Service;

import org.revo.Domain.Bucket;
import org.revo.Domain.Media;
import org.revo.Util.HlsResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by ashraf on 15/04/17.
 */
public interface S3Service {
    String generateTsUrl(String key);

    String thumbUrl(String mediaKey);

    void saveMedia(String key, MultipartFile file);

    String saveImage(String key, File file);

    void saveTs(Path path);

    String getUrl(Bucket bucket, String key);


    String pull(Media media);

    void push(HlsResult hlsResult);
}
