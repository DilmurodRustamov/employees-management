package tax.uz.employeesmanagement.repository;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import tax.uz.employeesmanagement.entity.User;
import tax.uz.employeesmanagement.ref.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    List<User> findAllByUserRole(UserRole userRole);

    User findByIdAndPhoneNumber(Long id, String phoneNumber);

}
