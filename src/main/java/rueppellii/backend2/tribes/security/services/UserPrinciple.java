package rueppellii.backend2.tribes.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rueppellii.backend2.tribes.user.ApplicationUser;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
public class UserPrinciple implements UserDetails {

    //TODO ???
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    @JsonIgnore
    private String password;

    public UserPrinciple(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static UserPrinciple build(ApplicationUser applicationUser) {

        return new UserPrinciple(
                applicationUser.getId(),
                applicationUser.getUsername(),
                applicationUser.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserPrinciple user = (UserPrinciple) obj;
        return Objects.equals(id, user.id);
    }
}
