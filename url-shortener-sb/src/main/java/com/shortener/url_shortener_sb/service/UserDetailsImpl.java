package com.shortener.url_shortener_sb.service;

import com.shortener.url_shortener_sb.models.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
//UserdetailIMPL is the format spring security understands

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new UserDetailsImpl(
                user.getUsername(),
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
