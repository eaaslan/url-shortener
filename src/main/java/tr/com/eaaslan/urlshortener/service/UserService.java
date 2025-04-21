package tr.com.eaaslan.urlshortener.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.eaaslan.urlshortener.entity.Dto.LoginRequest;
import tr.com.eaaslan.urlshortener.entity.Dto.RegisterRequest;
import tr.com.eaaslan.urlshortener.entity.User;
import tr.com.eaaslan.urlshortener.repository.UserRepository;
import tr.com.eaaslan.urlshortener.security.jwt.JwtAuthenticationResponse;
import tr.com.eaaslan.urlshortener.security.jwt.JwtUtils;
import tr.com.eaaslan.urlshortener.security.jwt.service.UserDetailsImpl;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public User registerUser(RegisterRequest registerRequest){
        User user= User.builder()
                .email(registerRequest.email())
                .role("ROLE_USER")
                .password(registerRequest.password())
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        userRepository.save(user);
        return user;
    }


    public JwtAuthenticationResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        String jwt=jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);

    }

    public User getUserById(String name) {
        return userRepository.findUserByUsername(name).orElseThrow(()->new UsernameNotFoundException("Username not found: "+name));
    }
}
