package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ashraf on 15/04/17.
 */
@Entity
@Getter
@Setter
public class Media {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String m3u8;
    @Column(length = 82)
    private String image;
    @Column(length = 36)
    private String mediaKey;
    @Column(length = 16)
    private byte[] secret;
    private Status status = Status.BINDING;
}