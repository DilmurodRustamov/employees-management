package tax.uz.employeesmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tax.uz.employeesmanagement.apiResponsdeMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.UserDto;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.repository.UserRepository;
import tax.uz.employeesmanagement.service.EmployeeService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static tax.uz.employeesmanagement.apiResponsdeMessages.ResponseMessageKeys.USER_ALREADY_EXISTS;
import static tax.uz.employeesmanagement.apiResponsdeMessages.ResponseMessageKeys.USER_SAVED;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public DataTablesOutput<User> getAll(DataTablesInput input) {
        return userRepository.findAll(input);
    }

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

    public ApiResponse createEmployee(User user) {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber()))
            return new ApiResponse(USER_ALREADY_EXISTS, false);
        userRepository.save(user);
        return new ApiResponse(USER_SAVED, false);
    }
}
