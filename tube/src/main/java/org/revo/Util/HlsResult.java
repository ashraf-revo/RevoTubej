package org.revo.Util;

import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * Created by ashraf on 15/04/17.
 */
public class HlsResult {
    private String m3u8;
    private byte[] key;
    private boolean status;
    private String data;
    private String file;
    public HlsResult(String m3u8, byte[] key, boolean status, String data, String file) {
        this.m3u8 = m3u8;
        this.key = key;
        this.status = status;
        this.data = data;
        this.file = file;
    }

    public String getM3u8() {
        return m3u8;
    }

    public byte[] getKey() {
        return key;
    }

    public boolean isStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getFile() {
        return file;
    }

    public void freeSpace() {
        FileSystemUtils.deleteRecursively(new File(data).getParentFile());
    }
}
