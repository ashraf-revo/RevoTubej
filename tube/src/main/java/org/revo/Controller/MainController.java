package org.revo.Controller;

import org.revo.Domain.Media;
import org.revo.Service.Bento4Service;
import org.revo.Service.MediaService;
import org.revo.Service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by ashraf on 18/04/17.
 */
@RestController
@RequestMapping("api")
public class MainController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private Bento4Service bento4Service;
    @Autowired
    private S3Service s3Service;

    @PostMapping
    private Media save(@RequestParam("file") MultipartFile file) {
        Media save = mediaService.save(file);
        new Thread(() -> {
            s3Service.saveMedia(save.getMediaKey(), file);
            bento4Service.convertHls(save);
        }).start();
        return save;
    }

    @GetMapping
    public Iterable<Media> findAll() {
        return mediaService.findAll();
    }

    @GetMapping("{id}")
    public Media findOne(@PathVariable("id") Long id) {
        return mediaService.findOne(id);
    }

    @GetMapping("{id}.key/")
    public void findOneKey(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        byte[] secret = mediaService.findOne(id).getSecret();
        response.getOutputStream().write(secret);
        response.setContentType("application/pgp-keys");
        response.setHeader("Content-disposition", "attachment; filename=" + id + ".key");
    }

    @GetMapping("{id}.m3u8/")
    public void findOneM3u8(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        response.getWriter().print(mediaService.findOneParsed(id));
        response.setContentType("audio/x-mpegurl");
        response.setHeader("Content-disposition", "attachment; filename=" + id + ".m3u8");
    }


}