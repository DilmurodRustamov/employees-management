package tax.uz.employeesmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tax.uz.employeesmanagement.repository.UserRepository;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new SecurityUser(userRepository.findByPhoneNumber(username).orElseThrow(()->new UsernameNotFoundException(username)));
    }

}
