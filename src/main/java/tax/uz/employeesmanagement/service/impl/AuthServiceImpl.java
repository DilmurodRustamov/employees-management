package tax.uz.employeesmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tax.uz.employeesmanagement.apiResponsdeMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.LoginDto;
import tax.uz.employeesmanagement.dto.RegisterDto;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.ref.UserRole;
import tax.uz.employeesmanagement.repository.UserRepository;
import tax.uz.employeesmanagement.security.JwtProvider;
import tax.uz.employeesmanagement.security.SecurityUser;
import tax.uz.employeesmanagement.service.AuthService;

import static tax.uz.employeesmanagement.apiResponsdeMessages.ResponseMessageKeys.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

//    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getPerPassword()))
            return new ApiResponse(PASSWORD_NOT_EQUALS, false);
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(registerDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new ApiResponse(USER_ALREADY_EXISTS, false);
        User user = new User(
                registerDto.getName(),
                registerDto.getPhoneNumber(),
                passwordEncoder.encode(registerDto.getPassword()),
                UserRole.USER
        );
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, true);
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword()));
        SecurityUser user = (SecurityUser) authenticate.getPrincipal();
        return JwtProvider.generateToken(user.getPhoneNumber(), user.getUserRole());
    }
}
