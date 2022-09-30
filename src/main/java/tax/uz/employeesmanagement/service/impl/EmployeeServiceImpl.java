package tax.uz.employeesmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tax.uz.employeesmanagement.apiResponseMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.UserDto;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.ref.UserRole;
import tax.uz.employeesmanagement.repository.UserRepository;
import tax.uz.employeesmanagement.service.EmployeeService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static tax.uz.employeesmanagement.apiResponseMessages.ResponseMessageKeys.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

//    @Override
//    public DataTablesOutput<User> getAll(DataTablesInput input) {
//        return userRepository.findAll(input);
//    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public ApiResponse createUser(UserDto userDto) {
        return createEmployee(userDto.mapToUser(passwordEncoder));
    }

    @Override
    public User get(Long id) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow(() -> new UsernameNotFoundException("entity with specified id not found"));
    }

    @Override
    public ApiResponse updateEmployee(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            return new ApiResponse(USER_DOES_NOT_EXIST, false);
        User user = optionalUser.get();
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(UserRole.valueOf(userDto.getUserRole()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPosition(userDto.getPosition());
        user.setSalary(userDto.getSalary());
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, true);
    }

    public ApiResponse createEmployee(User user) {
        if (userRepository.existsByPhoneNumberAndIdNot(user.getPhoneNumber(), user.getId()))
            return new ApiResponse(USER_ALREADY_EXISTS, false);
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, false);
    }

    @Override
    public ApiResponse deleteEmployee(Long id) {
        userRepository.deleteById(id);
        return new ApiResponse(USER_DELETED, true);
    }

    @Override
    public User getEmployee(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseGet(User::new);
    }
}
