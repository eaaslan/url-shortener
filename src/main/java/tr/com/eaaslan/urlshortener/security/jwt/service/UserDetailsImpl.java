package tr.com.eaaslan.urlshortener.security.jwt.service;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tr.com.eaaslan.urlshortener.entity.User;

import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID=1L;
    private Long id;
    private String username;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user){
        GrantedAuthority grantedAuthority=new SimpleGrantedAuthority(user.getRole());


        return UserDetailsImpl.builder()
                .id(user.getId())
                .authorities(Collections.singleton(grantedAuthority))
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return  password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
