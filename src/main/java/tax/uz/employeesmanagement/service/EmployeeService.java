package tax.uz.employeesmanagement.service;

import tax.uz.employeesmanagement.apiResponseMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.UserDto;
import tax.uz.employeesmanagement.entity.User;

import java.util.List;

public interface EmployeeService {

//    DataTablesOutput<User> getAll(DataTablesInput input);

    List<User> getAll();

    ApiResponse createUser(UserDto userDto);

    User get(Long id);

    ApiResponse updateEmployee(Long id, UserDto userDto);

    ApiResponse deleteEmployee(Long id);

    User getEmployee(Long id);
}
