package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by ashraf on 15/04/17.
 */
@Getter
@Setter
public class Media {
    private Long id;
    private String m3u8;
    private String image;
    private String mediaKey;
    private byte[] secret;
    private Status status = Status.BINDING;
    private Long user;
    private Date createdDate = new Date();
}