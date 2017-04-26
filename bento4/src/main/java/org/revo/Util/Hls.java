package org.revo.Util;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.revo.Util.Utils.*;

/**
 * Created by ashraf on 14/04/17.
 */
public class Hls {
    private int hls_version = 3;
    private int segment_duration = 10;
    private int segment_duration_threshold = 15;
    private String index_filename = "stream.m3u8";
    private String key_filename = "stream.key";
    private String segment_filename_template = "segment-%d.ts";
    private String segment_url_template = "segment-%d.ts";
    private String encryption_key = UUID.randomUUID().toString().replace("-", "");
    private String encryption_iv_mode = "random";
    private String source;
    private String executable;
    private String executionDir = "";
    private String tsUrl = "";
    private String keyUrl = "";


    public Hls(String source) {
        String name = getFilename(source).split("\\.")[0];
        this.executionDir = new File(source).getParent() + File.separator + name;
        File file = new File(executionDir);
        deleteRecursively(file);
        file.mkdirs();
        this.executable = System.getProperty("user.home") + File.separator + "Bento4" + File.separator + "bin" + File.separator + "mp42hls";
        this.source = source;
        this.index_filename = name + ".m3u8";
        this.key_filename = name + ".key/";
        this.segment_filename_template = name + "-%d.ts";
        this.segment_url_template = name + "-%d.ts";
    }

    public void setTsUrl(String tsUrl) {
        this.tsUrl = tsUrl;
    }

    public void setSegment_duration(int segment_duration) {
        this.segment_duration = segment_duration;
    }

    public void setKey_filename(String key_filename) {
        this.key_filename = key_filename;
    }

    private String withExecutionDir(String txt) {
        return executionDir.isEmpty() ? txt : executionDir + File.separator + txt;
    }

    private String withSiteUrl(String txt) {
        return tsUrl.isEmpty() ? txt : tsUrl + "/" + txt;
    }

    private String withKeyUrl(String txt) {
        return keyUrl.isEmpty() ? txt : keyUrl + "/" + txt;
    }


    @Override
    public String toString() {
        return executable + " --hls-version " + hls_version + " --segment-duration " + segment_duration + " --segment-duration-threshold " + segment_duration_threshold + " --index-filename " + withExecutionDir(index_filename) + " --segment-filename-template " + withExecutionDir(segment_filename_template) + " --segment-url-template " + withSiteUrl(segment_url_template) + " --encryption-key " + encryption_key + " --encryption-iv-mode " + encryption_iv_mode + " --encryption-key-uri " + withKeyUrl(key_filename) + " " + source;
    }

    public HlsResult execute() {
        String[] CMDARRAY1 = {"/bin/sh", "-c", this.toString()};
        ProcessBuilder processBuilder = new ProcessBuilder(CMDARRAY1);
        try {
            Process start = processBuilder.start();
            int result = start.waitFor();
            return new HlsResult(readFileToString(new File(withExecutionDir(index_filename))), key(encryption_key), result == 0, executionDir, source);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return new HlsResult(null, null, false, null, null);
    }

    public static void init(File bento4) {
        try {
            File path = new File(System.getProperty("user.home") + File.separator + "Bento4");
            deleteRecursively(path);
            path.mkdir();
            copyRecursively(bento4, path);
            String[] CMDARRAY2 = {"chmod", "-R", "+xr", System.getProperty("user.home") + File.separator + "Bento4" + File.separator + "bin" + File.separator};
            new ProcessBuilder(CMDARRAY2).start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
