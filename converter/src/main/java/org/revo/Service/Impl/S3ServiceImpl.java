package org.revo.Service.Impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.revo.Domain.Bucket;
import org.revo.Domain.Media;
import org.revo.Service.S3Service;
import org.revo.Util.HlsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Override
    public String saveImage(String key, File file) {
        this.amazonS3Client.putObject(Bucket.Thumb.toString(), file.getName(), file);
        return getUrl(Bucket.Thumb, file.getName());
    }

    @Override
    public void saveTs(Path path) {
        this.amazonS3Client.putObject(Bucket.Ts.toString(), path.getParent().getFileName().toString() + "/" + path.getFileName().toString(), path.toFile());
    }

    @Override
    public String getUrl(Bucket bucket, String key) {
        return amazonS3Client.getUrl(bucket.toString(), key).toString();
    }

    @Override
    public String pull(Media media) {
        S3Object object = this.amazonS3Client.getObject(Bucket.Videos.toString(), media.getMediaKey());
        try {
            Path temp = Files.createTempDirectory("temp");
            Path f = Paths.get(temp.toString(), media.getMediaKey());
            Files.copy(object.getObjectContent(), f);
            return f.toString();
        } catch (IOException e) {
            return "";
        }
    }

    @Override
    public void push(HlsResult hlsResult) {
        try {
            Files.walk(Paths.get(hlsResult.getData()))
                    .filter(Files::isRegularFile)
                    .filter(it -> it.toString().endsWith("ts"))
                    .forEach(this::saveTs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
