package org.revo.Service.Impl;

import org.jcodec.api.JCodecException;
import org.jcodec.api.awt.FrameGrab;
import org.jcodec.common.FileChannelWrapper;
import org.jcodec.common.NIOUtils;
import org.revo.Domain.Media;
import org.revo.Domain.Status;
import org.revo.Service.Bento4Service;
import org.revo.Service.MediaService;
import org.revo.Service.S3Service;
import org.revo.Util.Hls;
import org.revo.Util.HlsResult;
import org.revo.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Created by ashraf on 15/04/17.
 */
@Service
public class Bento4ServiceImpl implements Bento4Service {
    @Autowired
    private S3Service s3Service;
    @Autowired
    private MediaService mediaService;

    @Override
    public void convertHls(Media media) {
        String source = s3Service.pull(media);
        Hls hls = new Hls(source);
        hls.setKey_filename(media.getId() + ".key/");
        String filename = Utils.getFilename(source);
        hls.setTsUrl(filename.substring(0, filename.length() - Utils.getExtension(filename).length() - 1));

        HlsResult hlsResult = hls.execute();
        if (hlsResult.isStatus()) {
            File image = convertImage(hlsResult.getFile());
            s3Service.push(hlsResult, () -> {
                if (image != null)
                    media.setImage(s3Service.saveImage(image.getName(), image));

                media.setM3u8(hlsResult.getM3u8());
                media.setSecret(hlsResult.getKey());
                media.setStatus(Status.SUCCESS);
                mediaService.save(media);
                hlsResult.freeSpace();
            });
        } else {
            media.setStatus(Status.FAIL);
            mediaService.save(media);
            hlsResult.freeSpace();
        }
    }

    @Override
    public File convertImage(String video) {
        try {
            FileChannelWrapper ch = NIOUtils.readableFileChannel(new File(video));
            FrameGrab fg = new FrameGrab(ch);
            String type = "png";
            File output = new File(video.substring(0, video.length() - StringUtils.getFilenameExtension(video).length()) + type);
            ImageIO.write(fg.getFrame(), type, output);
            NIOUtils.closeQuietly(ch);
            return output;
        } catch (IOException | JCodecException ignored) {
            return null;
        }
    }
}
