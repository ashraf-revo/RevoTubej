package org.revo.Domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ashraf on 15/04/17.
 */
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Media {
    @Id
    @GenericGenerator(name = "wikiSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    @GeneratedValue(generator = "wikiSequenceGenerator")
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
    @CreatedBy
    private Long user;
    @CreatedDate
    private Date createdDate = new Date();
}