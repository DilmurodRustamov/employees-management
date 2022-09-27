package tax.uz.employeesmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.ref.UserRole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "^998(9[012345789]|6[125679]|7[01234569])[0-9]{7}$", message = "user.phone.invalid")
    private String password;

    private String position;

    private double salary;

    private String userRole;

    public User mapToUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setName(this.name);
        user.setPhoneNumber(this.phoneNumber);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setPosition(this.position);
        user.setSalary(this.salary);
        user.setUserRole(UserRole.valueOf(userRole));
        return user;
    }

}
