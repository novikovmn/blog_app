package by.novikov.mn.blog_app.security;

import by.novikov.mn.blog_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userRepository.findByUsername(username)
                .map(user -> new CustomUserDetails(
                                user.getId(),
                                user.getUsername(),
                                user.getPassword(),
                                List.of(new SimpleGrantedAuthority(user.getRole().name()))
                    )
                ).orElseThrow(() -> new UsernameNotFoundException("Not found user with username " + ">" + username + "<"));
    }
}
