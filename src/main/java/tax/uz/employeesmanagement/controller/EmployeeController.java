package tax.uz.employeesmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tax.uz.employeesmanagement.apiResponseMessages.ApiResponse;
import tax.uz.employeesmanagement.dto.UserDto;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.service.EmployeeService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    @ResponseBody
    public HttpEntity<?> getEmployees() {
        List<User> employees = employeeService.getAll();
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping
    public HttpEntity<?> createEmployee(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = employeeService.createUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public HttpEntity<?> getEmployee(@PathVariable Long id) {
        User user = employeeService.getEmployee(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PutMapping("/{id}")
    public HttpEntity<?> editEmployee(@PathVariable Long id,@RequestBody UserDto userDto) {
        ApiResponse apiResponse = employeeService.updateEmployee(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteEmployee(@PathVariable Long id) {
        ApiResponse apiResponse = employeeService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

}
