package org.revo.Service.Impl;

import com.comcast.viper.hlsparserj.PlaylistFactory;
import com.comcast.viper.hlsparserj.PlaylistVersion;
import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.revo.Domain.Media;
import org.revo.Repository.MediaRepository;
import org.revo.Service.MediaService;
import org.revo.Service.S3Service;
import org.revo.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public Media save(MultipartFile file) {
        String key = UUID.randomUUID().toString().replace("-", "") + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
        s3Service.saveMedia(key, file);
        Media media = new Media();
        media.setMediaKey(key);
        return mediaRepository.save(media);
    }

    @Override
    public Media save(Media media) {
        Media one = mediaRepository.findOne(media.getId());
        one.setM3u8(media.getM3u8());
        one.setImage(media.getImage());
        one.setStatus(media.getStatus());
        one.setSecret(media.getSecret());
        return mediaRepository.save(one);
    }

    @Override
    public Iterable<Media> findAll() {
        return mediaRepository.findAll();
    }

    @Override
    public Media findOne(Long id) {
        return mediaRepository.findOne(id);
    }

    @Override
    public String findOneParsed(Long id) {
        List<UnparsedTag> tags = PlaylistFactory.parsePlaylist(PlaylistVersion.TWELVE, findOne(id).getM3u8()).getTags();
        return Utils.TOString(tags, s -> s3Service.generateTsUrl(s));
    }

    @Override
    public Long lastId() {
        return mediaRepository.findTopByOrderByIdDesc().getId();
    }
}
