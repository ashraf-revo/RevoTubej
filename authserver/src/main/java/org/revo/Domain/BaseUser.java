package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static org.revo.Domain.Role.*;

/**
 * Created by ashraf on 17/04/17.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseUser  implements UserDetails {
    @JsonProperty(access = READ_ONLY)
    private boolean locked = true;
    @JsonProperty(access = READ_ONLY)
    private boolean enable = false;
    @JsonIgnore
    @Column(length = 3)
    private String type = "000";

    @Transient
    @JsonProperty(access = READ_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        if (type.charAt(0) == '1') {
            roles.add(USER.getBuildRole());
        }
        if (type.charAt(1) == '1') {
            roles.add(MEDIA.getBuildRole());
        }
        if (type.charAt(2) == '1') {
            roles.add(ADMIN.getBuildRole());
            roles.add("ROLE_ACTUATOR");
        }
        return AuthorityUtils.createAuthorityList(roles.stream().toArray(String[]::new));
    }

    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    @Override
    public boolean isEnabled() {
        return enable;
    }
}