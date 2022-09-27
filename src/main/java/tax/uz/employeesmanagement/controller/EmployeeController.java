package tax.uz.employeesmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tax.uz.employeesmanagement.apiResponsdeMessages.ApiResponse;
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

    @GetMapping
    public String getPage(@RequestParam(required = false) Long profile, Model model) {
        if (profile == null)
            return "pages/main";
        User user = employeeService.get(profile);
        model.addAttribute(user);
        return "pages/user-profile";
    }

    @GetMapping("data")
    @ResponseBody
    public DataTablesOutput<User> getLeadsData(DataTablesInput input) {
        return employeeService.getAll(input);
    }

    @GetMapping("all")
    @ResponseBody
    public List<User> getLeads() {
        return employeeService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping
    public HttpEntity<?> createEmployee(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = employeeService.createUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
//        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).build();
    }

}
