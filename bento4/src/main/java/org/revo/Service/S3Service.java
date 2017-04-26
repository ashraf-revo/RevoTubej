package org.revo.Service;

import org.revo.Domain.Bucket;
import org.revo.Domain.Media;
import org.revo.Util.HlsResult;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by ashraf on 15/04/17.
 */
public interface S3Service {

    String saveImage(String key, File file);

    void saveTs(Path path);

    String getUrl(Bucket bucket, String key);


    String pull(Media media);

    void push(HlsResult hlsResult);
}
