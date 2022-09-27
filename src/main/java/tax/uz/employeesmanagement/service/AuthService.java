package tax.uz.employeesmanagement.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import tax.uz.employeesmanagement.apiResponsdeMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.LoginDto;
import tax.uz.employeesmanagement.dto.RegisterDto;
import tax.uz.employeesmanagement.entity.User;

public interface AuthService {

    ApiResponse registerUser(RegisterDto registerDto);

    String loginUser(LoginDto loginDto);
}
