package tax.uz.employeesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.ref.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findAllByUserRole(UserRole userRole);

}
