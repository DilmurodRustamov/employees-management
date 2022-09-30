package tax.uz.employeesmanagement.service;

import tax.uz.employeesmanagement.apiResponseMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.LoginDto;
import tax.uz.employeesmanagement.dto.RegisterDto;

public interface AuthService {

    ApiResponse registerUser(RegisterDto registerDto);

    String loginUser(LoginDto loginDto);
}
