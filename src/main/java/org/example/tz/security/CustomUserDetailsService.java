package org.example.tz.security;



import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Simulated users — replace with DB logic as needed
        if (username.equals("admin")) {
            return new CustomUserDetails("admin", "{noop}admin123", "ADMIN");
        } else if (username.equals("user")) {
            return new CustomUserDetails("user", "{noop}user123", "USER");
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
