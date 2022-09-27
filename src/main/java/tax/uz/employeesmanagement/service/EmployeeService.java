package tax.uz.employeesmanagement.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import tax.uz.employeesmanagement.apiResponsdeMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.UserDto;
import tax.uz.employeesmanagement.entity.User;

import java.util.List;

public interface EmployeeService {

    DataTablesOutput<User> getAll(DataTablesInput input);

    List<User> getAll();

    ApiResponse createUser(UserDto userDto);

    User get(Long id);

}
