package org.revo.Service.Impl;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;
import org.revo.Service.FfmpegService;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by ashraf on 24/03/17.
 */
@Service
public class FfmpegServiceImpl implements FfmpegService {

    static void doc() {
        String filename = "/media/ashraf/revo/bs.mp4";
        File file = new File(filename);
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file);
        try {
            frameGrabber.start();
            Frame grab;
            FrameRecorder recorder = new FFmpegFrameRecorder(file.getParent() + File.separator + "te.mp4", frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
            recorder.setAspectRatio(frameGrabber.getAspectRatio());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.start();
            frameGrabber.start();
            while ((grab = frameGrabber.grab()) != null) {
                recorder.record(grab);
            }
            recorder.stop();
            recorder.release();
            frameGrabber.stop();
        } catch (FrameGrabber.Exception | FrameRecorder.Exception ignored) {
        }
    }

    public static void main(String[] args) {
        doc();
    }
}
