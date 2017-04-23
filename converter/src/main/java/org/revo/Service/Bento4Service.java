package org.revo.Service;

import org.revo.Domain.Media;

import java.io.File;

/**
 * Created by ashraf on 15/04/17.
 */
public interface Bento4Service {
    Media convertHls(Media media);

    File convertImage(String video);
}
