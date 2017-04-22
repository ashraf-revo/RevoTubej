package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Created by ashraf on 17/04/17.
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "media_user")
public class User extends BaseUser {
    @Id
    @GenericGenerator(name = "wikiSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    @GeneratedValue(generator = "wikiSequenceGenerator")
    private Long id;
    @NotBlank
    @Column(length = 40)
    private String name;
    @Column(length = 100)
    private String imageUrl = "/assets/images/a0.png";
    @NotBlank
    @Column(length = 15)
    private String phone;
    private String info;
    @Email
    @NotBlank
    @Column(length = 40, unique = true)
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    @NotBlank
    @Column(length = 60)
    private String password;
    @JsonProperty(access = WRITE_ONLY)
    @Transient
    private String currentPassword;
    @CreatedDate
    private Date createdDate = new Date();

    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private MultipartFile image;


    @Override
    @JsonProperty(access = READ_ONLY)
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                " ,createdDate='" + getCreatedDate() + '\'' +
                " ,name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", info='" + info + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}