package au.com.belong.customerservice.repository;

import au.com.belong.customerservice.domain.Customer;
import au.com.belong.customerservice.domain.PhoneNumber;
import au.com.belong.customerservice.domain.PhoneNumberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, PhoneNumberId> {

    List<PhoneNumber> findByCustomer(Customer customer);
}
