package org.revo.Service.Impl;

import org.bytedeco.javacv.*;
import org.revo.Service.FfmpegService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by ashraf on 24/03/17.
 */
@Service
public class FfmpegServiceImpl implements FfmpegService {


    public void doConversion(File videoFile) {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoFile);
        try {
            String name = videoFile.getName().split("\\.")[0];
            File file = new File(Paths.get(videoFile.getParentFile().getPath(), name).toString());
            frameGrabber.start();
            Frame grab;
            FrameRecorder recorder = new FFmpegFrameRecorder(new File(file.getPath() + "/" + name + "index" + ".m3u8"), frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());

            recorder.setAspectRatio(frameGrabber.getAspectRatio());
            recorder.setFrameRate(frameGrabber.getFrameRate());


            recorder.setOption("hls_time", "3");
            recorder.setOption("start_number", "0");
            recorder.setOption("hls_list_size", "0");
            recorder.setOption("f", "hls");
            recorder.setOption("level", "3.0");
            recorder.setOption("profile:v", "baseline");
            recorder.setOption("strict", "experimental");
            recorder.setOption("hls_key_info_file", file.toString() + "/" + name + ".keyinfo");


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
}
