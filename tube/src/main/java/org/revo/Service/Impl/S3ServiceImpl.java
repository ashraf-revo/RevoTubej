package org.revo.Service.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.revo.Domain.Bucket;
import org.revo.Domain.Media;
import org.revo.Service.S3Service;
import org.revo.Util.HlsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public String generateTsUrl(String key) {
        return getUrlExpired(Bucket.Ts, key);
    }

    @Override
    public String thumbUrl(String mediaKey) {
        return amazonS3Client.getUrl(Bucket.Thumb.toString(), mediaKey + ".png").toString();
    }

    private String getUrlExpired(Bucket bucket, String key) {
        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 60;
        expiration.setTime(msec);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket.toString(), key);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);
        return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    @Override
    public void saveMedia(String key, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            this.amazonS3Client.putObject(Bucket.Videos.toString(), key, file.getInputStream(), metadata);
        } catch (IOException ignored) {
        }
    }

    @Override
    public String saveImage(String key, File file) {
        this.amazonS3Client.putObject(Bucket.Thumb.toString(), file.getName(), file);
        return getUrl(Bucket.Thumb, file.getName());
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
    public void push(HlsResult hlsResult, Runnable runnable) {
        TransferManager tm = new TransferManager(amazonS3Client);
        MultipleFileUpload t = tm.uploadDirectory(Bucket.Ts.toString(), StringUtils.getFilename(hlsResult.getData()), new File(hlsResult.getData()), false);
        t.addProgressListener((ProgressListener) p -> {
            if (t.isDone()) {
                tm.shutdownNow(false);
                runnable.run();
            }
        });
    }
}
